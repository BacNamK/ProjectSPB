FROM gradle:9.0-jdk25 AS build

WORKDIR /workspace
COPY Backend/gradlew Backend/gradlew.bat Backend/settings.gradle Backend/build.gradle ./Backend/
COPY Backend/gradle ./Backend/gradle
RUN chmod +x Backend/gradlew
RUN ./Backend/gradlew -p Backend dependencies --no-daemon

COPY Backend/src ./Backend/src
RUN ./Backend/gradlew -p Backend bootJar --no-daemon

FROM eclipse-temurin:25-jre

WORKDIR /app
COPY --from=build /workspace/Backend/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
