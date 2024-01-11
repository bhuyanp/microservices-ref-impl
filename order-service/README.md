## Setting Up Order Service
[< Go Back](../README.md)

This service uses MongoDB. Follow below steps to get the service up and running.

### MongoDB Docker Container Setup
Don't repeat if you have done this setup as part of product service.

<code>docker pull mongo</code>

<code>docker run -p 27017:27017 --name microservice-mongo -d mongo:latest
</code>

### Launch the application locally using maven
<code>cd order-service</code>

<code>mvn spring-boot:run</code>

### Service URL
http://localhost:8083