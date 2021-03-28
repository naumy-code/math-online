FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY ./target/api_gateway.jar api_gateway.jar
ENTRYPOINT ["java","-jar","/demojenkins.jar", "&"]