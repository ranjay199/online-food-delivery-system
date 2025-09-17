import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Food } from '../../services/food';
import { Restaurant } from '../../models/food.model';

@Component({
  selector: 'app-restaurants',
  imports: [CommonModule, RouterModule, FormsModule],
  templateUrl: './restaurants.html',
  styleUrl: './restaurants.css'
})
export class Restaurants implements OnInit {
  restaurants: Restaurant[] = [];
  filteredRestaurants: Restaurant[] = [];
  searchQuery = '';
  selectedCategory = '';
  categories: string[] = [];

  constructor(private foodService: Food) {}

  ngOnInit(): void {
    this.foodService.getRestaurants().subscribe(restaurants => {
      this.restaurants = restaurants;
      this.filteredRestaurants = restaurants;
      this.categories = [...new Set(restaurants.map(r => r.category))];
    });
  }

  onSearch(): void {
    this.filterRestaurants();
  }

  onCategoryChange(): void {
    this.filterRestaurants();
  }

  private filterRestaurants(): void {
    let filtered = this.restaurants;

    if (this.searchQuery.trim()) {
      filtered = filtered.filter(restaurant =>
        restaurant.name.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        restaurant.category.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        restaurant.description.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    }

    if (this.selectedCategory) {
      filtered = filtered.filter(restaurant => restaurant.category === this.selectedCategory);
    }

    this.filteredRestaurants = filtered;
  }

  clearFilters(): void {
    this.searchQuery = '';
    this.selectedCategory = '';
    this.filteredRestaurants = this.restaurants;
  }
}
