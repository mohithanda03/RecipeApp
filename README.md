# Introduction
Assignment for CGI - Recipe Api

# recipeApi
RecipeApi

## General info
An api to manage the recipes. A user can perform below operations using recipeApi:
1. Read from file "receipe.json" and load in memory.
2. search all the available recipes.
3. search recipes based on ingredients.

## Part 2 - Log Analyser
1. Read the file "logFile-2018-09-10.log" and load in memory.
2. Display Top N errors based on Error Type in sorted order.

## Technologies
### Backend
This API has been developed using 
Java 17
Spring-boot 3.2.2
Maven

### Frontend
Angular 17

All the entities are mapped using the JPA.  
Used Docker composer to run api in the docker container.

## Instructions to run this api:
Build : mvn clean install
Run: docker-compose up
Prerequisite: Please make sure that docker desktop is running.

## Instruction to run UI
Command: cd recipe-app
Build: ng build
Run: ng serve

## Api Links
Health check using Spring actuator url : http://localhost:8080/actuator/health

## Endpoints
Swagger-ui url : http://localhost:8080/swagger-ui/index.html

Please refer to the postman collection file "CGI Recipe Api.postman_collection" for the sample requests.

END.