## Setting Up Customer Service
[< Go Back](../README.md)

This service uses MYSQL database. Follow below steps to get the service up and running.

### Docker

<code>docker pull mysql:latest</code>

<code>docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:latest</code>

### Launch the application locally using maven
<code>cd customer-service</code>

<code>mvn spring-boot:run</code>

### Service URL
http://localhost:8081

[comment]: <> (### Launch the application as a docker container)

[comment]: <> (mvn clean install)

[comment]: <> (docker build -t customer-service .)


