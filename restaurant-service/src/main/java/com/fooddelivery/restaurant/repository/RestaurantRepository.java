package com.fooddelivery.restaurant.repository;

import com.fooddelivery.restaurant.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByOwnerId(Long ownerId);
    
    List<Restaurant> findByStatus(Restaurant.Status status);
    
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    
    List<Restaurant> findByAddressContainingIgnoreCase(String address);
    
    @Query("SELECT r FROM Restaurant r WHERE r.rating >= :minRating ORDER BY r.rating DESC")
    List<Restaurant> findByRatingGreaterThanEqual(@Param("minRating") Double minRating);
    
    Optional<Restaurant> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    @Query("SELECT r FROM Restaurant r WHERE r.name LIKE %:keyword% OR r.description LIKE %:keyword% OR r.address LIKE %:keyword%")
    List<Restaurant> searchRestaurants(@Param("keyword") String keyword);
}