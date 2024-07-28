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

COPY photon-schema ./photon-schema
COPY photon-database ./photon-database
COPY photon-logic ./photon-logic
COPY photon-queue-logic ./photon-queue-logic
COPY photon-queue-application ./photon-queue-application

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

COPY photon-schema ./photon-schema
COPY photon-database ./photon-database
COPY photon-logic ./photon-logic
COPY photon-webserver-logic ./photon-webserver-logic
COPY photon-ebserver-application ./photon-webserver-application

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