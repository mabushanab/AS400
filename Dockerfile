FROM openjdk:8-alpine


WORKDIR /
ADD target/dependency/webapp-runner.jar webapp-runner.jar
ADD target/app.war app.war
EXPOSE 8080
CMD java -jar  -Djava.security.egd=file:/dev/urandom webapp-runner.jar app.war --port 8080 
