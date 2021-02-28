# Three Microservices

## Back-End (BE) Engineer
## Pre-Test

## How the Applications Work

The web-application is based on Golang Vanilla Framework. It's implemented through Graph Query Language as well as Google's Remote Procedure Call Application Program Interface (GraphQL & gRPC API) and designed using Kubernetes standard directories. The assumptions that has been made are as follows:

1. Some of tables like User currently didn't have created_at and updated_at for matching my only database that already have certain amount of data.
2. Most of the variable for Javascript Object Notation (JSON) response is styled in snake case while the golang use Pastel and Camel Case. 
3. Password is also temporarely not hashed to match with the previous data that are already inserted into the database. 
4. Indexes from each table in the database may not optimised yet. 
5. Database transaction is not used for now since the calls aren't parallel. 
6. There's denormalisation or some redundancy column in few of the tables to minimise the used of foreign key
7. Some of the tables like category in product didn't have all CRUD functionalities yet since it's only act as a support table or only have predefined values in it. 
8. Authentication still not act as the middleware for securing the endpoint since there're no created_by and updated_by column yet to monitor the activity with each of the rows inside every table. 
9. Since most of the functionalities are only CRUD, there's no additional business logic yet that can be separated. 

Design Decision:
* Create Directories similar to the kubernetes open-source project structure in one microservice and the rest of the two will follow the same standard. 
* Create Dockerfile in each of the three microservices.
* Create docker-compose.yml in the root directory. 
* Create main.go in every root directory of the microservice.
* Create .env in the root directory since all of the microservices will temporarely use the same server configuration. 
* Create main.go on the root in every microservice for running the app. 
* Create utils/config for accessing the environment. 
* Create internal/database for database migration.
* Create internal/models for models instance.
* Create internal/handlers for additional business logic when accessing the model or beside using GraphQL.
* Create cmd/web/resolver.go for routing the web service controller using GraphQL.
* Create internal/constant for better encapsulation on the error. 
* Create test/ for mocking the unit-test such as models.
* Refactor for better validation.
* Refactor for better variable's name.
* Create README.md
* Finishing.  

## Getting Started (How to Run the Program)

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites (How to set up your machine)

1. Navigate to the directory where you've cloned this repository and setting up the docker first.
2. Still in the directory where you've cloned this repository, install all its dependencies and run the app.

    ```bash
    docker-compose up
    ```

    Dependencies are all listed in `Dockerfile` on each of the microservice root directory and `docker-compose.yml` in root of the repository.

3. The app is now running! To check that the web is actually running,
try to send a GET request to one of the microservices (auth), for instance:

    ```bash
    curl http://127.0.0.1:8080
    ```

    or open `http://localhost:8080` from your browser. You should get into the GraphQL playground. 

### Installing (How to check and test the program)

1. Make sure you already pull the docker images and run the container.
Both are listed in `Dockerfile` as well as `docker-compose.yml` so if you followed the instructions to setup your machine above then they should already be installed and running.
2. You can run the check for running container ID with `docker ps` and for the installed images with `docker images` respectively.
3. To run the My Structured Query Language (MySQL) console in one command, you can use `docker exec -it <database container ID> mysql -U <mysql user that stated on the ENV> `. This is useful to check the database directly.
4. To run the tests, you can use `docker exec -it <web container ID> go test`.
5. For more info on what you can do with `docker`, run `docker --help`.

## Documentation

### Authentication Microservice

