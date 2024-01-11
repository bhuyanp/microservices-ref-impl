FROM amazoncorretto:17

ARG JARFILE=target/customer*.jar

COPY ${JARFILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]