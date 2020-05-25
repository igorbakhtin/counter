FROM openjdk:8-jre
WORKDIR /app
VOLUME ["/app"]
COPY maven/app.jar app.jar
RUN sh -c 'touch app.jar'
CMD ["java", "-jar", "app.jar"]
