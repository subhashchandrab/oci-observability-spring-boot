FROM openjdk:8-jdk
RUN addgroup --system spring && adduser --system spring -ingroup spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ADD apm/oracle-apm-agent oracle-apm-agent
ENTRYPOINT ["java","-javaagent:/oracle-apm-agent/bootstrap/ApmAgent.jar","-jar","/app.jar"]
