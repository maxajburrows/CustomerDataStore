# Customer Data API

Welcome to the **Customer Data API**! This Spring Boot Java application provides a RESTful API for storing and retrieving customer data. The API is designed to be simple and intuitive, allowing other applications to easily interact with customer information.

## Endpoints

1. **GET /customers**: Retrieve all customers stored in the database.
2. **GET /customers/{id}**: Retrieve a customer by their unique ID.
3. **GET /customers/search-by-name**: Search for customers by their first name, last name, or both.
4. **POST /customers**: Add a new customer to the database.
5. **PATCH /customers/{id}**: Update an existing customer's information by their ID.

## Customer Fields

Each customer in the database has the following fields:

- **First Name**: The first name of the customer.
- **Last Name**: The last name of the customer.
- **Age**: The age of the customer.
- **Address**: A list of addresses associated with the customer.
- **Email Address**: The email address of the customer.

## Database

The project uses an in-memory H2 database, allowing for easy setup and testing. Data is stored locally and is only available while the application is running.

## Local Setup

To run the application locally and test the endpoints:

1. Clone the repository to your local machine.
2. Navigate to the project directory.
3. Build and run the application using Maven: `mvn clean install && mvn spring-boot:run`.
5. The application will start locally on port [8080](http://localhost:8080).
6. Try it out!:
   - Send requests to http://localhost:8080
   - Navigate to http://localhost:8080/swagger-ui/index.html to test it with swagger.
  
## Future Work

- Validate email addresses with a regex.
- Add e2e PATCH tests.
- Implement a DELETE endpoint.
- Improve automation of test reporting.
- Fully deploy the app with a production database.
