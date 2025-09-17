import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { Food } from '../../services/food';
import { Cart } from '../../services/cart';
import { Restaurant, FoodItem } from '../../models/food.model';

@Component({
  selector: 'app-menu',
  imports: [CommonModule, RouterModule],
  templateUrl: './menu.html',
  styleUrl: './menu.css'
})
export class Menu implements OnInit {
  restaurant: Restaurant | null = null;
  foodItems: FoodItem[] = [];
  groupedItems: { [category: string]: FoodItem[] } = {};
  categories: string[] = [];

  constructor(
    private route: ActivatedRoute,
    private foodService: Food,
    private cartService: Cart
  ) {}

  ngOnInit(): void {
    const restaurantId = Number(this.route.snapshot.paramMap.get('restaurantId'));
    
    this.foodService.getRestaurantById(restaurantId).subscribe(restaurant => {
      this.restaurant = restaurant || null;
    });

    this.foodService.getFoodItemsByRestaurant(restaurantId).subscribe(items => {
      this.foodItems = items;
      this.groupItemsByCategory();
    });
  }

  private groupItemsByCategory(): void {
    this.groupedItems = {};
    this.foodItems.forEach(item => {
      if (!this.groupedItems[item.category]) {
        this.groupedItems[item.category] = [];
      }
      this.groupedItems[item.category].push(item);
    });
    this.categories = Object.keys(this.groupedItems);
  }

  addToCart(foodItem: FoodItem): void {
    if (this.restaurant) {
      this.cartService.addToCart(foodItem, this.restaurant, 1);
      // You could add a toast notification here
    }
  }
}
