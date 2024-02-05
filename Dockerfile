FROM openjdk:21
EXPOSE 8080
WORKDIR /post-app

ADD target/post.jar post.jar
ENTRYPOINT ["java", "-jar", "post.jar"]