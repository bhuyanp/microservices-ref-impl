FROM amazoncorretto:17

ARG JARFILE=target/discovery*.jar

COPY ${JARFILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]