## Setting Up Customer Service
[< Go Back](../README.md)

This service uses MYSQL database. Follow below steps to get the service up and running.

### Docker
Using 8.0 due to flyway constraint

docker pull mysql:8.0

docker run -p 3306:3306 --name microservice-mysql-customer -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=microservice -d mysql:8.0

### Create Schema
Create a new schema called microservice.

mysql> CREATE schema microservice;

### Launch the discovery server before starting customer
<code>cd discovery-server</code>

<code>mvn clean package</code>

<code>mvn spring-boot:run</code>

### Launch the application locally using maven
<code>cd customer-service</code>

<code>mvn clean package</code>

<code>mvn spring-boot:run</code>

### Discovery Service URL
Goto discovery server to find the customer service endpoint
http://localhost:9761

