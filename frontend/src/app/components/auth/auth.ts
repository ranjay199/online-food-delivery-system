import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { Auth } from '../../services/auth';

@Component({
  selector: 'app-auth',
  imports: [CommonModule, FormsModule],
  templateUrl: './auth.html',
  styleUrl: './auth.css'
})
export class AuthComponent {
  isLoginMode = true;
  isLoading = false;
  
  // Login form
  loginEmail = '';
  loginPassword = '';
  
  // Register form
  registerEmail = '';
  registerPassword = '';
  registerName = '';
  registerPhone = '';
  registerAddress = '';

  constructor(
    private authService: Auth,
    private router: Router
  ) {}

  toggleMode(): void {
    this.isLoginMode = !this.isLoginMode;
    this.clearForms();
  }

  onLogin(): void {
    if (!this.loginEmail || !this.loginPassword) {
      alert('Please fill in all fields');
      return;
    }

    this.isLoading = true;
    this.authService.login(this.loginEmail, this.loginPassword).subscribe(
      success => {
        this.isLoading = false;
        if (success) {
          this.router.navigate(['/']);
        } else {
          alert('Invalid credentials');
        }
      }
    );
  }

  onRegister(): void {
    if (!this.registerEmail || !this.registerPassword || !this.registerName || 
        !this.registerPhone || !this.registerAddress) {
      alert('Please fill in all fields');
      return;
    }

    this.isLoading = true;
    this.authService.register(
      this.registerEmail,
      this.registerPassword,
      this.registerName,
      this.registerPhone,
      this.registerAddress
    ).subscribe(success => {
      this.isLoading = false;
      if (success) {
        this.router.navigate(['/']);
      } else {
        alert('Registration failed');
      }
    });
  }

  clearForms(): void {
    this.loginEmail = '';
    this.loginPassword = '';
    this.registerEmail = '';
    this.registerPassword = '';
    this.registerName = '';
    this.registerPhone = '';
    this.registerAddress = '';
  }
}
