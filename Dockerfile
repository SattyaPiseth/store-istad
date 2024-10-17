FROM openjdk:17-alpine
RUN mkdir -p workspace
COPY build/libs/store-istad-0.0.1.jar /workspace
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=trigger","/workspace/store-istad-0.0.1.jar"]

