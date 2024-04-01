FROM openjdk:21-jdk-slim
COPY build/libs/shop-sale-service-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]