
# BookingSystem
_Welcome to The Black Cat Retirement Home
The obvious choice for all monster children who feel their old parents are getting… well, a
little too old to climb out of coffins, chase full moons, or brew curses without forgetting
the ingredients. Here we offer safe and cozy support for elderly monsters of every kind, whether they are:_
* _Mummies who need help keeping their bandages fresh_
* _Vampires who forget where they left their coffin_
* _Witches who accidentally turn themselves into cats a little too often_
* _Zombies who get lost on the way to the dining hall_
* _Werewolves who would rather play bingo than howl at the moon_

_At The Black Cat Retirement Home, your old monster parents enjoy:_
* _Soft coffins with ergonomic lids_
* _Nightly walks in a safely fenced forest_
* _Blood pudding every Thursday_
* _Quiet hours during the full moon_
* _The Cat’s own “purr therapy” for stressed undead_
* _So relax, dear monster child. We’ll take care of your parents — whether they’re dusty, undead, enchanted, or just delightfully decrepit._

_At The Black Cat, everyone is welcome… as long as they’re not afraid of a little darkness._


## Table of contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Installation](#installation)
* [FAQ](#faq)
* [Project Status](#project-status)
* [Authors](#authors)

## General information
BookingSystem is a Spring Boot backend application that allows customers to register, log in using HTTPSession, and manage their personal profile. <br><br>
The system provides functionality for booking rooms, viewing existing reservations, and validating room availability. <br><br>
It uses MySQL for data storage, JPA for persistence, and follows a clean layered architecture with controllers, services, and repositories. <br><br>
The application includes robust input validation, secure password handling, and structured error management to ensure a reliable and user‑friendly booking experience. <br><br>

## Technologies used
| Layer | Technology |
| --- | --- |
| Frontend | HTML, CSS, JavaScript, Thymeleaf |
| Backend | Java, Spring Boot, Spring MVC, Spring Validation |
| Business Logic Layer (Service) | Spring Services, Password hashing (Spring   Security Crypto), Booking logic, Customer logic |
| Data Access Layer (Repository) | Spring Data JPA, Hibernate, JPA Entities |
| Database | MySQL, HikariCP (connection pooling) |
| Authentication / Session Management | HTTPSession, Spring Security (session handling only) |
| Build & Dependency Management | Maven |
| IDE | IntelliJ IDE |
| Version Control | Git + GitHub |
| Error Handling / Cross‑cutting | Global Exception Handling, Logging (SLF4J/Logback) |
| Testing | JUnit, Mockito |



## Installation
##### __Follow these steps to install and run the BookingSystem project.__

### 1.  _Clone the repository in git console with_
bash git clone <your-repo-url>

### 2. _Open the project in IntelliJ IDEA_
1. You can either open the folder directly
2. use File → New → Project from Existing Sources.

_IntelliJ will automatically detect Maven and download all dependencies._

### 3. _Set up the MySQL database_
__Database requirements__

* Database name must be: bookingsystem
* MySQL must be running on:
    * localhost: 3306
    * Create the database manually if it does not exist:
    * sql 
        - CREATE DATABASE bookingsystem;

### 4. _Configure environment variables (Recommended)_
${\color{green}This \space is \space the \space safe \space method \space because \space credentials \space are \space not \space stored \space 
in \space the \space project.}$

* Go to: Run → Edit Configurations → BookingSystemApplication
* Click Modify Options
* Enable Environment Variables
* Add:
    * DB_USER=your_mysql_username; DB_PASS=your_mysql_password

_Your application.properties will then read these values automatically:_
* in properties
    - spring.datasource.username=${DB_USER}
    - spring.datasource.password=${DB_PASS}

### 5. (Not recommended) Hard‑coding credentials
${\color{red}Only \space use \space this \space if \space you \space understand \space the \space risks \space
— \space credentials \space may \space be \space pushed \space to \space GitHub}$

Replace in properties:
* spring.datasource.username=${DB_USER}
  - Replace DB_USER with your actual MySql username.
* spring.datasource.password=${DB_PASS}
  - Replace DB_PASS with your actual MySql password.

### 6. Application configuration
These settings are already included in application.properties:

```.properties
spring.application.name=BookingSystem

spring.datasource.url=jdbc:mysql://localhost:3306/bookingsystem?useSSL=false&serverTimezone=UTC

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.

format_sql=true

# makes Hibernate create/update tables automatically
ddl-auto=update 

show-sql=true prints SQL queries

# makes them readable
format_sql=true
```

#### 7. Run the application
Start the project by running BookingSystemApplication.java;
Spring Boot will start, connect to MySQL, and create the necessary tables.

## FAQ

#### How do I make a clone by Git Bash commando?

You can find all the information you need [here](https://docs.github.com/en/repositories/creating-and-managing-repositories/cloning-a-repository)

#### How do I make a clone in my IDE?

You can find all the information you need [here](https://blog.jetbrains.com/idea/2020/10/clone-a-project-from-github/) (for IntelliJ)


## Project status
Project is: _in progress_ 

## Authors

- [Beata Schleisner-Petersen](https://github.com/Beata-Scheisner-Petersen)
- [Daniel Evansson](https://github.com/SleepingFores7s)
- [Valeria Kostova](https://github.com/valeriakostova-source)

