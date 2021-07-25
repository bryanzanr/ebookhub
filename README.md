# Ebookhub Monolith

A barebones Java app, which can easily be deployed to Heroku.

This application supports the [Getting Started with Java on Heroku](https://devcenter.heroku.com/articles/getting-started-with-java) article - check it out.

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

## Running Locally

Make sure you have Java and Maven installed.  Also, install the [Heroku CLI](https://cli.heroku.com/).

```sh
$ git clone https://github.com/bryanzanr/ebookhub.git
$ cd ebookhub
$ mvn install
$ heroku local:start
```

Your app should now be running on [localhost:5000](http://localhost:5000/).

If you're going to use a database, ensure you have a local `.env` file in the `.bash_profile` that reads something like this:

```
SPRING_DATABASE_URL=jdbc:mysql://localhost:3306/java_database_name
SPRING_DATABASE_USERNAME=xxx
SPRING_DATABASE_PASSWORD=xxx
```

## Deploying to Heroku

```sh
$ heroku create
$ git push heroku main
$ heroku open
```

## Documentation

### User Module

* [Login User](#login-user)
* [Register User](#register-user)
* [Update User](#update-user)
* [Delete User](#delete-user)

### Book Module

* [Get All Books](#get-books)
* [Get All Books with Limit and Offset](#get-all-books)
* [Get All Books by Category ID](#get-books-by-category-id)
* [Get All Categories](#get-categories)
* [Get Category by Category ID](#get-category-by-id)
* [Add Book](#add-book)
* [Update Book](#update-book)
* [Delete Book](#delete-book)

### Order Module

* [Get All Order by User ID](#get-user-orders)
* [Create Order](#create-order)
* [Update Order](#update-order)
* [Delete Order](#delete-order)


## Login User
URL: POST - `http://localhost:8000/login`

Example Request Body:

```json
{
    "username": "test_java2_edit",
    "password": "test12345"
}
```

Example Response Body:

```json
{
    "data": {
        "role": "admin",
        "email": "test12345@test.com",
        "password": "test12345",
        "username": "test_java2_edit",
        "id": 42
    },
    "status": 200
}
```

## Register User
URL: POST - `http://localhost:8000/register`

Example Request Body:

```json
{
    "role": "admin",
    "email": "test12345@test.com",
    "password": "test12345",
    "username": "test_java2"
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
URL: POST - `http://localhost:8000/user/{id}`

Example Request Body:

```json
{
    "role": "admin",
    "email": "test12345@test.com",
    "password": "test12345",
    "username": "test_java2_edit_again"
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
    "error": {
        "cause": {
            "cause": {
                "cause": null,
                "stackTrace": [
                    {
                        "methodName": "createSQLException",
                        "fileName": "SQLError.java",
                        "lineNumber": 117,
                        "className": "com.mysql.cj.jdbc.exceptions.SQLError",
                        "nativeMethod": false
                    },
                <...>
                ]
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
    "timestamp": "2021-07-25T03:24:11.627+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "path": "/merchants"
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


For more information about using Java on Heroku, see these Dev Center articles:

- [Java on Heroku](https://devcenter.heroku.com/categories/java)
