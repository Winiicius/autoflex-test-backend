FROM maven:3.9.6-eclipse-temurin-17 AS build-package

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine

WORKDIR /app

COPY --from=build-package /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]