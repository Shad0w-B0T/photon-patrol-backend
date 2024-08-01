# Photon Backend applications

__About the project__

Photon Patrol is a pioneering project that combines the power of renewable energy with innovative technology. The main component of this project is a state-of-the-art solar cell that is connected to an Arduino microcontroller and corresponding attachments. This enables the solar cell to automatically track the sun, ensuring optimal energy production.

The front end of the system is developed using JavaScript with React and Tailwind CSS. These modern technologies enable a user-friendly and engaging web interface that allows users to access important data in real time. The interface will allow users to monitor information such as the energy generated, the current current and the voltage of the solar cell.

The backend of the system is developed in Java with the Spring Framework. This combination provides a robust and scalable infrastructure for processing data and implementing business logic. The backend will be the interface between the frontend and the hardware, enabling seamless communication and control of the Arduino microcontroller.

Overall, Photon Patrol will not only enable efficient use of renewable energy, but also provide an intuitive user experience that makes it easy for users to monitor and optimize their energy production.

__Technologies used__

Technology Stack: **Spring Boot** with **Swagger**, **Vite** with **React**, and **Tailwind CSS**
In our project, we've opted for a combination of modern technologies to develop a powerful and user-friendly
application.

__Spring Boot with Swagger:__

Spring Boot was chosen as the backend framework to create a robust, scalable, and lightweight server application. The
conventions of Spring Boot allow for rapid development, facilitating the implementation of best practices. Swagger has
been integrated to automate API documentation. This significantly simplifies communication between backend and frontend
teams. Swagger provides clear documentation of API endpoints, parameters, and response codes, easing development,
testing, and maintenance.

__Vite with React and Tailwind CSS:__

Vite was selected as the frontend build tool to enable fast development and efficient deployment. Vite offers quick
startup times and Hot Module Replacement (HMR), significantly reducing development time. React is used as the UI library
to create reactive and reusable components. Tailwind CSS was chosen as the CSS framework for quickly and easily styling
attractive user interfaces. Tailwind's Utility-First approach streamlines styling and allows for consistent visual
design.

__Why This Combination:__

The combination of Spring Boot with Swagger for the backend and Vite with React and Tailwind CSS for the frontend
strikes an ideal balance between performance, development productivity, and user experience. Clear API documentation
facilitates collaboration between backend and frontend teams, while the powerful tools of Vite and React enable rapid
development and an appealing user interface. The use of Tailwind CSS allows for consistent design and reduces styling
effort. Overall, these technologies create a modern, scalable, and user-friendly application that meets the needs of our
users.

__JUnit__

In the test environment, all support methods were written in-house and a test framework was not used in order to keep
the project smaller.
The F.I.R.S.T. principle was followed in the JUnit test, but the complete testing of the application takes more time
than we would have liked. This is due to the fact that there are now over 250 tests and the environment is emptied after
each test in order to obtain an optimal and unadulterated result.
The parallel execution of tests is also not possible at application, logic and database level, as the tests access
the same H2 instance. If the fact is neglected, the condition that the tests do not influence each other is also
violated, so the tests can only be executed sequentially.
Important
Before the test is carried out, it must be ensured that port 80 is free, as the test environment uses a fully-fledged
server and carries out the tests on this. If the port is blocked, the test will fail.

__Swagger__

Swagger can be reached under localhost/doc after starting the application.
Due to the implementation and use of annotations, all possible answers or response codes are displayed for all requests.
To save a little time and avoid the constant adjustments, the ControllerAdvice provides the answers to the errors.

__Breakdown of the project__

The project was divided into two sub-projects. There is a queue application and a web application.

```mermaid
flowchart TD
  schema("schema")
  database("database")
  logic("logic")
  queue-logic("queue-logic")
  queue-application("queue-application")
  web-logic("web-logic")
  web-application("web-application")
  web-application == utilised ==> web-logic
  queue-application == utilised ==> queue-logic
  web-logic == utilised ==> logic
  queue-logic == utilised ==> logic
  logic == utilised ==> database
  schema
```

---

## Testing

Due to the complexity of the application and the use of Postgres, InfluxDB and RabbitMQ, individual software components
are difficult to mock. The functional properties of InfluxDB and RabbitMQ are therefore difficult to replicate. So the
decision was made to build the applications as a tangible container. Since GitLab does not support Docker in Docker, the
containers were integrated as an additional service and thus managed by the CI/CD runner. Local containers are started
for each test, as the environment is rebuilt for each test class, this process takes a lot of time.

