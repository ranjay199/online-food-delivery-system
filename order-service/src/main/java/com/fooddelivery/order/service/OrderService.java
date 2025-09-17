package com.fooddelivery.order.service;

import com.fooddelivery.order.client.RestaurantServiceClient;
import com.fooddelivery.order.client.UserServiceClient;
import com.fooddelivery.order.dto.CreateOrderRequest;
import com.fooddelivery.order.dto.OrderItemRequest;
import com.fooddelivery.order.exception.InvalidOrderException;
import com.fooddelivery.order.exception.OrderNotFoundException;
import com.fooddelivery.order.model.Order;
import com.fooddelivery.order.model.OrderItem;
import com.fooddelivery.order.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private RestaurantServiceClient restaurantServiceClient;

    @Transactional
    public Order createOrder(CreateOrderRequest request) {
        logger.info("Creating order for user {} at restaurant {}", request.getUserId(), request.getRestaurantId());

        // Validate user exists
        try {
            UserServiceClient.UserResponse user = userServiceClient.getUserById(request.getUserId());
            if (user == null) {
                throw new InvalidOrderException("User not found with id: " + request.getUserId());
            }
        } catch (Exception e) {
            logger.error("Error fetching user: {}", e.getMessage());
            throw new InvalidOrderException("Unable to validate user: " + e.getMessage());
        }

        // Validate restaurant exists
        try {
            RestaurantServiceClient.RestaurantResponse restaurant = restaurantServiceClient.getRestaurantById(request.getRestaurantId());
            if (restaurant == null) {
                throw new InvalidOrderException("Restaurant not found with id: " + request.getRestaurantId());
            }
            if (!"ACTIVE".equals(restaurant.getStatus())) {
                throw new InvalidOrderException("Restaurant is not available for orders");
            }
        } catch (Exception e) {
            logger.error("Error fetching restaurant: {}", e.getMessage());
            throw new InvalidOrderException("Unable to validate restaurant: " + e.getMessage());
        }

        // Create order
        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setRestaurantId(request.getRestaurantId());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setSpecialInstructions(request.getSpecialInstructions());
        order.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(30)); // Default 30 minutes

        // Create order items
        List<OrderItem> orderItems = new ArrayList<>();
        for (OrderItemRequest itemRequest : request.getOrderItems()) {
            OrderItem orderItem = createOrderItem(itemRequest, order);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);
        order.calculateTotalAmount();

        Order savedOrder = orderRepository.save(order);
        logger.info("Order created successfully with id: {}", savedOrder.getId());
        return savedOrder;
    }

    private OrderItem createOrderItem(OrderItemRequest request, Order order) {
        // Validate menu item exists and is available
        try {
            RestaurantServiceClient.MenuItemResponse menuItem = restaurantServiceClient
                    .getMenuItemById(order.getRestaurantId(), request.getMenuItemId());

            if (menuItem == null) {
                throw new InvalidOrderException("Menu item not found with id: " + request.getMenuItemId());
            }

            if (!"AVAILABLE".equals(menuItem.getStatus())) {
                throw new InvalidOrderException("Menu item is not available: " + menuItem.getName());
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(menuItem.getId());
            orderItem.setMenuItemName(menuItem.getName());
            orderItem.setPrice(menuItem.getPrice());
            orderItem.setQuantity(request.getQuantity());
            orderItem.setSpecialInstructions(request.getSpecialInstructions());
            orderItem.setOrder(order);

            return orderItem;
        } catch (Exception e) {
            logger.error("Error fetching menu item: {}", e.getMessage());
            throw new InvalidOrderException("Unable to validate menu item: " + e.getMessage());
        }
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public List<Order> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    public List<Order> getOrdersByStatus(Order.OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    public List<Order> getUserOrdersByStatus(Long userId, Order.OrderStatus status) {
        return orderRepository.findByUserIdAndStatus(userId, status);
    }

    public List<Order> getRestaurantOrdersByStatus(Long restaurantId, Order.OrderStatus status) {
        return orderRepository.findByRestaurantIdAndStatus(restaurantId, status);
    }

    public List<Order> getOrdersBetweenDates(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findOrdersBetweenDates(startDate, endDate);
    }

    @Transactional
    public Order updateOrderStatus(Long orderId, Order.OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        // Validate status transition
        if (!isValidStatusTransition(order.getStatus(), status)) {
            throw new InvalidOrderException("Invalid status transition from " + order.getStatus() + " to " + status);
        }

        order.setStatus(status);

        // Update estimated delivery time based on status
        if (status == Order.OrderStatus.CONFIRMED) {
            order.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(30));
        } else if (status == Order.OrderStatus.PREPARING) {
            order.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(20));
        } else if (status == Order.OrderStatus.OUT_FOR_DELIVERY) {
            order.setEstimatedDeliveryTime(LocalDateTime.now().plusMinutes(15));
        }

        Order updatedOrder = orderRepository.save(order);
        logger.info("Order {} status updated to {}", orderId, status);
        return updatedOrder;
    }

    private boolean isValidStatusTransition(Order.OrderStatus currentStatus, Order.OrderStatus newStatus) {
        switch (currentStatus) {
            case PENDING:
                return newStatus == Order.OrderStatus.CONFIRMED || newStatus == Order.OrderStatus.CANCELLED;
            case CONFIRMED:
                return newStatus == Order.OrderStatus.PREPARING || newStatus == Order.OrderStatus.CANCELLED;
            case PREPARING:
                return newStatus == Order.OrderStatus.OUT_FOR_DELIVERY || newStatus == Order.OrderStatus.CANCELLED;
            case OUT_FOR_DELIVERY:
                return newStatus == Order.OrderStatus.DELIVERED;
            case DELIVERED:
            case CANCELLED:
                return false; // Terminal states
            default:
                return false;
        }
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        if (order.getStatus() == Order.OrderStatus.DELIVERED) {
            throw new InvalidOrderException("Cannot cancel a delivered order");
        }

        if (order.getStatus() == Order.OrderStatus.CANCELLED) {
            throw new InvalidOrderException("Order is already cancelled");
        }

        order.setStatus(Order.OrderStatus.CANCELLED);
        orderRepository.save(order);
        logger.info("Order {} cancelled", orderId);
    }

    public Long getUserOrderCount(Long userId) {
        return orderRepository.countOrdersByUserId(userId);
    }

    public Long getRestaurantOrderCount(Long restaurantId) {
        return orderRepository.countOrdersByRestaurantId(restaurantId);
    }
}