# post-web-app

This project represents traditional post.
There are three types of clients:
- normal,
- vip,
- instant.

Vip clients are always before normal clients in queue. Instant clients are handling before all other clients.
There can only be one instant client since there is only one customer service window.
Normal and vip clients are handling for 20 seconds and instant client is handled for 60 seconds.
To register as vip or instant client, you have to pass correct pin code.

# Set-up
- Java 21,
- Spring Boot,
- Hibernate
- Maven,
- Docker,
- Lombok.

# Software design approach
- Domain Driven Design

# Design patterns
- Abstract factory
- Observer
- Builder
- Singleton

# How to install?

First of all fill `application.yml` with your database data.
Then generate .jar file using:
```
mvn clean package -DskipTests
```

# How to run?

After performing above command, fill missing data for db inside `docker-compose.yml` and run application with docker:
```
docker-compose up -d --build
```

If you want to use existing image of this application, uncomment this line in `docker-compose.yml`:
```
    image: pietrasfilip/post-web-app-backend:1.0
```
and comment:
```
    image: post_web_app
    build:
      context: .
      dockerfile: Dockerfile
```
Then fill database environment variables with:
```
    environment:
      MYSQL_DATABASE: post
      MYSQL_ROOT_PASSWORD: main
      MYSQL_USER: user
      MYSQL_PASSWORD: user1234
```
Then run in command line:
```
docker-compose up -d --build
```
Application image from dockerhub will be downloaded.

# Running unit tests

Simply perform below command:
```
mvn test
```

# REST API

Add client to queue:
```
/clients/add
```
Show queue:
```
/clients/queue
```
Monitor client by username:
```
/clients/monitor/username/:username
```
Monitor client by id:
```
/clients/monitor/id/:id
```