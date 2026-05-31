# 🍕 Awesome Pizza - Order Management Portal

Awesome Pizza is a backend system designed to manage pizza orders efficiently. This is **Iteration 1** of the project, focusing on a robust REST API for customers and pizza makers (pizzaioli), featuring a FIFO (First-In-First-Out) queue system.

## 🚀 Overview

The portal allows customers to place orders without registration and monitor their progress using a unique tracking code. The pizza maker can manage the queue, taking charge of one order at a time to ensure quality and order sequence.

## ✨ Features

- **For Customers**:
  - Place an order instantly (no login required).
  - Receive a unique **8-character tracking code**.
  - Monitor real-time status: `PLACED`, `IN_PROGRESS`, `COMPLETED`.
- **For Pizza Makers**:
  - Automatically fetch the next order in the queue (**FIFO logic**).
  - **Single Tasking**: The system prevents taking a new order if one is already being prepared.
  - One-click order completion.

## 🛠 Tech Stack

- **Java 21**
- **Spring Boot 3.4.1**
- **Spring Data JPA**
- **H2 Database** (In-memory database)
- **Lombok** (Optional)
- **SpringDoc OpenAPI (Swagger UI)**
- **JUnit 5 & Mockito** (Unit Testing)

---

## 🔧 Installation & Running

### Prerequisites
- **JDK 21**
- **Maven 3.8+**

### Steps
1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/awesome-pizza.git
   cd awesome-pizza
