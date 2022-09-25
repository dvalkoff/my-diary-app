FROM openjdk:17.0
COPY target/my-diary-app-0.0.1-SNAPSHOT.jar my-diary-app-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/my-diary-app-0.0.1-SNAPSHOT.jar"]