🧪 Testing the API
1. Interactive Swagger UI (Recommended)
Once the application is running, access the graphical interface to test all endpoints:
👉 http://localhost:8080/swagger-ui/index.html
2. Manual Endpoints Reference
Customer API
Method	Endpoint	Description
POST	/api/v1/orders	Create a new order and get a tracking code.
GET	/api/v1/orders/{code}	Get the current status of an order.
Pizza Maker API
Method	Endpoint	Description
PATCH	/api/v1/pizzaiolo/next	Take the oldest pending order (sets status to IN_PROGRESS).
PATCH	/api/v1/pizzaiolo/{id}/complete	Mark a specific order as COMPLETED.
🏗 Architecture Decisions
FIFO Logic: Orders are processed based on their creation timestamp (createdAt), ensuring the first customer to order is the first to be served.
Concurrency Guard: The system checks if any order is currently IN_PROGRESS before allowing a pizza maker to take a new one, preventing multiple simultaneous tasks for a single station.
DTO Pattern: Used Java Records for Data Transfer Objects to ensure immutability and clean API responses, decoupling the Database Entity from the JSON output.
Error Handling: A Global Exception Handler is implemented to return meaningful HTTP status codes (e.g., 404 Not Found for invalid codes, 400 Bad Request if the pizza maker is busy).
🧪 Unit Tests
To run the automated tests:
code
Bash
mvn test
The test suite covers service logic, repository interactions, and edge cases (e.g., trying to take an order while another is in progress).
🗄 Database Console
You can inspect the in-memory database at any time:
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
User: sa | Password: (leave blank)
