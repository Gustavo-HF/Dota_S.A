#Etapa 1 - build
FROM maven: 4.0.0-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#Etapa 2 - Execução
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar apr.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

