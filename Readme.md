### This is a multi module maven project to find list of primes with a top limit of 1000000.

### Modules:
1. prime-service-api - This module, prime-service-api exposed rest api. 
2. prime-service-integration - This module contains the interface definition which is implemented by the prime service providers.
3. prime-service-default - This is a service which implements the interface defined in the prime-service-integration.
4. prime-service-apache - This is another service which implements the interface defined in the prime-service-integration.

### How to run the service:
#### For both the methods listed below please run mvn clean package from the top level of the code.
1. Open command line and Go to prime-service-api - cd prime-service-api
2. run command mvn spring-boot:run

#### Alternative way to run:
1. Open command line and Go to prime-service-api cd prime-service-api/
2. java -jar target/prime-service-api-0.0.1-SNAPSHOT.jar

### Swagger
http://localhost:8080/swagger-ui/index.html

### How to get primes:
The url to his the rest endpoint is GET http://localhost:8080/primes/{limit} where limit is any integer between 2 and 1000000.
e.g. http://localhost:8080/primes/11 will return 
{
    "initial": 11,
    "primes": [
        2,
        3,
        5,
        7,
        11
    ]
}

### Postman collection: 
**Please refer to file: prime.postman_collection.json **

### Content negotiation:
Default response type is application/json
The content of the response can be changed to xml by adding header as below:
1. Accept=application/json - this is the default implementation.
2. Accept=application/xml - to get the response in xml format.

### Switching between service implementation:
In this project there are two services which provide the list of primes.
1. prime-service-default - this is the default service.
2. prime-service-apache - This uses apache library to determine is a number is prime.

### To use one of the above add header parameter as below:
1. serviceType=apache to use prime-service-apache
2. serviceType=default to use prime-service-default to explicitly specify to use prime-service-default.
####If the "serviceType" parameter is missing in the header then the default service, prime-service-default is used.

### Extension possibilities:
If there is a need to provide another service then simply add another module inline with prime-service-default.

### Caching
The services use ehcache to cache whether a number is prime.
