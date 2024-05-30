FROM gradle:7.6.4 AS BUILD
WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN gradle build

FROM openjdk:17-oracle
ENV ARTIFACT_NAME=api-gateway-0.0.1-SNAPSHOT.jar
WORKDIR /app
COPY --from=BUILD /app/build/libs/$ARTIFACT_NAME .

ENTRYPOINT exec java -jar ${ARTIFACT_NAME} --spring.profiles.active=production
