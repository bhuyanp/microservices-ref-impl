FROM amazoncorretto:17

ARG JARFILE=target/product*.jar

COPY ${JARFILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]