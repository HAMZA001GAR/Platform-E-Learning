FROM openjdk:17-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

EXPOSE 8070

# Set environment variables
ENV DB_URL=jdbc:postgresql://db:5432/jittracking
ENV ACTIVE_PROFILE=${PROFILE}
ENV JAR_VERSION=${APP_VERSION}

# Run the application
CMD java -jar "jobintech-${JAR_VERSION}.jar" -Dspring.profiles.active="${ACTIVE_PROFILE}" -Dspring.datasource.url="${DB_URL}"
