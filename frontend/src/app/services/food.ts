import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { Restaurant, FoodItem } from '../models/food.model';

@Injectable({
  providedIn: 'root'
})
export class Food {

  private restaurants: Restaurant[] = [
    {
      id: 1,
      name: 'Pizza Palace',
      description: 'Authentic Italian pizzas with fresh ingredients',
      image: 'https://images.unsplash.com/photo-1513104890138-7c749659a591?w=300&h=200&fit=crop',
      rating: 4.5,
      deliveryTime: '25-35 min',
      deliveryFee: 2.99,
      category: 'Italian'
    },
    {
      id: 2,
      name: 'Burger Barn',
      description: 'Juicy burgers and crispy fries',
      image: 'https://images.unsplash.com/photo-1571091718767-18b5b1457add?w=300&h=200&fit=crop',
      rating: 4.2,
      deliveryTime: '20-30 min',
      deliveryFee: 1.99,
      category: 'American'
    },
    {
      id: 3,
      name: 'Sushi Zen',
      description: 'Fresh sushi and Japanese cuisine',
      image: 'https://images.unsplash.com/photo-1553621042-f6e147245754?w=300&h=200&fit=crop',
      rating: 4.7,
      deliveryTime: '30-40 min',
      deliveryFee: 3.99,
      category: 'Japanese'
    },
    {
      id: 4,
      name: 'Curry House',
      description: 'Spicy Indian curries and naan bread',
      image: 'https://images.unsplash.com/photo-1565557623262-b51c2513a641?w=300&h=200&fit=crop',
      rating: 4.3,
      deliveryTime: '25-35 min',
      deliveryFee: 2.49,
      category: 'Indian'
    }
  ];

  private foodItems: FoodItem[] = [
    // Pizza Palace items
    { id: 1, name: 'Margherita Pizza', description: 'Classic pizza with tomato sauce, mozzarella, and basil', price: 12.99, image: 'https://images.unsplash.com/photo-1604068549290-dea0e4a305ca?w=300&h=200&fit=crop', category: 'Pizza', restaurantId: 1, isVegetarian: true },
    { id: 2, name: 'Pepperoni Pizza', description: 'Pepperoni with mozzarella cheese and tomato sauce', price: 14.99, image: 'https://images.unsplash.com/photo-1628840042765-356cda07504e?w=300&h=200&fit=crop', category: 'Pizza', restaurantId: 1, isVegetarian: false },
    
    // Burger Barn items
    { id: 3, name: 'Classic Burger', description: 'Beef patty with lettuce, tomato, and special sauce', price: 9.99, image: 'https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=300&h=200&fit=crop', category: 'Burger', restaurantId: 2, isVegetarian: false },
    { id: 4, name: 'Chicken Burger', description: 'Grilled chicken breast with avocado and mayo', price: 10.99, image: 'https://images.unsplash.com/photo-1606755962773-d324e9a13086?w=300&h=200&fit=crop', category: 'Burger', restaurantId: 2, isVegetarian: false },
    
    // Sushi Zen items
    { id: 5, name: 'California Roll', description: 'Crab, avocado, and cucumber roll', price: 8.99, image: 'https://images.unsplash.com/photo-1579584425555-c3ce17fd4351?w=300&h=200&fit=crop', category: 'Sushi', restaurantId: 3, isVegetarian: false },
    { id: 6, name: 'Salmon Nigiri', description: 'Fresh salmon over seasoned rice', price: 6.99, image: 'https://images.unsplash.com/photo-1617196034796-73dfa7b1fd56?w=300&h=200&fit=crop', category: 'Sushi', restaurantId: 3, isVegetarian: false },
    
    // Curry House items
    { id: 7, name: 'Chicken Tikka Masala', description: 'Creamy tomato curry with tender chicken', price: 13.99, image: 'https://images.unsplash.com/photo-1585937421612-70a008356fbe?w=300&h=200&fit=crop', category: 'Curry', restaurantId: 4, isVegetarian: false },
    { id: 8, name: 'Vegetable Biryani', description: 'Fragrant rice with mixed vegetables and spices', price: 11.99, image: 'https://images.unsplash.com/photo-1563379091339-03246963d51b?w=300&h=200&fit=crop', category: 'Rice', restaurantId: 4, isVegetarian: true }
  ];

  constructor() { }

  getRestaurants(): Observable<Restaurant[]> {
    return of(this.restaurants);
  }

  getRestaurantById(id: number): Observable<Restaurant | undefined> {
    return of(this.restaurants.find(restaurant => restaurant.id === id));
  }

  getFoodItemsByRestaurant(restaurantId: number): Observable<FoodItem[]> {
    return of(this.foodItems.filter(item => item.restaurantId === restaurantId));
  }

  getFoodItemById(id: number): Observable<FoodItem | undefined> {
    return of(this.foodItems.find(item => item.id === id));
  }

  searchRestaurants(query: string): Observable<Restaurant[]> {
    return of(this.restaurants.filter(restaurant => 
      restaurant.name.toLowerCase().includes(query.toLowerCase()) ||
      restaurant.category.toLowerCase().includes(query.toLowerCase())
    ));
  }
}
