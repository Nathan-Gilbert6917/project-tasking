# Project Tasking

## Technology
### Frontend
- React
- Axios

### Backend
- Spring Boot
- Spring Framework
- Spring Boot-JPA
- Spring Boot-JDBC
- Spring Boot-WEB
- MySQL Connector
- Spring Boot-Test
- JUnit
- Mockito

### Database
- MySQL


## Running Spring Backend
- cd project-tasking
- ./mvnw spring-boot:run

## Running React Frontend
- cd project-tasking-ui
- npm start

# Backend Approach:
The design pattern I am using creates several layers that interact with each other 
                    HTTP Request           Call                Call                     Performs Database Actions
Frontend (React) ==> REST APIs ==> Services ==> DAOs w/ Entities ==> Database (MySQL)

From the Front end to the Database, it goes:
1. The Frontend sends HTTP Requests that get answered by the REST APIs
2. The REST APIs utilize services to access DAOs
3. The DAOs utilize Entities to perform data access operations on the database

From the Database back to the Front end:
1. The Database returns the results found
2. The Entity holds the results
3. The DAO returns the Entity(s) or accesses the Entities attributes and returns the relevant information
4. The Service returns the returned data(s) from the DAO
5. The REST API answers the HTTP Request with a JSON object holding the requested data
6. The Frontend handles the JSON and uses the data to update the display  

An Entity works as a DTO or (Data Transfer Object) that holds attributes such as columns, Id, Keys, relations, etc. to make an object that maps the database values. 

DAO (Data Access Object) provides the implementation of the data access operations (DML, DDL) for a data source (In this case relational tables in a MySQL Database) 

Services contain the business logic that interacts with DAOs to access the data sources. Creating a separation between DAO and Services means changes to one such as the DAO does not affect the other in this case the services

These services can be used in different ways. Most commonly used as methods to achieve the goal of a REST API endpoint. In my project, I have different REST controllers that utilize the services to interact indirectly with the database. 

Utilizing this design pattern allows for each layer to have its own encapsulation and abstractions that allow for easy testing of each layer and modifications that will not cause problems to other areas of the system as long as the inputs and return value are kept the same.

Since I am using a REST API accessed backend the frontend could be anything that can send HTTP requests so I could use Angular, React, Vue, Blazor, CLI, etc.
This approach allows for a straightforward process for additions. If I want to add a new Database table and access the values 
1. Design the table or Data source 
2. Create an Entity that resembles the columns of the table, create constructors, create getters and setters
3. Create a DAO Interface holding the methods the DAO should perform

### Notification system
Using Server-Side Events
- Clients subscribe and are added to a map of clients mapped with SseEmitters
- Certain user actions will dispatch notification events to a list of clients
- The clients EventSource will keep the client side up to date with notifications

4. Create a DAO implementation of the DAO Interface implementing and injecting the entityManager, and entity into the constructor(s)
5. Create a Service that utilizes the DAO and typically has similar or the same methods that the DAO has
6. Create a REST API controller to map HTTP requests to access desired service methods and handle the data returned
7. Call the API from the front end and handle errors and response
