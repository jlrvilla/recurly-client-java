FROM openjdk:8u201-jdk-alpine3.9

RUN apk add bash maven

WORKDIR /app/clients/java
COPY . .
RUN ./scripts/build
WORKDIR /app
