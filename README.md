# Online Food Delivery System

A modern, responsive web application for online food delivery built with Angular. This application provides a complete food ordering experience with restaurant browsing, menu viewing, cart management, user authentication, and order placement functionality.

## 🚀 Features

### Core Functionality
- **Restaurant Browsing**: View all available restaurants with ratings, delivery time, and cuisine categories
- **Advanced Search & Filtering**: Search restaurants by name or cuisine type with real-time filtering
- **Menu Navigation**: Browse restaurant menus organized by food categories
- **Shopping Cart**: Add items to cart, manage quantities, and view order summary
- **User Authentication**: Login/Register functionality with session management
- **Order Placement**: Complete checkout process with order confirmation

### User Interface
- **Responsive Design**: Optimized for desktop, tablet, and mobile devices
- **Modern UI**: Built with Bootstrap 5 and FontAwesome icons
- **Intuitive Navigation**: Clean header navigation with cart item counter
- **Interactive Elements**: Hover effects, smooth transitions, and visual feedback

### Technical Features
- **Angular 20**: Latest Angular framework with standalone components
- **TypeScript**: Full type safety and modern JavaScript features
- **RxJS**: Reactive programming for state management
- **Modular Architecture**: Organized service and component structure
- **Mock Data**: Comprehensive sample data for testing and demonstration

## 📱 Screenshots

### Home Page - Logged In User
![Home Page](https://github.com/user-attachments/assets/46bf93e2-901c-4428-85e5-ffe1dbadb383)

### Restaurant Search & Filtering
![Restaurant Search](https://github.com/user-attachments/assets/20a22c83-5445-45fd-806a-2e1c9e6bedf5)

### Empty Cart After Checkout
![Empty Cart](https://github.com/user-attachments/assets/5ba5e47b-13a2-4d85-bd4d-1ac6cea180ac)

## 🛠️ Installation & Setup

### Prerequisites
- Node.js (v18 or higher)
- npm (v8 or higher)
- Angular CLI (v20 or higher)

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/ranjay199/online-food-delivery-system.git
   cd online-food-delivery-system
   ```

2. **Navigate to frontend directory**
   ```bash
   cd frontend
   ```

3. **Install dependencies**
   ```bash
   npm install
   ```

4. **Start development server**
   ```bash
   npm start
   # or
   ng serve
   ```

5. **Open in browser**
   Navigate to `http://localhost:4200/`

### Build for Production
```bash
npm run build
# or
ng build --configuration production
```

The build artifacts will be stored in the `dist/frontend/` directory.

## 🏗️ Project Structure

```
frontend/
├── src/
│   ├── app/
│   │   ├── components/          # Feature components
│   │   │   ├── auth/           # Authentication (login/register)
│   │   │   ├── cart/           # Shopping cart management
│   │   │   ├── header/         # Navigation header
│   │   │   ├── home/           # Landing page
│   │   │   ├── menu/           # Restaurant menu display
│   │   │   └── restaurants/    # Restaurant listing & search
│   │   ├── models/             # TypeScript interfaces
│   │   │   └── food.model.ts   # Data models
│   │   ├── services/           # Business logic services
│   │   │   ├── auth.ts         # Authentication service
│   │   │   ├── cart.ts         # Cart management service
│   │   │   └── food.ts         # Restaurant & food data service
│   │   ├── app.routes.ts       # Application routing
│   │   ├── app.component.*     # Root component
│   │   └── app.config.ts       # App configuration
│   ├── styles.css              # Global styles
│   └── index.html              # Main HTML file
├── angular.json                # Angular configuration
├── package.json                # Dependencies and scripts
└── README.md                   # This file
```

## 🎯 Key Components

### 1. Home Component
- Hero section with call-to-action
- Popular cuisine categories display
- Featured restaurants showcase
- "How it works" information

### 2. Restaurants Component
- Complete restaurant listing
- Search functionality by name/cuisine
- Category filtering dropdown
- Restaurant cards with ratings and details

### 3. Menu Component
- Restaurant information header
- Menu items organized by categories
- Sidebar navigation for categories
- Add to cart functionality

### 4. Cart Component
- Order items with quantity controls
- Order summary with pricing breakdown
- Checkout functionality
- Empty cart state

### 5. Auth Component
- Toggle between login and register
- Form validation
- Demo mode for testing
- Session management

### 6. Header Component
- Responsive navigation
- Cart item counter
- User authentication status
- Dropdown menu for logged-in users

## 🔧 Services

### Food Service
- Restaurant data management
- Menu item retrieval
- Search and filtering logic
- Mock data for 4 restaurants with multiple menu items

### Cart Service
- Add/remove items from cart
- Quantity management
- Total calculation
- Persistent cart state using RxJS

### Auth Service
- User authentication
- Session management
- Local storage integration
- Mock authentication for demo

## 🎨 Styling

The application uses:
- **Bootstrap 5**: For responsive grid system and components
- **FontAwesome**: For icons and visual elements
- **Custom CSS**: For specific styling and animations
- **CSS Variables**: For consistent theming

## 🧪 Demo Data

The application includes comprehensive mock data:

### Restaurants (4)
- Pizza Palace (Italian)
- Burger Barn (American)  
- Sushi Zen (Japanese)
- Curry House (Indian)

### Food Items (8)
- 2 items per restaurant
- Various categories (Pizza, Burger, Sushi, Curry, Rice)
- Vegetarian options marked
- Realistic pricing

## 🔐 Authentication

**Demo Mode**: The application runs in demo mode where any email/password combination will successfully authenticate the user as "John Doe".

For production use, replace the mock authentication service with real backend API calls.

## 🛣️ Routing

- `/` or `/home` - Home page
- `/restaurants` - Restaurant listing
- `/menu/:restaurantId` - Restaurant menu
- `/cart` - Shopping cart
- `/auth` - Login/Register

## 📱 Responsive Design

The application is fully responsive and optimized for:
- **Desktop**: Full-featured experience with sidebar navigation
- **Tablet**: Adjusted layouts and touch-friendly interfaces
- **Mobile**: Collapsible navigation and stacked layouts

## 🚀 Future Enhancements

- Real backend API integration
- Payment gateway integration
- Order tracking functionality
- User profile management
- Restaurant owner dashboard
- Real-time order status updates
- Push notifications
- Advanced search filters
- Favorites and order history
- Delivery tracking on map

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is open source and available under the [MIT License](LICENSE).

## 👨‍💻 Developer

Built with ❤️ using Angular, Bootstrap, and modern web technologies.

---

**Note**: This is a demonstration application with mock data. For production use, integrate with a real backend API and implement proper security measures.
