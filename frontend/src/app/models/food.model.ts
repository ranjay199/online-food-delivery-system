export interface Restaurant {
  id: number;
  name: string;
  description: string;
  image: string;
  rating: number;
  deliveryTime: string;
  deliveryFee: number;
  category: string;
}

export interface FoodItem {
  id: number;
  name: string;
  description: string;
  price: number;
  image: string;
  category: string;
  restaurantId: number;
  isVegetarian: boolean;
}

export interface CartItem {
  foodItem: FoodItem;
  quantity: number;
  restaurant: Restaurant;
}

export interface Order {
  id: number;
  items: CartItem[];
  totalAmount: number;
  status: 'pending' | 'confirmed' | 'preparing' | 'out_for_delivery' | 'delivered';
  orderDate: Date;
  deliveryAddress: string;
}

export interface User {
  id: number;
  email: string;
  name: string;
  phone: string;
  address: string;
}