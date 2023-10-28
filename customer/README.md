docker pull mysql:latest
docker run -p 3306:3306 -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:latest