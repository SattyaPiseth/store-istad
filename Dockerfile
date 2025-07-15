FROM openjdk:17-alpine
RUN mkdir -p workspace
COPY build/libs/store-istad-0.0.1.jar /workspace
EXPOSE 8080
ENTRYPOINT ["java","-jar","-Dspring.profiles.active=dev","/workspace/store-istad-0.0.1.jar"]

#--------------------------
#FROM gradle:jdk21-ubi AS build
#WORKDIR /workspace
#COPY --chown=gradle:gradle . /workspace/
#
#RUN gradle clean build
#
#FROM gradle:jdk21-ubi
#WORKDIR /workspace
#COPY --from=build /workspace/build/libs/*.jar /workspace/store-istad.jar
#EXPOSE 8888
#ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=stage", "store-istad.jar"]
