#
# Build queue application
#
FROM gradle:jdk17-alpine as queue-build

LABEL authors="Photon Group"
LABEL project="photon-queue"
LABEL tag="0.0.1"

WORKDIR /app

# Copy maven stuff
COPY gradle ./gradle
COPY build.gradle ./build.gradle
COPY gradlew ./gradlew
COPY gradlew.bat ./gradlew.bat
COPY settings.gradle ./settings.gradle

COPY schema ./schema
COPY database ./database
COPY queue-logic ./queue-logic
COPY queue-application ./queue-application

RUN gradle --build-cache bootJar
RUN gradle check

#
# Deploy queue application
#
FROM eclipse-temurin:21 as queue-application

# Define workir for app
WORKDIR /app

COPY --from=queue-build /app/queue-application/build/libs/*.jar build.jar

ENTRYPOINT ["java", "-jar", "build.jar"]


#
# Build web application
#
FROM gradle:jdk17-alpine as web-build

LABEL authors="Photon Group"
LABEL project="photon-web"
LABEL tag="0.0.1"

WORKDIR /app

# Copy maven stuff
COPY gradle ./gradle
COPY build.gradle ./build.gradle
COPY gradlew ./gradlew
COPY gradlew.bat ./gradlew.bat
COPY settings.gradle ./settings.gradle

COPY schema ./schema
COPY database ./database
COPY webserver-logic ./webserver-logic
COPY webserver-application ./webserver-application

RUN gradle --build-cache bootJar
RUN gradle check

#
# Deploy queue application
#
FROM eclipse-temurin:21 as webserver-application

# Define workir for app
WORKDIR /app

COPY --from=web-build /app/webserver-application/build/libs/*.jar build.jar

ENTRYPOINT ["java", "-jar", "build.jar"]