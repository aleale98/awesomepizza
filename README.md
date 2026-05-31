# 🍕 Awesome Pizza API

Simple REST API to manage pizza orders.

## 🚀 How to Start
1. **Run the Application**: Open the main class in IntelliJ and click the green "Play" button, or run `mvn spring-boot:run` in the terminal.
2. **Access the GUI (Swagger)**:
   Once the app is running, open your browser at:
   👉 **http://localhost:8080/swagger-ui/index.html**

---

## 📍 API Endpoints

### 👤 Customer Endpoints
- **POST** `/api/v1/orders`  
  *Creates a new order and returns a unique 8-character tracking code.*
- **GET** `/api/v1/orders/{code}`  
  *Check the current status (PLACED, IN_PROGRESS, COMPLETED) using your code.*

### 👨‍🍳 Pizza Maker (Pizzaiolo) Endpoints
- **PATCH** `/api/v1/pizzaiolo/next`  
  *Picks up the oldest order from the queue. Note: You can only handle one order at a time.*
- **PATCH** `/api/v1/pizzaiolo/{id}/complete`  
  *Marks the specific order as finished/ready.*

---

## ⚖️ Business Rules
- **FIFO Queue**: The oldest order is always processed first.
- **Single Tasking**: The system blocks the pizza maker from taking a new order if one is already "In Progress".
- **Database**: The app uses an in-memory H2 database (data is reset on restart).
