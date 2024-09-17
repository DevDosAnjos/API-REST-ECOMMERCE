FROM maven:3.8.5 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/ECommerce-0.0.1.jar /app/ECommerce-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/ECommerce-0.0.1.jar"]