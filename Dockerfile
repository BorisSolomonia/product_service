FROM maven:latest
WORKDIR /product_service

COPY . /product_service
COPY . /src/main/resources/application.yaml
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/product_service/target/product_service-0.0.1-SNAPSHOT.jar"]