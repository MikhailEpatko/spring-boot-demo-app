FROM maven:3.9.6-eclipse-temurin-17-focal AS build

WORKDIR /wd

COPY .. .

RUN mvn install -DskipTests

FROM bellsoft/liberica-openjdk-alpine:17

EXPOSE 8008

WORKDIR /opt

COPY --from=build /wd/target/farm-app-*.jar /opt/farm-app.jar

ENTRYPOINT ["java","-XX:MaxHeapSize=1g","-jar","/opt/farm-app.jar"]