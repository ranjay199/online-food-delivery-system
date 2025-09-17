package com.fooddelivery.order.repository;

import com.fooddelivery.order.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(Long orderId);
    
    @Query("SELECT oi FROM OrderItem oi WHERE oi.menuItemId = :menuItemId")
    List<OrderItem> findByMenuItemId(@Param("menuItemId") Long menuItemId);
    
    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o WHERE o.restaurantId = :restaurantId")
    List<OrderItem> findByRestaurantId(@Param("restaurantId") Long restaurantId);
    
    @Query("SELECT oi FROM OrderItem oi JOIN oi.order o WHERE o.userId = :userId")
    List<OrderItem> findByUserId(@Param("userId") Long userId);
}