FROM eclipse-temurin:17-alpine as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM eclipse-temurin:17-alpine
RUN adduser -D -u 1000 java
COPY .db.properties application/

WORKDIR application
COPY --chown=java:java --from=builder application/dependencies/ ./
RUN true
COPY --chown=java:java --from=builder application/spring-boot-loader/ ./
RUN true
COPY --chown=java:java --from=builder application/snapshot-dependencies/ ./
RUN true
COPY --chown=java:java --from=builder application/application/ ./
USER 1000

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]