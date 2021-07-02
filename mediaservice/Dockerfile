FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/mediaservice-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} mediaservice-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar" , "mediaservice-0.0.1-SNAPSHOT.jar"]