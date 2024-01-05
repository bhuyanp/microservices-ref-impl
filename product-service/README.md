## Setting Up Product Service
[< Go Back](../README.md)

This service uses MongoDB. Follow below steps to get the service up and running.

### MongoDB Docker Container Setup

<code>docker pull mongo</code>

<code>docker run -p 27017:27017 --name microservice-mongo -d mongo:latest
</code>

### Launch the application locally using maven
<code>cd product-service</code>

<code>mvn spring-boot:run</code>

### Service URL
http://localhost:8082
