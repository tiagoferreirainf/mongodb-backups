FROM adoptopenjdk/openjdk11:alpine

ARG JAR_FILE=target/dependency/app.jar

RUN apk upgrade --update && \
    apk add --update dos2unix bash

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]