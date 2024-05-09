# Promo codesa app for LAT

## About this application
This application is a REST API created for managing promo codes (coupons). It uses Spring framework and Java, and Maven is the build tool. The application uses H2 in-memory database. All constraints on values were applied and enforced using Jakarta Persistence. This application utilizes *Controller-Service-Repository* pattern.

The application has two types of *Coupons* - *ValueCoupons* (currency dependant, reducing price by specific amount) and *PercentageCoupons* (currency independant, reducing price by a specifc percent). 

## How to run
To run this application, open the project directory using terminal and run command `./mvnw spring-boot:run`. The server is configured to start on `localhost:8080`.

Upon starting the application, the database is seeded with 10 products and 10 coupons of different currencies, types etc. Seeding queries are stored in `/src/main/resources/data.sql`.

## Unit testing
For unit testing I used AssertJ, Mockito and JUnit. I focused on fully covering different cases in Services and Controllers, and in those classes I have achieved 100% code coverage:

<img alt="image" src="https://github.com/arturpietrzak/LAT/assets/69943356/0f4c682a-1030-4cb2-89ea-51a134e0142f">

## Database ERD diagram
Only necessary data is stored in the database, focusing on the rules of database normalization. Other useful fields, for example `usagesLeft` on *Coupons* are calculated by looking up exisitng purchases associated with this coupon, and stored as *@Transient* fields in the *Coupon* object.

<img alt="image" src="https://github.com/arturpietrzak/LAT/assets/69943356/9ad4ea0e-b0ce-4526-8dd4-4cb6c1de9b0c">

## Sample queries
| Description | Method | URL | Expected request body | Response example |
| - | - | - | - | - |
| Create new percentage coupon | POST | `/api/v1/coupons` | ```{"code": String, "expirationDate": LocalDate, "maxUsages": int, "discountPercentage": BigDecimal, "couponType": "PERCENTAGE"}``` | ```{ "couponType": "PERCENTAGE", "id": 11, "code": "percentagenew", "expirationDate": "2024-05-10", "maxUsages": 5, "usagesLeft": 0, "discountPercentage": 0.05 }``` |
| Create new value coupon | POST | `/api/v1/coupons` | ```{ "code": String, "expirationDate": LocalDate, "maxUsages": int, "currency": String (ISO 4217 code), "discountAmount": BigDecimal, "couponType": "VALUE" }``` | ```{ "couponType": "PERCENTAGE", "id": 11, "code": "percentagenew", "expirationDate": "2024-05-10", "maxUsages": 5, "usagesLeft": 0, "discountPercentage": 0.05 }``` |
| Get coupon by code | GET | `/api/v1/coupons/{code}` | - | ```{ "couponType": "VALUE", "id": 1, "code": "teneuro", "expirationDate": "2023-12-10", "maxUsages": 3, "usagesLeft": 3, "currency": "EUR", "discountAmount": 10.99 }``` |
| Get all coupons | GET | `/api/v1/coupons` | - | ```[ { "couponType": "VALUE", "id": 1, "code": "teneuro", "expirationDate": "2023-12-10", "maxUsages": 3, "usagesLeft": 3, "currency": "EUR", "discountAmount": 10.99 }, { "couponType": "VALUE", "id": 2, "code": "small", "expirationDate": "2024-12-10", "maxUsages": 10, "usagesLeft": 10, "currency": "EUR", "discountAmount": 3.99 } ]``` |
| Update product | PUT | `/api/v1/products/{id}` | ```{ "name": String, "description": String, "currency": String (ISO 4217 code),, "price": BigDecimal }``` | ```{ "id": 1, "name": "New name", "description": "New description", "currency": "INR", "price": 10 }``` |
| Create new product | POST | `/api/v1/products` | ```{ "name": String, "description": String, "currency": String (ISO 4217 code),, "price": BigDecimal }``` | ```{ "id": 11, "name": "Product name", "description": "test", "currency": "EUR", "price": 10 }``` |
| Get all products | GET | `/api/v1/products` | - | ```[ { "id": 2, "name": "shirt", "description": null, "currency": "EUR", "price": 20.99 }, { "id": 3, "name": "pepsi", "description": null, "currency": "EUR", "price": 1.99 } ]``` |
| Calculate discount | POST | `/api/v1/discount` | ```{ "code": String, "productId": long }``` | ```{ "price": 17.00, "currency": "EUR", "message": null, "valid": true }``` |
| Simulate purchase | POST | `/api/v1/purchase` | ```{ "code": String, "productId": long }``` | ```{ "id": 1, "dateOfPurchase": "2024-05-09", "currency": "EUR", "price": 20.99, "discount": 3.99, "product": { "id": 2, "name": "shirt", "description": null, "currency": "EUR", "price": 20.99 }, "coupon": { "couponType": "VALUE", "id": 2, "code": "small", "expirationDate": "2024-12-10", "maxUsages": 10, "usagesLeft": 10, "currency": "EUR", "discountAmount": 3.99 } }``` |
| Get sales report | GET | `/api/v1/sales-report` | - | ```{ "elements": [ { "currency": "EUR", "totalRegularPrice": 104.95, "totalDiscount": 19.95, "numberOfPurchases": 5 }, { "currency": "PLN", "totalRegularPrice": 3199.96, "totalDiscount": 2880.00, "numberOfPurchases": 4 } ] }``` |

## CURL for each response example
| Description | CURL |
| - | - |
| Create new percentage coupon | ```curl --location 'http://localhost:8080/api/v1/coupons' \ --header 'Content-Type: application/json' \ --data '{ "code": "percentagenew", "expirationDate": "2024-05-10", "maxUsages": 5, "discountPercentage": 0.05, "couponType": "PERCENTAGE" }'``` |
| Create new value coupon | ```curl --location 'http://localhost:8080/api/v1/coupons' \ --header 'Content-Type: application/json' \ --data '{ "code": "sssss", "expirationDate": "2024-12-10", "maxUsages": 25, "currency": "INR", "discountAmount": 0.12, "couponType": "VALUE" }'``` |
| Get coupon by code | ```curl --location 'http://localhost:8080/api/v1/coupons/teneuro'``` |
| Get all coupons | ```curl --location 'http://localhost:8080/api/v1/coupons'``` |
| Update product | ```curl --location --request PUT 'http://localhost:8080/api/v1/products/1' \ --header 'Content-Type: application/json' \ --data '{ "name": "New name", "description": "New description", "currency": "INR", "price": 10 }'``` |
| Create new product | ```curl --location 'http://localhost:8080/api/v1/products' \ --header 'Content-Type: application/json' \ --data '{ "name": "Product name", "description": "test", "currency": "EUR", "price": 10 }'``` |
| Get all products | ```curl --location 'http://localhost:8080/api/v1/products'``` |
| Calculate discount | ```curl --location 'http://localhost:8080/api/v1/discount' \ --header 'Content-Type: application/json' \ --data '{ "code": "small", "productId": 2 }'``` |
| Simulate purchase | ```curl --location 'http://localhost:8080/api/v1/purchase' \ --header 'Content-Type: application/json' \ --data '{ "code": "ninetyoff", "productId": 6 }'``` |
| Get sales report | ```curl --location 'http://localhost:8080/api/v1/sales-report'``` |
