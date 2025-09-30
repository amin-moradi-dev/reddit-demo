# Reddit Mini-Clone

A minimal Reddit-style web app where users register, log in, create posts, comment, and vote.

## Features
- Register, log in, log out
- Create/edit/delete your own posts
- Comment on posts
- Upvote/downvote
- Simple, clean UI

## Tech Stack
- Java 17, Spring Boot, Spring MVC, Spring Security
- Thymeleaf templates
- MySQL + JPA/Hibernate
- Maven

## Quick Start
1) Clone the repo
```bash
git clone https://github.com/<your-username>/reddit-mini-clone.git
cd reddit-mini-clone
```
2) Configure DB in `src/main/resources/application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/reddit_mini_clone
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```
3) Run
```bash
mvn spring-boot:run
```
App: http://localhost:8080

## Basic Routes
- `/login`, `/register`
- `/` home feed
- `/post/{id}` post details
