FROM openjdk:11

# Source path of jar file
ARG JAR_PATH

# Server port on which server will run
ARG SERVER_PORT

# Add jar in docker container
ADD ${JAR_PATH} docker-ews-server.jar

# Expose the port on which server will run
EXPOSE ${SERVER_PORT}

# Execute the jar to run server
ENTRYPOINT [ "java", "-jar", "docker-ews-server.jar" ]
