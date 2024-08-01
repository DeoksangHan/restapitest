FROM eclipse-temurin:17

WORKDIR /app
COPY target/restapitest-1.0.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]