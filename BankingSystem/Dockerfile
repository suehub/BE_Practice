# syntax=docker/dockerfile:1

# Comments are provided throughout this file to help you get started.
# If you need more help, visit the Dockerfile reference guide at
# https://docs.docker.com/go/dockerfile-reference/

# Want to help us make this template better? Share your feedback here: https://forms.gle/ybq9Krt8jtBL3iCk7

################################################################################

# Create a stage for resolving and downloading dependencies.
FROM eclipse-temurin:21-jdk-jammy as deps

WORKDIR /build

# Copy the gradlew wrapper with executable permissions.
COPY --chmod=0755 gradlew gradlew
COPY gradle/ gradle/

# Download dependencies as a separate step to take advantage of Docker's caching.
# Leverage a cache mount to /root/.gradle so that subsequent builds don't have to
# re-download packages.
RUN --mount=type=bind,source=build.gradle,target=build.gradle \
    --mount=type=cache,target=/root/.gradle ./gradlew --offline dependencies

################################################################################

# Create a stage for building the application based on the stage with downloaded dependencies.
FROM deps as package

WORKDIR /build

COPY ./src src/
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
RUN --mount=type=bind,source=build.gradle,target=build.gradle \
    --mount=type=cache,target=/root/.gradle \
    ./gradlew assemble -x test && \
    mv build/libs/*.jar build/app.jar

################################################################################

# Create a new stage for running the application that contains the minimal
# runtime dependencies for the application. This often uses a different base
# image from the install or build stage where the necessary files are copied
# from the install stage.
FROM eclipse-temurin:21-jre-jammy AS final

# Create a non-privileged user that the app will run under.
ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser
USER appuser

# Copy the executable from the "package" stage.
COPY --from=package build/build/app.jar app.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "app.jar" ]