FROM amazoncorretto:17.0.11
WORKDIR app

ARG JAR_FILE=build/libs/bob-chats-api.jar

COPY ${JAR_FILE} app.jar

ENV TZ=Asia/Seoul

EXPOSE 8080
EXPOSE 8081

CMD ["java", "-jar", "app.jar"]