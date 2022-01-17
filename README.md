# meetups-challenge

> This project pretends to involve a microservices-based architecture. 
> Due to lack of time, just two components have been developed.

![architecture](https://i.imgur.com/STaOZp0.png)


## meetups-challenge-user-management

This component deploys a pre-configured jboss/keycloak server instance for managing security.

### requirements

In order to run this server you will need the following:

1. __Docker__ (version 19.0.3 or newer):
  
    * If you have installed Docker, you can check the version by typing the following in a command line:
    
        ```
        docker --version
        ```
      
### usage

1. Build the docker image by typing the following in a command line:

    ```bash
    $ docker build -t meetups-keycloak-image .
    ```
    
2. Launch a docker container from that built image by typing:

    ```bash
    $ docker run --name meetups-keycloak-container -p 8082:8080 meetups-keycloak-image
    ```
3. The Keycloak instance (docker container) is built from image with pre-loaded data:
    * Realm: __MeetupsChallenge__
    * Clients:
        * __java-meetups-challenge-api-public__: for user login, it returns an access token.
        * __java-meetups-challenge-api-confidential__: for consuming Keycloak Rest API endpoints (user admin management).
        * __java-meetups-challenge-api-tokens__: for consuming secured API endpoints.
    * Roles
        * __meetup_user__
        * __meetup_admin__

    * Users:
        * __arturo.chari__ (with password '1234567890' and roles *meetup_admin* and *meetup_admin*)
        * __felix.chari__ (with password '1234567890' and role *meetup_user*)

4. You can log into the Keycloak (open browser http://localhost:8082) using the admin account (username: admin, password: admin).

### test

* Request an access tokey by using cURL:
    ```bash
    $ curl --location --request POST 'http://localhost:8082/auth/realms/MeetupsChallenge/protocol/openid-connect/token' \
           --header 'Content-Type: application/x-www-form-urlencoded' \
           --data-urlencode 'grant_type=password' \
           --data-urlencode 'client_id=java-meetups-challenge-api-public' \
           --data-urlencode 'username=felix.chari' \
           --data-urlencode 'password=0123456789'
    ```  
    
* Finally, an unit test suite can be run:

  * Python3 is required. You can check the version by typing the following in a command line:
  
     ```bash
     $ python3 --version
     ```
     
  * Moreover, a keycloak third-party dependency is necessary in order to run the unit tests. You can install it via pip3 tool:
 
     ```bash
     $ pip install python-keycloak 
     ```
  * Also make sure that you have the correct versions of packages 'urllib3' and 'requests':
     ```bash
     $ pip install requests==2.25.1 urllib3==1.26.5 
     ```
  * Run the unit test suite with Python3:
  
     ```bash
     $ python3 test_keycloak_server.py
     ```

## meetups-challenge-java-api

### stack
* Java 8
* Maven
* Spring Boot
* Hibernate/JPA
* Swagger
* Java Email
* Keycloak admin client
* Hystrix
* Spring Retry
* Junit
* Mockito

### dockerize db, build and run app
1. Dockerize db:

    ```bash
    $ docker run -d --name pg-meetups -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=meetups -p 5432:5432 postgres:latest
    ```

2. Build:

    ```bash
    $ mvn clean install
    ```
    
3. Run (replace the placeholder with the built-jar current final name):

    ```bash
    $ mvn spring-boot:run
    ```

### URL

#### service:
http://localhost:8080/meetups-challenge-java-api
#### swagger-ui:
http://localhost:8080/meetups-challenge-java-api/swagger-ui/index.html?configUrl=/meetups-challenge-java-api/v3/api-docs/swagger-config#
### email notifications

Replace the following properties in src/main/resources/application.properties:
* __spring.mail.username__: *<your-gmail-user>*
* __spring.mail.password__: *<your-gmail-password>*

You also can pass these properties as VM properties when start the application:
```bash
$ mvn spring-boot:run -Dspring.mail.username=<your-gmail-user> -Dspring.mail.password=<your-gmail-password>
```

NOTE: Google account may request you to generate an [application password](https://myaccount.google.com/apppasswords) to use it in the second property above.

### circuit breaker
* If the application cannot retrieve the weather forecast from the Weather API Service (it is down or internet connection failed), it will be looked up from the database.
Finally, if there is no record it will reply with 'Could not get meetup weather from API Service neither from database'.

* When the weather did retrieve successfully from Weather API Service, the application save it in the database and caching this record.
Only one record will be stored in the database for a given weather with unique (date,country,city).
### retry
The application will retry 2 times with an incremental backoff (*2) to connect to the Weather Service.

### scheduled tasks
1. __retrieveWeatherForIncomingMeetups__: task which will try to retrieve the weather (from Weather API Service) for incoming meetups every 6 hours. 
This helps to the circuit breaker flow in order to always has a weather report in the database for all created meetups. 
2. __clearCaches__: task which will clear the cached results every one hour.
### cache

The Cache Manager will cache mainly results for meetup weather and beer packs needed for meetups.

### CI/CD flow
It can be done with Github Actions, GitLab CI, Jenkins, CircleCI.
1. A developer pushes a commit to a git branch in remote repository. This will trigger a CI/CD Build. 
2. Also, Sonar can be use in order to analyze the code for smells and bugs. 
It will also show test coverage and make sure it is enough to assure the good quality of the code.
3. The CI/CD service daemon will run unit and integration tests, and if they are successful, it will create a Pull Request for merge. 
4. After code review and PR approved, this version will be deployed by CI/CD service in a testing environment where automation testing will be performed.
5. After successful QA team tested the new version, it can be deployed in UAT environments and finally in production.

### test
* unit tests:

    ```bash
    $ mvn test
    ```
* postman:
    * Test api endpoints by using the postman collection exported at [here](meetups-challenge-java-api/src/test/resources/meetups-challenge.postman_collection.json). Import that json file in your postman app.

### final thoughts
It was a good challenge!
I proposed this software architecture to offer a scalable and maintainable solution. 
However, I wish I could have more time to finish the whole solution as shown in the diagram.
##### pending items:
* front-end development (meetups-challenge-web-ui)
* API for locations (model)
* i18n
* reach more unit test coverage
* integration tests
* automation tests
##### pending idea:
I also love to code in Python, so I'd like to create an API in FastAPI framework just for comparing performance and resource usage between different implementations.
