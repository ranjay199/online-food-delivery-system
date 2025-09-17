import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { CartItem, FoodItem, Restaurant } from '../models/food.model';

@Injectable({
  providedIn: 'root'
})
export class Cart {
  private cartItems: CartItem[] = [];
  private cartItemsSubject = new BehaviorSubject<CartItem[]>([]);
  public cartItems$ = this.cartItemsSubject.asObservable();

  constructor() { }

  addToCart(foodItem: FoodItem, restaurant: Restaurant, quantity: number = 1): void {
    const existingItem = this.cartItems.find(item => item.foodItem.id === foodItem.id);
    
    if (existingItem) {
      existingItem.quantity += quantity;
    } else {
      this.cartItems.push({
        foodItem,
        restaurant,
        quantity
      });
    }
    
    this.cartItemsSubject.next([...this.cartItems]);
  }

  removeFromCart(foodItemId: number): void {
    this.cartItems = this.cartItems.filter(item => item.foodItem.id !== foodItemId);
    this.cartItemsSubject.next([...this.cartItems]);
  }

  updateQuantity(foodItemId: number, quantity: number): void {
    const item = this.cartItems.find(item => item.foodItem.id === foodItemId);
    if (item) {
      if (quantity <= 0) {
        this.removeFromCart(foodItemId);
      } else {
        item.quantity = quantity;
        this.cartItemsSubject.next([...this.cartItems]);
      }
    }
  }

  clearCart(): void {
    this.cartItems = [];
    this.cartItemsSubject.next([]);
  }

  getCartItems(): Observable<CartItem[]> {
    return this.cartItems$;
  }

  getTotalAmount(): number {
    return this.cartItems.reduce((total, item) => 
      total + (item.foodItem.price * item.quantity), 0
    );
  }

  getTotalItems(): number {
    return this.cartItems.reduce((total, item) => total + item.quantity, 0);
  }

  getDeliveryFee(): number {
    // Use the highest delivery fee from items in cart (if multiple restaurants)
    const restaurants = [...new Set(this.cartItems.map(item => item.restaurant))];
    return restaurants.length > 0 ? Math.max(...restaurants.map(r => r.deliveryFee)) : 0;
  }
}
