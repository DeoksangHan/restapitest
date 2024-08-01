FROM maven as build
COPY mvnw .
COPY pom.xml .
COPY src src
RUN mvn package -Dmaven.test.skip=true

FROM eclipse-temurin:19-jdk-alpine
COPY --from=build /target/restapitest-1.0.jar app.jar
CMD ["java", "-jar", "app.jar"]

