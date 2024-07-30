#
# Build queue application
#
FROM gradle:jdk17-alpine as queue-build

LABEL authors="photonteam"
LABEL project="photon/photon-backend"
LABEL tag="1.0.0"

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

RUN gradle --build-cache bootJar -x test

#
# Deploy queue application
#
FROM eclipse-temurin:17 as queue-application

# Define workir for app
WORKDIR /app

COPY --from=queue-build /app/photon-queue-application/build/libs/*.jar build.jar

ENTRYPOINT ["java", "-jar", "build.jar"]


#
# Build web application
#
FROM gradle:jdk17-alpine as webserver-build

LABEL authors="photonteam"
LABEL project="photon/photon-backend"
LABEL tag="1.0.0"

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
COPY photon-webserver-application ./photon-webserver-application

RUN gradle --build-cache bootJar -x test

#
# Deploy queue application
#
FROM eclipse-temurin:17 as webserver-application

# Define workir for app
WORKDIR /app

COPY --from=webserver-build /app/photon-webserver-application/build/libs/*.jar build.jar

ENTRYPOINT ["java", "-jar", "build.jar"]