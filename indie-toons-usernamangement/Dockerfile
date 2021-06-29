FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=target/indie-toons-usernamangement-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} indie-toons-usernamanagement.jar
ENTRYPOINT ["java", "-jar" , "indie-toons-usernamanagement.jar"]
