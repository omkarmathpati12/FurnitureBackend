# 🪑 Furniture E-Commerce - Backend

This is the backend of the Furniture E-Commerce project, built with **Spring Boot**. It is designed as a **beginner-friendly** project to demonstrate basic REST API concepts, database integration, and business logic.

## 🚀 Technologies Used
- **Language:** Java 17+
- **Framework:** Spring Boot 3.2+
- **Database:** MySQL
- **ORM:** Spring Data JPA
- **Build Tool:** Maven

## ✨ Key Features (Beginner Level)
- **Product Management:** CRUD operations for furniture items.
- **Cart Logic:** Add, update, and remove items from a user's cart.
- **Order Processing:** Automated stock reduction and order creation.
- **Simplified Auth:** Login and Registration using plain-text comparisons (Ideal for understanding logic before moving to JWT/OAuth).
- **Global Exception Handling:** Clean and user-friendly error messages for the frontend.
- **Data Initialization:** Automatically seeds the database with 12+ premium furniture products on start.

## ⚙️ Setup & Installation
1.  **Database Config:**
    - Open `src/main/resources/application.properties`.
    - Update your MySQL `username` and `password`.
    - Create a database named `furniture_db` in your MySQL.
2.  **Run Application:**
    - Run `mvn spring-boot:run` or start the application from your IDE.
    - The server will start on `http://localhost:8080`.

## 📍 API Endpoints
- `GET /api/products` - List all products.
- `POST /api/auth/login` - Simple login.
- `POST /api/cart/add` - Add item to cart (needs `username` param).
- `POST /api/orders` - Place an order.

