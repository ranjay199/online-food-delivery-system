import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { User } from '../models/food.model';

@Injectable({
  providedIn: 'root'
})
export class Auth {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$ = this.currentUserSubject.asObservable();

  constructor() { 
    // Check if user is already logged in (from localStorage)
    const savedUser = localStorage.getItem('currentUser');
    if (savedUser) {
      this.currentUserSubject.next(JSON.parse(savedUser));
    }
  }

  login(email: string, password: string): Observable<boolean> {
    // Mock login - in real app, this would call an API
    if (email && password) {
      const user: User = {
        id: 1,
        email: email,
        name: 'John Doe',
        phone: '+1234567890',
        address: '123 Main St, City, State 12345'
      };
      
      this.currentUserSubject.next(user);
      localStorage.setItem('currentUser', JSON.stringify(user));
      return of(true);
    }
    return of(false);
  }

  register(email: string, password: string, name: string, phone: string, address: string): Observable<boolean> {
    // Mock registration - in real app, this would call an API
    const user: User = {
      id: Date.now(), // Mock ID
      email,
      name,
      phone,
      address
    };
    
    this.currentUserSubject.next(user);
    localStorage.setItem('currentUser', JSON.stringify(user));
    return of(true);
  }

  logout(): void {
    this.currentUserSubject.next(null);
    localStorage.removeItem('currentUser');
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }

  isLoggedIn(): boolean {
    return this.currentUserSubject.value !== null;
  }
}