### Local

Due to the inertia of rabbitmq, runtime errors can occur when the individual test instances are shut down, but the tests
still run, the overall construct is then marked as faulty due to the implementation of intellij.

Dependencies:

- https://mvnrepository.com/artifact/org.testcontainers/testcontainers
- https://mvnrepository.com/artifact/org.testcontainers/junit-jupiter
- https://mvnrepository.com/artifact/org.testcontainers/postgresql
- https://mvnrepository.com/artifact/org.testcontainers/influxdb
- https://mvnrepository.com/artifact/org.testcontainers/rabbitmq

### Remote (GitLab CI/CD)

The environment is started once and runs quickly.

---

## Start Spring Java Application

### Version

Java Version: ``Java 17 (Temurin)`` was used for the development. I chose this version because of the lts support. It is
strongly recommended to use this version when setting up the project.

Check the java version, whether it is greater than or equal to Java 17. The Version can be checked with the
command `java -version`.

### Execute

By setting the parameter `--spring.config.location`, the location of the configuration can be defined, i.e. where the
file is stored. In this way, a config can be used outside the jar file.

```
java -jar <application-jar-name>.jar [--spring.config.location=<path_to_configuration>.properties]
```

**Example for startup:**

File structure

```
.
├── application.jar             # Spring application.
└── config                      # File with multiple configs.
    └── application.properties  # Unit tests
```

Command will look like:

> java -jar application.jar --spring.config.location=./config/application.properties

## Queue Application

The queue application is used to log in as a subscriber in order to access the data of the hardware participants.

### Configuration Parameters

#### System
| Field         | Value |
|---------------|-------|
| `server.port` | 8081  |

#### Logger
| Field                | Value |
|----------------------|-------|
| `logging.level.root` | INFO  |

#### SQL
| Field                        | Value                                     |
|------------------------------|-------------------------------------------|
| `spring.datasource.url`      | jdbc:postgresql://localhost:5432/postgres |
| `spring.datasource.username` | postgres                                  |
| `spring.datasource.password` | test                                      |

#### Hibernate
| Field                                         | Value                                   |
|-----------------------------------------------|-----------------------------------------|
| `spring.jpa.properties.hibernate.dialect`     | org.hibernate.dialect.PostgreSQLDialect |
| `spring.jpa.show-sql`                         | false                                   |
| `spring.jpa.properties.hibernate.format_sql`  | true                                    |
| `spring.jpa.hibernate.ddl-auto`               | update                                  |
| `spring.jpa.defer-datasource-initialization`  | true                                    |

#### InfluxDB
| Field                           | Value                      |
|---------------------------------|----------------------------|
| `application.influxdb.url`      | http://localhost:8086      |
| `application.influxdb.user`     | my-user                    |
| `application.influxdb.password` | my-super-secret-auth-token |
| `application.influxdb.database` | my-bucket                  |

#### RabbitMQ (MQTT)
| Field                                | Value                              |
|--------------------------------------|------------------------------------|
| `application.rabbit.uri`             | amqp://admin:instar@localhost:5672 |
| `application.rabbit.exchange`        | amq.topic                          |
| `application.rabbit.queue`           | photon_broadcast_channel           |
| `application.rabbit.key`             | .photon.device                     |

#### Swagger
| Field                                       | Value             |
|---------------------------------------------|-------------------|
| `springdoc.swagger-ui.path`                 | /doc              |
| `springdoc.swagger-ui.operationsSorter`     | alpha             |
| `springdoc.swagger-ui.tagsSorter`           | alpha             |
| `study.web.swagger.title`                   | Photon Patrol API |
| `study.web.swagger.version`                 | 1.0               |

#### Application
| Field                                 | Value  |
|---------------------------------------|--------|
| `application.heartbeat.delay`         | 50     |
| `application.security.active`         | true   |
| `application.security.username`       | photon |

## Web application

The web application provides the web interface for the frontend.

### Configuration Parameters

``app.security.hash`` store hash

``app.security.passwordStorage`` position of password.


## Testing
__Important__

Before testing the Docker Engine must be started.
The reason for this is that a new Docker container is started before each test to create a clean test environment that works smoothly in exchange with the database.