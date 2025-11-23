# 📦 Item Detail API - Mercado Libre Challenge

This repository contains a RESTful Backend API designed to support a high-traffic **Item Detail Page**. The project focuses on scalability, clean code, and robust error handling, following Mercado Libre's architectural standards.

---

## 🛠️ Stack Tecnológico

* **Java 21**
* **Spring Boot 3.3.5**
* **H2 Database** (Base de datos en memoria para fácil ejecución)
* **Spring Data JPA**
* **JUnit 5 & Mockito** (Testing)
* **Docker**

---

## 🏗️ Architecture & Design

I implemented a classic **Layered Architecture** to ensure separation of concerns and testability.

```text
HTTP Request
     │
     ▼
[ItemController]      <-- Validates JSON (@Valid) & handles HTTP codes
     │
     ▼
[ItemService]         <-- Business Logic (Creation rules, Lazy Loading)
     │
     ├── (Uses) ──> [ItemMapper] (Transform Entity <-> DTO)
     │
     ├── (Read/Write) ──> [ItemRepository]
     │
     └── (Read/Write) ──> [SellerRepository] / [CategoryRepository]
```
### 🧠 Key Design Decisions

Here is the reasoning behind the architectural choices I made for this MVP:

**1. Domain Modeling (Item vs. Product)**
Even though "Product" is the standard term in e-commerce, I stuck with **`Item`** throughout the codebase (Entities, DTOs, Endpoints) to align strictly with the challenge requirements and the business ubiquitous language (`/items/{id}`).

**2. Type Safety & DDD (Value Objects)**
* **Price:** Instead of a raw `BigDecimal`, I modeled Price as an `@Embeddable` Value Object. This prevents "Primitive Obsession" and ensures `Amount` and `Currency` always travel together with centralized validation.
* **Enums:** I used Enums for `Currency` and `Condition` to avoid "magic strings" and ensure data integrity.

**3. Data Seeding Strategy**
To improve the Developer Experience (DX) when testing the API:
* **Sellers (Lazy Creation):** If you push an item with a seller nickname that doesn't exist, the system creates it on the fly. This removes the friction of creating users before seeding items.
* **Categories (Strict):** Validation is strict here. Items must belong to an existing category to preserve the catalog structure.

**4. Security via DTOs**
I utilized a single `ItemDTO` but leveraged `@JsonProperty(access = READ_ONLY)` for sensitive fields like `id` or `createdAt`. This prevents users from injecting IDs or falsifying timestamps during a `POST` request without the need for duplicate "Request/Response" classes, keeping the codebase concise.

**5. Centralized Error Handling**
I implemented a `GlobalExceptionHandler` (`@ControllerAdvice`). This ensures that no matter what happens (Validation failed, Item not found, or Internal error), the API always returns a clean, standardized JSON response instead of leaking stack traces.
### 🗄️ Domain Model

![Diagrama de Clases.png](extras/Diagrama%20de%20Clases.png)

### 🗄️Data Model

![Diagrama Entidad-Relacion.png](extras/Diagrama%20Entidad-Relacion.png)

---
## 🚀 How to Run

Option A: Maven Wrapper  
You don't need Maven installed on your machine.
```Bash
./mvnw clean spring-boot:run
```
Option B: Docker
If you prefer containers:
```Bash
docker build -t ml-challenge .
docker run -p 8080:8080 ml-challenge
```
The application will start at: http://localhost:8080

---
## 📚 Documentation & Testing
The project includes Swagger UI for interactive testing. 
**👉 View API Documentation::** http://localhost:8080/swagger-ui/index.html

Try Out  
Create Item:
```
{
  "title": "Samsung Galaxy S23 Ultra",
  "description": "Smartphone con camara de 200MP.",
  "price": {
    "amount": 1200.00,
    "currency": "USD"
  },
  "stockQuantity": 15,
  "soldQuantity": 0,
  "condition": { "value": "NEW" },
  "category": { "id": 1 },
  "seller": {
    "name": "SamsungOfficial",
    "page": "http://samsung.com"
  },
  "attributes": [
    { "name": "Color", "value": "Phantom Black" },
    { "name": "Storage", "value": "256GB" }
  ],
  "images": [
    "http://img.mlstatic.com/s23.jpg"
  ]
}
```

Quick Test (CURL)

* Get Item Detail (GET) 

```Bash
curl -X GET http://localhost:8080/items/1
```

* Create an Item (POST):

```Bash
curl -X 'POST' \
  'http://localhost:8080/items/create' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "title": "Samsung Galaxy S23 Ultra",
  "description": "Smartphone con camara de 200MP.",
  "price": {
    "amount": 1200.00,
    "currency": "USD"
  },
  "stockQuantity": 15,
  "soldQuantity": 0,
  "condition": { "value": "NEW" },
  "category": { "id": 1 },
  "seller": {
    "name": "SamsungOfficial",
    "page": "http://samsung.com"
  },
  "attributes": [
    { "name": "Color", "value": "Phantom Black" },
    { "name": "Storage", "value": "256GB" }
  ],
  "images": [
    "http://img.mlstatic.com/s23.jpg"
  ]
}'
```
---
## ✅ Testing
I included Unit Tests (focusing on business logic and mappers) and Integration Tests (focusing on controllers and validation rules).

### Run all tests:
```Bash
./mvnw test
```