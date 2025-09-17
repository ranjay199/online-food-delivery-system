import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { Food } from '../../services/food';
import { Restaurant } from '../../models/food.model';

@Component({
  selector: 'app-home',
  imports: [CommonModule, RouterModule],
  templateUrl: './home.html',
  styleUrl: './home.css'
})
export class Home implements OnInit {
  restaurants: Restaurant[] = [];
  featuredRestaurants: Restaurant[] = [];

  constructor(private foodService: Food) {}

  ngOnInit(): void {
    this.foodService.getRestaurants().subscribe(restaurants => {
      this.restaurants = restaurants;
      this.featuredRestaurants = restaurants.filter(r => r.rating >= 4.5);
    });
  }
}
