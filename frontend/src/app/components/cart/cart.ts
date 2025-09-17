import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Cart } from '../../services/cart';
import { Auth } from '../../services/auth';
import { CartItem } from '../../models/food.model';

@Component({
  selector: 'app-cart',
  imports: [CommonModule, RouterModule],
  templateUrl: './cart.html',
  styleUrl: './cart.css'
})
export class CartComponent implements OnInit {
  cartItems: CartItem[] = [];
  subtotal = 0;
  deliveryFee = 0;
  total = 0;
  isLoggedIn = false;

  constructor(
    private cartService: Cart,
    private authService: Auth
  ) {}

  ngOnInit(): void {
    this.cartService.getCartItems().subscribe(items => {
      this.cartItems = items;
      this.calculateTotals();
    });

    this.authService.currentUser$.subscribe(user => {
      this.isLoggedIn = !!user;
    });
  }

  private calculateTotals(): void {
    this.subtotal = this.cartService.getTotalAmount();
    this.deliveryFee = this.cartService.getDeliveryFee();
    this.total = this.subtotal + this.deliveryFee;
  }

  updateQuantity(foodItemId: number, quantity: number): void {
    this.cartService.updateQuantity(foodItemId, quantity);
  }

  removeItem(foodItemId: number): void {
    this.cartService.removeFromCart(foodItemId);
  }

  clearCart(): void {
    this.cartService.clearCart();
  }

  checkout(): void {
    if (this.isLoggedIn) {
      // In a real app, this would navigate to checkout or process the order
      alert('Order placed successfully! (This is a demo)');
      this.cartService.clearCart();
    } else {
      // Redirect to login
      alert('Please login to place an order');
    }
  }
}
