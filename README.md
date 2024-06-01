# Movie Catalog
> Manage movies with this restful API

This is a simple Java Spring Boot project to learn about Spring Boot. This project implements authentication with Spring Security and implements spring boot's best practices with sevice and repository pattern. This project also uses hibernate as the ORM and Postgre SQL as the database.

## Installing / Getting started

```shell
git clone https://github.com/KevinChristian30/movie-catalog
```

The command above will clone the repository.

### Initial Configuration

```shell
mvn dependency:resolve
```
The command above will install the required maven dependencies.
You also need do make a file to configure environment variables.

### Running the application

```shell
mvn spring-boot:run
```
The command above will run the application with maven.

## Features
* Authentication with Spring Security (JWT Token).
* Entity relationships with Hibernate and Java Persistence API.
* Endpoints to manage Users, Movies, Genres.
* Managers can manage uers, movies, movie schedules, etc.
* Users can wishlist movies.
* Role based access control for managers and studios.
* Custom validation using custom annotations.
* Logging with Aspect Oriented Programming.
* Avoided bloated function definitions with Data Transfer Objects.
