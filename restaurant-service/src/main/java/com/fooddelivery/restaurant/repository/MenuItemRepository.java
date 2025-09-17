package com.fooddelivery.restaurant.repository;

import com.fooddelivery.restaurant.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    List<MenuItem> findByRestaurantId(Long restaurantId);
    
    List<MenuItem> findByRestaurantIdAndStatus(Long restaurantId, MenuItem.Status status);
    
    List<MenuItem> findByCategory(MenuItem.Category category);
    
    List<MenuItem> findByRestaurantIdAndCategory(Long restaurantId, MenuItem.Category category);
    
    List<MenuItem> findByIsVegetarianTrue();
    
    List<MenuItem> findByIsVeganTrue();
    
    List<MenuItem> findByRestaurantIdAndIsVegetarianTrue(Long restaurantId);
    
    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :restaurantId AND m.price BETWEEN :minPrice AND :maxPrice")
    List<MenuItem> findByRestaurantIdAndPriceBetween(@Param("restaurantId") Long restaurantId, 
                                                     @Param("minPrice") BigDecimal minPrice, 
                                                     @Param("maxPrice") BigDecimal maxPrice);
    
    @Query("SELECT m FROM MenuItem m WHERE m.restaurant.id = :restaurantId AND (m.name LIKE %:keyword% OR m.description LIKE %:keyword%)")
    List<MenuItem> searchMenuItems(@Param("restaurantId") Long restaurantId, @Param("keyword") String keyword);
}