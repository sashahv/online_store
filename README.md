#Onlinestore API
## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [Collaboration](#collaboration)
5. [A little bit about endpoints](#a-little-bit-about-endpoints)
6. [Endpoints](#endpoints)
### General Info
***
The Online Store API is a REST API that allows users to interact with an online store.
After registration and authorization users can view products, add products to their cart, and purchase products, 
generate shopping cart and place order.
## Technologies
***
A list of technologies used within the project:
* [Spring Boot](https://start.spring.io/): Version 3.0.1
* [Spring Data](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/)
* [Spring Security](https://spring.io/projects/spring-security)
* [Apache Maven](https://maven.apache.org/): Version 2.7.0
* [JUnit 5](https://junit.org/)
* [Lombok](https://projectlombok.org/): Version: 1.18.24
## Installation
***
A little intro about the installation.
```
$ cd ../path/to/the/folder
$ git clone https://github.com/sashahv/onlinestore-api.git
```
## Collaboration
***
- Clone the repository to your local machine
- Navigate to the project directory and run the following command: ./mvnw spring-boot:run
- Access the application by opening a web browser and navigating to http://localhost:8080

### A little bit about endpoints
**GET** - getting information (e.g. getting products by their index, showing all users or categories)\
**PUT** - editing information (e.g. edit user/product/category details, change some information connected with it)\
**POST** - posting information (e.g. creating new user/product/category)\
**DELETE** - deleting information (e.g. deleting user/category/product)

## Endpoints
The Online Store API has the following endpoints:

### Products
**REQUEST MAPPING: "api/v1/products"**\
**GET**: /products: Retrieves a list of all products in the store\
**GET**: /products/{id} Retrieves a specific product by its id\
**POST**: /products: Adds a new product to the store\
**DELETE**: /products/{id} Deletes a product by its id\
**PUT**: /products/{id} Updates an existing product by its id

### Categories
**REQUEST MAPPING: "api/v1/categories"** \
**GET**: /: Retrieves a list of all categories\
**GET**: /{id} Retrieves a specific category by its id\
**GET**: /{id}/products: Retrieves a list of all products within a specific category\
**POST**: : Creates a new category\
**DELETE**: /{id} Deletes a category by its id\
**PUT**: /{id} Updates an existing category by its id

### USERS
**REQUEST MAPPING: "api/v1/users"**\
**GET** : Retrieves a user by email\
**POST** /register: Register\
**GET** /verifyRegistration: Verify registration by clicking on link\
**GET** /resendVerifyToken: Resend link to verify account\
**POST** /resetPassword: Reset password by clicking on link\
**POST** /passwordRecovery/token?=**: Page with recovering the password by token\
**POST** /changePassword: Page with changing the password\
**PUT** /{id}/update: Updates an existing user by its id\
**DELETE** /{id}: Deletes user by its id

### ORDERS
**REQUEST MAPPING: "api/v1/orders"**\
**GET** /{orderId}: Retrieves an order by id\
**POST** /placeOrder: Places order

### SHOPPING CART
**REQUEST MAPPING: "api/v1/shoppingCart"**\
**GET** /{id}: Retrieves a shopping cart by id\
**POST** : Create shopping cart by inputting cartItems
