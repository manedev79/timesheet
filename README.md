# Timesheet

Track working hours, flex time and vacations.

## How to build

Use Gradle to build and run:  

```gradlew bootRun```

## Setup development environment

Intellij IDEA:  
You need to install the Lombok plugin.

## URLs 

- http://localhost:8080/h2-console, JDBC URL: `jdbc:h2:mem:testdb`
- http://localhost:8080/swagger-ui.html 

## Test

The project contains separate source sets for unit tests and integration tests.

To run all tests:

```gradlew test integrationTest```

## Configuration

The following properties can be configured using SpringBoot mechanisms, see https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html#boot-features-external-config

* dailyHours (Duration)

## Used links

- https://start.spring.io/
- https://gitignore.io/


## Maintainers

- manedev79
- JanLoebel
- joc0611
