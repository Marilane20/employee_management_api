# 1. Étape de compilation (Build)
FROM maven:3.9.9-eclipse-temurin-21-alpine AS build
WORKDIR /app

# Copie du descripteur de dépendances pour mettre en cache les bibliothèques
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copie du code source et compilation du JAR (sans lancer les tests)
COPY src ./src
RUN mvn clean package -DskipTests

# 2. Étape d'exécution (Runtime léger)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Récupération du binaire JAR généré
COPY --from=build /app/target/*.jar app.jar


EXPOSE 8080

# Exécution du binaire avec support mémoire conteneur
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-jar", "app.jar"]