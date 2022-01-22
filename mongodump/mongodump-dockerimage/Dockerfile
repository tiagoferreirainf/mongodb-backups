FROM adoptopenjdk/openjdk11:alpine

ARG JAR_FILE=target/dependency/app.jar

RUN apk upgrade --update && \
    apk add --update \
    dos2unix \
    bash \
    curl \
    make \
    ca-certificates \
    mongodb-tools

RUN  wget https://github.com/instoll/alpine-mongoshell/releases/download/0.1.0/migrate

RUN chmod 0755 migrate &&  mv migrate /usr/bin

COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]