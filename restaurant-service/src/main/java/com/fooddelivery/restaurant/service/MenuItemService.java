package com.fooddelivery.restaurant.service;

import com.fooddelivery.restaurant.dto.MenuItemDto;
import com.fooddelivery.restaurant.exception.MenuItemNotFoundException;
import com.fooddelivery.restaurant.exception.RestaurantNotFoundException;
import com.fooddelivery.restaurant.model.MenuItem;
import com.fooddelivery.restaurant.model.Restaurant;
import com.fooddelivery.restaurant.repository.MenuItemRepository;
import com.fooddelivery.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    public MenuItem createMenuItem(MenuItemDto menuItemDto) {
        Restaurant restaurant = restaurantRepository.findById(menuItemDto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + menuItemDto.getRestaurantId()));

        MenuItem menuItem = new MenuItem();
        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setCategory(menuItemDto.getCategory());
        menuItem.setImageUrl(menuItemDto.getImageUrl());
        menuItem.setVegetarian(menuItemDto.isVegetarian());
        menuItem.setVegan(menuItemDto.isVegan());
        menuItem.setSpicy(menuItemDto.isSpicy());
        menuItem.setRestaurant(restaurant);

        return menuItemRepository.save(menuItem);
    }

    public Optional<MenuItem> getMenuItemById(Long id) {
        return menuItemRepository.findById(id);
    }

    public List<MenuItem> getAllMenuItems() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    public List<MenuItem> getAvailableMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantIdAndStatus(restaurantId, MenuItem.Status.AVAILABLE);
    }

    public List<MenuItem> getMenuItemsByCategory(MenuItem.Category category) {
        return menuItemRepository.findByCategory(category);
    }

    public List<MenuItem> getMenuItemsByRestaurantAndCategory(Long restaurantId, MenuItem.Category category) {
        return menuItemRepository.findByRestaurantIdAndCategory(restaurantId, category);
    }

    public List<MenuItem> getVegetarianMenuItems() {
        return menuItemRepository.findByIsVegetarianTrue();
    }

    public List<MenuItem> getVeganMenuItems() {
        return menuItemRepository.findByIsVeganTrue();
    }

    public List<MenuItem> getVegetarianMenuItemsByRestaurant(Long restaurantId) {
        return menuItemRepository.findByRestaurantIdAndIsVegetarianTrue(restaurantId);
    }

    public List<MenuItem> getMenuItemsByPriceRange(Long restaurantId, BigDecimal minPrice, BigDecimal maxPrice) {
        return menuItemRepository.findByRestaurantIdAndPriceBetween(restaurantId, minPrice, maxPrice);
    }

    public List<MenuItem> searchMenuItems(Long restaurantId, String keyword) {
        return menuItemRepository.searchMenuItems(restaurantId, keyword);
    }

    public MenuItem updateMenuItem(Long id, MenuItemDto menuItemDto) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found with id: " + id));

        menuItem.setName(menuItemDto.getName());
        menuItem.setDescription(menuItemDto.getDescription());
        menuItem.setPrice(menuItemDto.getPrice());
        menuItem.setCategory(menuItemDto.getCategory());
        menuItem.setImageUrl(menuItemDto.getImageUrl());
        menuItem.setVegetarian(menuItemDto.isVegetarian());
        menuItem.setVegan(menuItemDto.isVegan());
        menuItem.setSpicy(menuItemDto.isSpicy());

        return menuItemRepository.save(menuItem);
    }

    public MenuItem updateMenuItemStatus(Long id, MenuItem.Status status) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu item not found with id: " + id));

        menuItem.setStatus(status);
        return menuItemRepository.save(menuItem);
    }

    public void deleteMenuItem(Long id) {
        if (!menuItemRepository.existsById(id)) {
            throw new MenuItemNotFoundException("Menu item not found with id: " + id);
        }
        menuItemRepository.deleteById(id);
    }
}