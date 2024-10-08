# Stage 1: Build
FROM amazoncorretto:17 as builder

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test

RUN ls -la /app/build/libs/

# Stage 2: Run
FROM amazoncorretto:17

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar /app/app.jar

EXPOSE 8080

ENV TZ Asia/Seoul

ENTRYPOINT ["java","-jar","/app/app.jar"]
