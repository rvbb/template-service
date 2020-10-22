FROM openjdk:8-jdk-alpine
EXPOSE 8080

ENV APP_HOME=/usr/app/

WORKDIR $APP_HOME
COPY ./target/*.jar ./app.jar

CMD ["java","-jar","app.jar"]