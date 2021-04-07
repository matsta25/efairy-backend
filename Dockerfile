FROM openjdk:11.0-jre
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-Duser.timezone=Europe/Warsaw","-jar","/app.jar"]

# ./gradlew build -Dorg.gradle.java.home=/c/Program\ Files/Java/jdk-11.0.1/
# docker build -t matsta25.tk:5000/efairy-backend .
# docker push matsta25.tk:5000/efairy-backend

# http://matsta25.tk:5000/v2/_catalog
