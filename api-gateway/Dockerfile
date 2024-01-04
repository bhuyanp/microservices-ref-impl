FROM amazoncorretto:17

ARG JARFILE=target/api-gateway*.jar

COPY ${JARFILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]