## Setting Up Customer Service
[< Go Back](../README.md)

This service uses MYSQL database. Follow below steps to get the service up and running.

### Docker

<code>docker pull mysql:8.0</code>

Using 8.0 due to flyway constraint

<code>docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:8.0</code>


### Launch the discovery server before stating customer
<code>cd discovery-server</code>

<code>mvn clean package</code>

<code>mvn spring-boot:run</code>

### Launch the application locally using maven
<code>cd customer-service</code>

<code>mvn clean package</code>

<code>mvn spring-boot:run</code>

### Discovery Service URL
Goto discovery server to find the customer service endpoint
http://localhost:8761

