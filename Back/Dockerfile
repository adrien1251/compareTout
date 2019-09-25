FROM anapsix/alpine-java

RUN mkdir -p /opt/compareTout/back
COPY ./target/compareTout-back-0.0.1-SNAPSHOT.jar /opt/compareTout/back/app.jar
EXPOSE 8080

CMD ["java", "-jar", "/opt/compareTout/back/app.jar"]
