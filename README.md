# 🛍️ EasyShop API

**EasyShop** is a full-stack Java Spring Boot RESTful API for a simple e-commerce platform. It supports user registration and login, secure profile management, category and product browsing, shopping cart feature. The backend uses MySQL with connection pooling (DBCP2), Spring Security with JWT authentication, and follows a modular DAO-service-controller architecture.

---

## 🚀 Features

- 🔐 **Authentication**
  - User registration with encrypted passwords
  - JWT-based login system
  - Role-based access (e.g., user, admin)

- 👤 **User Profile**
  - View, create, and update personal profile
  - Linked to authenticated user via `Principal`

- 📦 **Products & Categories**
  - CRUD operations for categories and products (admin)
  - Browse products by category

- 🛒 **Shopping Cart**
  - Add/remove products to cart
  - Track items by logged-in user session


---

## 🛠️ Technologies Used

| Tech         | Description                            |
|--------------|----------------------------------------|
| Java 17      | Primary language                       |
| Spring Boot  | Framework for REST API                 |
| Spring Security | JWT auth and role-based access     |
| MySQL        | Relational database                    |
| DBCP2        | Database connection pooling            |
| Maven        | Project dependency management          |
| Postman      | API testing                            |

---

## 📁 Project Structure

src
└── main
├── java
│ └── org.yearup
│ ├── controllers # API endpoints
│ ├── data # DAO interfaces
│ ├── data.mysql # MySQL DAO implementations
│ ├── models # POJOs (User, Profile, etc.)
│ ├── security.jwt # JWT filter and token provider
│ └── Application.java # Spring Boot main class
└── resources
├── application.properties # DB & JWT config
└── schema.sql # DB setup (if included)

yaml
Copy
Edit

---

## 🔧 Configuration

Update your `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/easyshop
spring.datasource.username=root
spring.datasource.password=yearup
jwt.secret=superSecretKeyForJWT
🧪 How to Run
Clone the repository:

bash
Copy
Edit
git clone https://github.com/ViktoriiaBreha/EasyShopWithAPI.git
Set up MySQL and create the database:

sql
Copy
Edit
CREATE DATABASE easyshop;
Run the project:

bash
Copy
Edit
./mvnw spring-boot:run
Test endpoints using Postman or browser at:

http://localhost:8080/register

http://localhost:8080/login

http://localhost:8080/profile

http://localhost:8080/categories

http://localhost:8080/products

✅ To Do
 Finish checkout/order feature

 Add product image support

 Improve error handling with custom exceptions

 Unit & integration tests

🤝 Contributors
Viktoriia Breha — Full Stack Developer

📜 License
This project is licensed under the MIT License. See LICENSE file for details.

yaml
Copy
Edit

---

Let me know if you'd like it exported as a `.md` file or tailored for deployment (e.g., Docker, Herok
