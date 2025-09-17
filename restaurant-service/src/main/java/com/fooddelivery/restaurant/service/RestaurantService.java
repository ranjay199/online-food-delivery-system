package com.fooddelivery.restaurant.service;

import com.fooddelivery.restaurant.dto.RestaurantDto;
import com.fooddelivery.restaurant.exception.RestaurantAlreadyExistsException;
import com.fooddelivery.restaurant.exception.RestaurantNotFoundException;
import com.fooddelivery.restaurant.model.Restaurant;
import com.fooddelivery.restaurant.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(RestaurantDto restaurantDto) {
        if (restaurantRepository.existsByEmail(restaurantDto.getEmail())) {
            throw new RestaurantAlreadyExistsException("Restaurant with email already exists: " + restaurantDto.getEmail());
        }

        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantDto.getName());
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setPhoneNumber(restaurantDto.getPhoneNumber());
        restaurant.setEmail(restaurantDto.getEmail());
        restaurant.setOwnerId(restaurantDto.getOwnerId());
        restaurant.setImageUrl(restaurantDto.getImageUrl());

        return restaurantRepository.save(restaurant);
    }

    public Optional<Restaurant> getRestaurantById(Long id) {
        return restaurantRepository.findById(id);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getRestaurantsByOwner(Long ownerId) {
        return restaurantRepository.findByOwnerId(ownerId);
    }

    public List<Restaurant> getActiveRestaurants() {
        return restaurantRepository.findByStatus(Restaurant.Status.ACTIVE);
    }

    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.searchRestaurants(keyword);
    }

    public List<Restaurant> getRestaurantsByRating(Double minRating) {
        return restaurantRepository.findByRatingGreaterThanEqual(minRating);
    }

    public Restaurant updateRestaurant(Long id, RestaurantDto restaurantDto) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        restaurant.setName(restaurantDto.getName());
        restaurant.setDescription(restaurantDto.getDescription());
        restaurant.setAddress(restaurantDto.getAddress());
        restaurant.setPhoneNumber(restaurantDto.getPhoneNumber());
        restaurant.setImageUrl(restaurantDto.getImageUrl());

        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurantStatus(Long id, Restaurant.Status status) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        restaurant.setStatus(status);
        return restaurantRepository.save(restaurant);
    }

    public void deleteRestaurant(Long id) {
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException("Restaurant not found with id: " + id);
        }
        restaurantRepository.deleteById(id);
    }

    public Restaurant updateRating(Long id, Double rating, Integer reviewCount) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with id: " + id));

        restaurant.setRating(rating);
        restaurant.setTotalReviews(reviewCount);
        return restaurantRepository.save(restaurant);
    }
}