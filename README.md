# Photon Backend applications

__About the project__

tbd.

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

  database == utilised ==> schema
```


## Queue Application

The queue application is used to log in as a subscriber in order to access the data of the hardware participants.

### Configuration Parameters
#### System
| Field         | Value |
|---------------|-------|
| `Server Port` | 8081  |

#### Application



#### SQL
| Field      | Value                                     |
|------------|-------------------------------------------|
| `url`      | jdbc:postgresql://localhost:5432/postgres |
| `username` | postgres                                  |
| `password` | test                                      |

#### Hibernate
| Field                             | Value                                   |
|-----------------------------------|-----------------------------------------|
| `dialect`                         | org.hibernate.dialect.PostgreSQLDialect |
| `show-sql`                        | false                                   |
| `format_sql`                      | true                                    |
| `ddl-auto`                        | update                                  |
| `defer-datasource-initialization` | true                                    |

#### InfluxDB
| Field      | Value                      |
|------------|----------------------------|
| `url`      | http://localhost:8086      |
| `user`     | my-user                    |
| `password` | my-super-secret-auth-token |
| `database` | my-bucket                  |

#### RabbitMQ (MQTT)
| Field             | Value                              |
|-------------------|------------------------------------|
| `uri`             | amqp://admin:instar@localhost:5672 |
| `username`        | photon                             |
| `exchange`        | amq.topic                          |
| `queue`           | photon_broadcast_channel           |
| `key`             | .photon.device                     |
| `heartbeat delay` | 50 ms                              |

#### Swagger
| Field     | Value             |
|-----------|-------------------|
| `title`   | Photon Patrol API |
| `version` | 1.0               |
| `path`    | /doc              |

## Web application

The web application provides the web interface for the frontend.

### Configuration Parameters

``app.security.hash`` store hash

``app.security.passwordStorage`` position of password.


## Testing
__Important__

Before testing the Docker Engine must be started.
The reason for this is that a new Docker container is started before each test to create a clean test environment that works smoothly in exchange with the database.