* [Login User](#login-user)
* [Register User](#register-user)
* [Update User](#update-user)
* [Delete User](#delete-user)

### Product Microservice

* [Get All Books](#get-books)
* [Get All Books with Limit and Offset](#get-all-books)
* [Get All Books by Category ID](#get-books-by-category-id)
* [Get All Categories](#get-categories)
* [Get Category by Category ID](#get-category-by-id)
* [Add Book](#add-book)
* [Update Book](#update-book)
* [Delete Book](#delete-book)

### Order Microservice

* [Get All Order by User ID](#get-user-orders)
* [Create Order](#create-order)
* [Update Order](#update-order)
* [Delete Order](#delete-order)


## Login User
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
query{
  getUser(username: "admin", password: "admin"){
    user_id
    role
    email
  }
}
```

Example Response Body:

```json
{
    "data": {
        "getUser": {
            "user_id": "1",
            "role": "admin",
            "email": "info@ebookHub.id"
        }
    }
}
```

## Register User
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
mutation{
    createUser(input: {username: "graphql5", password: "graphql5", role: "user", email: "graphql5@test.com"}){
        user_id
    }
}
```

Example Response Body:

```json
{
    "data": {
        "createUser": {
            "user_id": "39"
        }
    }
}
```

## Update User
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
mutation{
    updateUser(user_id: "39", updatedUser: {username: "graphql6", password: "graphql6", role: "user", email: "graphql6@test.com"}){
        user_id
        username
        role
        email
    }
}
```

Example Response Body:

```json
{
    "data": {
        "updateUser": {
            "user_id": "39",
            "username": "graphql6",
            "role": "user",
            "email": "graphql6@test.com"
        }
    }
}
```

## Delete User
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
mutation{
    deleteUser(user_id: "37"){
        user_id
        role
        email
    }
}
```

Example Response Body:

```json
{
    "data": {
        "deleteUser": {
            "user_id": "37",
            "role": "user",
            "email": "graphql3@test.com"
        }
    }
}
```

## Get All Books
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
query{
  books(){
      book_id
      img_path
      title
      author
      publisher
      description
      stock
      category
      created_at
  }
}
```

Example Response Body:

```json
{
    "data": {
        "books": [
            {
                "book_id": "1",
                ...
                }
        ]
    }
}
```

## Add Book
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
mutation{
    createBook(input: {img_path: "", title: "graphql", author: "graphql", publisher: "graphql@test.com", description: "graphql", stock: 1, category: "Fiksi"}){
        book_id
    }
}
```

Example Response Body:

```json
{
    "data": {
        "createBook": {
            "book_id": "31"
        }
    }
}
```

## Update Book
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
mutation{
    updateBook(book_id: "31", updatedBook: {img_path: "", title: "graphql2", author: "graphql2", publisher: "graphql2@test.com", description: "graphql2", stock: 1, category: "Fiksi"}){
        book_id
        img_path
        title
        author
        publisher
        description
        stock
        category
        created_at
    }
}
```

Example Response Body:

```json
{
    "data": {
        "updateBook": {
            "book_id": "31",
            ....
            }
    }
}
```

## Delete Book
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
mutation{
    deleteBook(book_id: "31"){
        book_id
        img_path
        author
        publisher
        description
        stock
        category
    }
}
```

Example Response Body:

```json
{
    "data": {
        "deleteBook": {
            "book_id": "31",
            ....
             "category": "Fiksi"
        }
    }
}
```

## Get All Books by Category ID
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
query{
  getBookByCategoryId(categoryId: 1){
      book_id
      img_path
      title
      author
      publisher
      description
      stock
      category
      created_at
  }
}
```

Example Response Body:

```json
{
    "data": {
        "getBookByCategoryId": [
            {
                "book_id": "5",
                ....
                }
        ]
    }
}
```

## Get All Orders by User ID
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
query{
  getOrdersByUserId(userId: 7){
      order_id
      book_id
      user_id
  }
}
```

Example Response Body:

```json
{
    "data": {
        "getOrdersByUserId": [
            {
                "order_id": "0",
                "book_id": 6,
                "user_id": 7
            },
            {
                "order_id": "0",
                "book_id": 8,
                "user_id": 7
            }
        ]
    }
}
```

## Create Order
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
mutation{
    createOrder(input: {book_id: 7, user_id: 2}){
        order_id
    }
}
```

Example Response Body:

```json
{
    "data": {
        "createOrder": {
            "order_id": "12"
        }
    }
}
```

## Update Order
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
mutation{
    updateOrder(order_id: "2", updatedOrder: {book_id: 5, user_id: 3}){
        order_id
        book_id
        user_id
    }
}
```

Example Response Body:

```json
{
    "data": {
        "updateOrder": {
            "order_id": "2",
            "book_id": 5,
            "user_id": 3
        }
    }
}
```

## Delete Order
URL: POST - `http://localhost:8000/query`

Example Request Body:

```graphql
mutation{
    deleteOrder(order_id: "10"){
        order_id
    }
}
```

Example Response Body:

```json
{
    "data": {
        "deleteOrder": {
            "order_id": "10"
        }
    }
}
```

Postman's API Documentation can be found [here](https://documenter.getpostman.com/view/1319523/TWDdiYsT).

## Built With

* [Golang](https://golang.org/) - The backend development language 
* [FreeSQL](https://www.freesqldatabase.com/) - Used to generate free MySQL cloud database. 

## Authors

* **Bryanza Novirahman** - *Information Technology Master's Graduate at University of Indonesia* - [LinkedIn](https://www.linkedin.com/in/bryanza-novirahman-902a94131)

## Important links
* [Docker](https://www.docker.com)