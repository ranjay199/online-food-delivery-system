import { Routes } from '@angular/router';
import { Home } from './components/home/home';
import { Restaurants } from './components/restaurants/restaurants';
import { Menu } from './components/menu/menu';
import { CartComponent } from './components/cart/cart';
import { AuthComponent } from './components/auth/auth';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'home', component: Home },
  { path: 'restaurants', component: Restaurants },
  { path: 'menu/:restaurantId', component: Menu },
  { path: 'cart', component: CartComponent },
  { path: 'auth', component: AuthComponent },
  { path: '**', redirectTo: '' }
];
