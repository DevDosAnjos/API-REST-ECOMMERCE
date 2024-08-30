FROM maven:3.8.5 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/E-Commerce-0.0.1.jar /app/E-Commerce-0.0.1.jar
ENTRYPOINT ["java","-jar","/app/ECommerce-0.0.1.jar"]