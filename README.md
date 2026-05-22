# 🛒 E-Commerce Shopping Cart API

A RESTful e-commerce backend built with **Spring Boot 3** and an **H2 in-memory database**. The project demonstrates a clean layered architecture enriched with three classic Gang-of-Four design patterns.

---

## 🚀 Tech Stack

![Java](https://img.shields.io/badge/Java-17-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen?logo=springboot)
![H2](https://img.shields.io/badge/H2-In--Memory-blue)
![Hibernate](https://img.shields.io/badge/Hibernate-6.4-yellow)
![Maven](https://img.shields.io/badge/Maven-3.x-red?logo=apachemaven)

- **Spring Boot 3.2** — web, JPA, validation
- **H2** — zero-config in-memory database (console at `/h2-console`)
- **Hibernate 6 / Spring Data JPA** — ORM & repositories
- **Jakarta Validation** — request validation
- **Maven** — build & dependency management

---

## ✨ Features

- **Product catalog** with three types: Books, Clothing, Shoes (joined-table inheritance)
- **Customer management** — register, update, delete
- **Shopping cart** — add, update quantity, remove items with real-time inventory reservation
- **Checkout & orders** — payment type selection, automatic stock confirmation
- **Inventory management** — stock tracking with reserved-quantity logic
- **Low stock alerts** — automatic warnings logged when stock falls to ≤ 5 units
- **Global exception handling** — consistent error responses across all endpoints

---

## 🏗️ Architecture

```
src/main/java/com/ecommerce/
├── controller/       # REST endpoints
├── service/          # Business logic
├── repository/       # Spring Data JPA interfaces
├── entity/           # JPA entities (Product hierarchy + domain models)
├── dto/              # Request DTOs with validation
├── factory/          # Factory Pattern — product creation
├── strategy/         # Strategy Pattern — payment processing
├── observer/         # Observer Pattern — inventory alerts
└── exception/        # Global exception handler & custom exceptions
```

---

## 🎨 Design Patterns

### 1. Factory Pattern — `com.ecommerce.factory`

Each product type has its own factory, all behind a common `ProductFactory<T, R>` interface. `ProductService` delegates object construction to the appropriate factory instead of building objects inline.

```
ProductFactory<T, R>  (interface)
    ├── BookFactory
    ├── ClothingFactory
    └── ShoesFactory
```

**Why:** Encapsulates construction logic per product type; adding a new product type only requires a new factory — no changes to `ProductService`.

---

### 2. Strategy Pattern — `com.ecommerce.strategy`

Payment processing is no longer a chain of `if/else` statements. Each payment type is a self-contained strategy resolved at runtime via `PaymentStrategyRegistry`.

```
PaymentStrategy  (interface)
    ├── CreditCardPaymentStrategy   → status: "Completed"
    ├── BankTransferPaymentStrategy → status: "Pending"
    └── CashOnDeliveryPaymentStrategy → status: "Completed"
```

**Why:** Adding a new payment method means adding one class; zero changes to `OrderService` or any existing code.

---

### 3. Observer Pattern — `com.ecommerce.observer`

After every purchase `OrderService` publishes an inventory event. Any registered `InventoryObserver` is notified when stock drops to or below the threshold (default: 5 units).

```
InventoryObserver  (interface)
    └── LowStockAlertObserver  → logs WARN to console

InventoryEventPublisher  → notifies all observers after confirmPurchase()
```

**Why:** Decouples stock-level monitoring from order processing. New observers (e.g., email alerts, push notifications) can be added without touching `OrderService`.

---

## ⚙️ Getting Started

### Prerequisites

- JDK 17+
- Maven 3.6+

### Run the application

```bash
git clone https://github.com/YOUR_USERNAME/ECommerceSpringBoot.git
cd ECommerceSpringBoot
mvn spring-boot:run
```

The server starts on **http://localhost:8080**.

### H2 Console

Access the in-memory database browser at:

```
http://localhost:8080/h2-console
```

| Field    | Value               |
|----------|---------------------|
| JDBC URL | `jdbc:h2:mem:ecommercedb` |
| Username | `sa`                |
| Password | *(leave blank)*     |

---

## 📡 API Reference

### Customers

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/customers` | Register a new customer |
| `GET` | `/api/customers` | List all customers |
| `GET` | `/api/customers/{id}` | Get customer by ID |
| `PUT` | `/api/customers/{id}` | Update customer |
| `DELETE` | `/api/customers/{id}` | Delete customer |

### Products

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/products` | List all products (supports `?search=`) |
| `GET` | `/api/products/{id}` | Get product by ID |
| `GET` | `/api/products/books` | List all books |
| `GET` | `/api/products/clothing` | List all clothing |
| `GET` | `/api/products/shoes` | List all shoes |
| `POST` | `/api/products/books` | Create a book |
| `POST` | `/api/products/clothing` | Create a clothing item |
| `POST` | `/api/products/shoes` | Create a shoes item |
| `DELETE` | `/api/products/{id}` | Delete a product |

### Inventory

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/inventory` | List all inventory records |
| `GET` | `/api/inventory/product/{productId}` | Get stock for a product |
| `POST` | `/api/inventory` | Create inventory entry for a product |
| `PUT` | `/api/inventory/product/{productId}/stock?stock=N` | Update stock level |

### Cart

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/cart/{customerId}` | View cart |
| `POST` | `/api/cart/{customerId}/items` | Add item to cart |
| `PUT` | `/api/cart/{customerId}/items/{productId}?quantity=N` | Update item quantity |
| `DELETE` | `/api/cart/{customerId}/items/{productId}` | Remove item |
| `DELETE` | `/api/cart/{customerId}` | Clear cart |

### Orders

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/orders/checkout/{customerId}` | Checkout cart |
| `GET` | `/api/orders/{orderId}` | Get order by ID |
| `GET` | `/api/orders/customer/{customerId}` | Get orders for a customer |
| `GET` | `/api/orders` | List all orders |

---

## 📋 Sample Request Bodies

**Create a Book**
```json
POST /api/products/books
{
  "name": "Clean Code",
  "price": 35.99,
  "description": "A handbook of agile software craftsmanship",
  "author": "Robert C. Martin",
  "isbn": "978-0132350884",
  "pages": 431,
  "genre": "Software Engineering",
  "publishYear": 2008,
  "language": "English"
}
```

**Register a Customer**
```json
POST /api/customers
{
  "name": "Ahmed Alaa",
  "email": "ahmed@example.com",
  "password": "secret123",
  "address": "Cairo, Egypt"
}
```

**Checkout**
```json
POST /api/orders/checkout/1
{
  "paymentType": "CREDIT_CARD"
}
```
Supported payment types: `CREDIT_CARD`, `BANK_TRANSFER`, `CASH_ON_DELIVERY`

---

## 📁 Project Structure

```
ECommerceSpringBoot/
├── src/
│   ├── main/
│   │   ├── java/com/ecommerce/
│   │   │   ├── ECommerceApplication.java
│   │   │   ├── controller/
│   │   │   ├── dto/
│   │   │   ├── entity/
│   │   │   ├── exception/
│   │   │   ├── factory/
│   │   │   ├── observer/
│   │   │   ├── repository/
│   │   │   ├── service/
│   │   │   └── strategy/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── .gitignore
├── pom.xml
└── README.md
```

---

