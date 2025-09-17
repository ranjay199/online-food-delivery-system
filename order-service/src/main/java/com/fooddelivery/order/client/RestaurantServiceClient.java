package com.fooddelivery.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(name = "restaurant-service")
public interface RestaurantServiceClient {

    @GetMapping("/api/restaurants/{id}")
    RestaurantResponse getRestaurantById(@PathVariable("id") Long id);

    @GetMapping("/api/restaurants/{restaurantId}/menu-items/{itemId}")
    MenuItemResponse getMenuItemById(@PathVariable("restaurantId") Long restaurantId, 
                                   @PathVariable("itemId") Long itemId);

    @GetMapping("/api/restaurants/{restaurantId}/menu-items/available")
    List<MenuItemResponse> getAvailableMenuItems(@PathVariable("restaurantId") Long restaurantId);

    // Inner class for Restaurant response
    class RestaurantResponse {
        private Long id;
        private String name;
        private String description;
        private String address;
        private String phoneNumber;
        private String email;
        private Long ownerId;
        private String status;
        private String imageUrl;
        private Double rating;
        private Integer totalReviews;

        // Default constructor
        public RestaurantResponse() {}

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Long getOwnerId() {
            return ownerId;
        }

        public void setOwnerId(Long ownerId) {
            this.ownerId = ownerId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public Integer getTotalReviews() {
            return totalReviews;
        }

        public void setTotalReviews(Integer totalReviews) {
            this.totalReviews = totalReviews;
        }
    }

    // Inner class for MenuItem response
    class MenuItemResponse {
        private Long id;
        private String name;
        private String description;
        private BigDecimal price;
        private String category;
        private String status;
        private String imageUrl;
        private boolean isVegetarian;
        private boolean isVegan;
        private boolean isSpicy;

        // Default constructor
        public MenuItemResponse() {}

        // Getters and Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public boolean isVegetarian() {
            return isVegetarian;
        }

        public void setVegetarian(boolean vegetarian) {
            isVegetarian = vegetarian;
        }

        public boolean isVegan() {
            return isVegan;
        }

        public void setVegan(boolean vegan) {
            isVegan = vegan;
        }

        public boolean isSpicy() {
            return isSpicy;
        }

        public void setSpicy(boolean spicy) {
            isSpicy = spicy;
        }
    }
}