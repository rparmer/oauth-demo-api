FROM openjdk:11

ENV JAR_FILE spring-boot-sample-1.0-SNAPSHOT.jar

COPY target/${JAR_FILE} ./

EXPOSE 8080

CMD java -jar ${JAR_FILE}
