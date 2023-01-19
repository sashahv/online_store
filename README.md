#Onlinestore API
## Table of Contents
1. [General Info](#general-info)
2. [Technologies](#technologies)
3. [Installation](#installation)
4. [Collaboration](#collaboration)
5. [Endpoints](#endpoints)
6. [FAQs](#faqs)
### General Info
***
The Online Store API is a RESTful API that allows users to interact with an online store. 
Users can view products, add products to their cart, and purchase products.
## Technologies
***
A list of technologies used within the project:
* [Spring Boot](https://start.spring.io/): Version 3.0.1
* [Apache Maven](https://maven.apache.org/): Version 3.10.1
* [JUnit 5](https://junit.org/)
* [Spring JPA](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/): Version 2.7.2
* [Mockito](https://site.mockito.org/)
## Installation
***
A little intro about the installation.
```
$ git clone https://github.com/kr5ture-edu/lp-awrsp-2022-2023-assessment-project-gr-0.git
$ cd ../path/to/the/file
$ npm install
$ npm start
```
## Collaboration
***
To interact with the project using existing servers, you need:
1. Install Docker, after it will be necessary to create new connection (opening local server).
2. Download Postman or another API testing system, open it
3. Run application
4. Input link - http://localhost:8080/api/v1/{endpoint_name}/
5. After selecting the endpoints (GET, PUT, POST, DELETE) and entering the link URL, you will be able to enter Body (if necessary) to change it.

## Endpoints
The Online Store API has the following endpoints:

### Products
GET /products: Retrieves a list of all products in the store.\
GET /products/{id} Retrieves a specific product by its id.\
POST /products: Adds a new product to the store.\
DELETE /products/{id} Deletes a product by its id.\
PUT /products/{id} Updates an existing product by its id.\\

### Categories
GET /categories: Retrieves a list of all categories.
GET /categories/{id} Retrieves a specific category by its id.
GET /categories/{id}/products: Retrieves a list of all products within a specific category
POST /categories: Creates a new category.
DELETE /categories/{id} Deletes a category by its id.
PUT /categories/{id} Updates an existing category by its id.

## FAQs
***
A list of frequently asked questions
1. Can this service work without running local server?
- No, it's necessary to run local server
3. What means every endpoint?
* GET - is needed to put index in the URL line after "/" - getting infromation (e.g. getting products by their index, showing all users or categories);
* PUT - is needed to put infromation in the certain field - editing infromation (e.g. edit user/product/category details, change some infromation connected with it)
* POST - is needed to put infromation in the certain field - posting infromation (e.g. creating new user/product/category);
* DELETE - is needed to put index in the URL line after "/" - deleting infromation (e.g. deleting user/category/product);
