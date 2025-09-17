package com.fooddelivery.restaurant.controller;

import com.fooddelivery.restaurant.dto.MenuItemDto;
import com.fooddelivery.restaurant.model.MenuItem;
import com.fooddelivery.restaurant.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/restaurants/{restaurantId}/menu-items")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<?> createMenuItem(@PathVariable Long restaurantId, @Valid @RequestBody MenuItemDto menuItemDto) {
        try {
            menuItemDto.setRestaurantId(restaurantId);
            MenuItem menuItem = menuItemService.createMenuItem(menuItemDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(menuItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/available")
    public ResponseEntity<List<MenuItem>> getAvailableMenuItems(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = menuItemService.getAvailableMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByCategory(@PathVariable Long restaurantId, 
                                                               @PathVariable MenuItem.Category category) {
        List<MenuItem> menuItems = menuItemService.getMenuItemsByRestaurantAndCategory(restaurantId, category);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/vegetarian")
    public ResponseEntity<List<MenuItem>> getVegetarianMenuItems(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = menuItemService.getVegetarianMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<MenuItem>> getMenuItemsByPriceRange(@PathVariable Long restaurantId,
                                                                 @RequestParam BigDecimal minPrice,
                                                                 @RequestParam BigDecimal maxPrice) {
        List<MenuItem> menuItems = menuItemService.getMenuItemsByPriceRange(restaurantId, minPrice, maxPrice);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> searchMenuItems(@PathVariable Long restaurantId, @RequestParam String keyword) {
        List<MenuItem> menuItems = menuItemService.searchMenuItems(restaurantId, keyword);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<?> getMenuItemById(@PathVariable Long restaurantId, @PathVariable Long itemId) {
        return menuItemService.getMenuItemById(itemId)
                .map(menuItem -> ResponseEntity.ok().body(menuItem))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<?> updateMenuItem(@PathVariable Long restaurantId, 
                                          @PathVariable Long itemId, 
                                          @Valid @RequestBody MenuItemDto menuItemDto) {
        try {
            menuItemDto.setRestaurantId(restaurantId);
            MenuItem updatedMenuItem = menuItemService.updateMenuItem(itemId, menuItemDto);
            return ResponseEntity.ok(updatedMenuItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    @PatchMapping("/{itemId}/status")
    public ResponseEntity<?> updateMenuItemStatus(@PathVariable Long restaurantId, 
                                                 @PathVariable Long itemId, 
                                                 @RequestParam MenuItem.Status status) {
        try {
            MenuItem updatedMenuItem = menuItemService.updateMenuItemStatus(itemId, status);
            return ResponseEntity.ok(updatedMenuItem);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{itemId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable Long restaurantId, @PathVariable Long itemId) {
        try {
            menuItemService.deleteMenuItem(itemId);
            return ResponseEntity.ok(new MessageResponse("Menu item deleted successfully!"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Error: " + e.getMessage()));
        }
    }

    // Global menu item endpoints (not restaurant specific)
    @GetMapping("/all")
    public ResponseEntity<List<MenuItem>> getAllMenuItems() {
        List<MenuItem> menuItems = menuItemService.getAllMenuItems();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/all/category/{category}")
    public ResponseEntity<List<MenuItem>> getAllMenuItemsByCategory(@PathVariable MenuItem.Category category) {
        List<MenuItem> menuItems = menuItemService.getMenuItemsByCategory(category);
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/all/vegetarian")
    public ResponseEntity<List<MenuItem>> getAllVegetarianMenuItems() {
        List<MenuItem> menuItems = menuItemService.getVegetarianMenuItems();
        return ResponseEntity.ok(menuItems);
    }

    @GetMapping("/all/vegan")
    public ResponseEntity<List<MenuItem>> getAllVeganMenuItems() {
        List<MenuItem> menuItems = menuItemService.getVeganMenuItems();
        return ResponseEntity.ok(menuItems);
    }

    // Inner classes for responses
    public static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class ErrorResponse {
        private String error;

        public ErrorResponse(String error) {
            this.error = error;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }
}