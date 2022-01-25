FROM openjdk:11

# Add jar in docker container
ADD build/libs/*.jar docker-ews-server.jar

# Export port on which server is running
EXPOSE 8080

# Export some environment variables
ENV EWS_USERNAME=${EWS_USERNAME}
ENV EWS_PASSWORD=${EWS_PASSWORD}
ENV EWS_DOMAIN=${EWS_DOMAIN}
ENV EWS_EXCHANGE_SERVER_URL=${EWS_EXCHANGE_SERVER_URL}
ENV EWS_SECRET_AUTH_KEY=${EWS_SECRET_AUTH_KEY}

# Execute the jar to run server
ENTRYPOINT [ "java", "-jar", "docker-ews-server.jar" ]
