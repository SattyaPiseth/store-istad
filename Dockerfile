# FROM openjdk:17-alpine
# RUN mkdir -p workspace
# COPY build/libs/store-istad-0.0.1.jar /workspace
# EXPOSE 8080
# ENTRYPOINT ["java","-jar","-Dspring.profiles.active=trigger","/workspace/store-istad-0.0.1.jar"]

#--------------------------

# Stage 1: Build the application
FROM gradle:7.4.0-jdk17 as build
WORKDIR /workspace
COPY . .
RUN gradle build -x test

# Stage 2: Run the application
FROM openjdk:17-alpine
WORKDIR /workspace
COPY --from=build /workspace/build/libs/store-istad-0.0.1.jar /workspace/store-istad-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=trigger", "/workspace/store-istad-0.0.1.jar"]
