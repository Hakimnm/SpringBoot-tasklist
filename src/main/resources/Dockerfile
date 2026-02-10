FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY build/libs/*.jar application.jar

ENTRYPOINT ["java", "-jar", "application.jar"]
