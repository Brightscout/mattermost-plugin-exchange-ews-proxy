# Mattermost Microsoft Exchange Calendar EWS Proxy

## Table of Contents

- [License](#license)
- [Overview](#overview)
- [Setup](#setup)
- [Development](#development)

## License

See the [LICENSE](./LICENSE) file for license rights and limitations.

## Overview

The Microsoft Exchange Calendar EWS Proxy is a JAVA-based intermediary service that enables the [Mattermost Microsoft Exchange Calendar Plugin](https://github.com/Brightscout/mattermost-plugin-exchange-calendar) to communicate with on-premise Microsoft Exchange servers.

## Setup

### Create an Exchange Service Account([Reference](https://www.cirrusinsight.com/knowledgebase/exchange-impersonated-sa))

1. Go to your exchange admin center.(https://your-exchange-server-url/ecp)
2. Click on 'recipients' in the navigation panel.
3. Click on the + icon and select the 'User mailbox' option to create the new service account.
4. Select the 'New user' option and complete the form.
5. Click on 'permissions' in the navigation panel.
6. Click on the + icon to add a new Role Group. Enter the values for Name and Description. Leave the 'Write scope' value set to 'Default’
7. Click on the + icon under 'Roles' and add 'ApplicationImpersonation’. Click 'OK' once it has been added to the list.
8. Click on the + icon under 'Members' and add the service account you created. Click 'OK' once it has been added to the list.
9. After completing the form, click on the 'Save' button and the new role group should be added to your list.

### Configuration

The EWS Proxy is configured through the use of environment variables that are optionally provided via an env file (to the docker container) as follows:

```bash
EWS_DOMAIN=your-exchange-domain
EWS_EXCHANGE_SERVER_URL=https://your-exchange-server-url
EWS_USERNAME=<service-account-username>
EWS_PASSWORD=<service-account-password>
EWS_SECRET_AUTH_KEY=<secret>
EWS_SUBSCRIPTION_LIFETIME_IN_MINUTES=30
JAR_PATH=build/libs/*.jar
SERVER_PORT=8080
SERVER_LOG_LEVEL=<log-levels>
```

| Variable | Description |
| ----------- | ----------- |
| EWS_DOMAIN | Domain of the Exchange Server.|
| EWS_EXCHANGE_SERVER_URL | URL of the Exchange Server.|
| EWS_USERNAME | Username of the Exchange service account.|
| EWS_PASSWORD | Password of the Exchange service account.|
| EWS_SECRET_AUTH_KEY | Secret used to authenticate API requests to this server.|
| EWS_SUBSCRIPTION_LIFETIME_IN_MINUTES | Length of time that Exchange will tolerate delivery failures before cancelling subscriptions for a user.|
| JAR_PATH | Path of the JAR file which is the server's executable.|
| SERVER_PORT | Port on which the server listens.|
| SERVER_LOG_LEVEL | Log levels for server logging. Possible values are INFO, DEBUG, ERROR, TRACE.

### Run with Docker

The simplest way to run this service is to do so via Docker. A public [Docker image](https://hub.docker.com/r/brightscout/mattermost-plugin-exchange-ews-proxy/tags) is available on Docker Hub.

**Pull the image:**

```bash
docker pull brightscout/mattermost-plugin-exchange-ews-proxy:latest
```
**Note-** Users can also pull using the specific tag version that is same as the Github's latest release tag. Example- `docker pull brightscout/mattermost-plugin-exchange-ews-proxy:<tagname>`

**Run the container:**

```bash
docker run --rm \
  -p 8080:8080  \
  --env-file=<path to env file> \
  brightscout/mattermost-plugin-exchange-ews-proxy:latest
```

### Run with Java

To run the JAR file directly on a host, do the following:

1. Ensure you have JDK 6 or later installed.
2. Go to the [releases page of this GitHub repository](https://github.com/Brightscout/mattermost-plugin-exchange-ews-proxy/releases) and download the JAR file from the latest release.
3. Ensure that the [environment variables](#configuration) are exported to the shell.
4. Start the server

    ```bash
     java -jar mattermost-plugin-exchange-ews-proxy-<version>.jar
     ```

## Development

Make sure you have the following components installed:

- [JDK 6](https://openjdk.java.net/install/) or later
- [Gradle](https://gradle.org/install/)
- [Docker](https://docs.docker.com/engine/install/)
- Make

... and you have the [environment variables](#configuration) exported to the shell.

### Build and Run the JAR

Run the following commands to prepare a distributable JAR:

```bash
git clone git@github.com:Brightscout/mattermost-plugin-exchange-ews-proxy.git
cd mattermost-plugin-exchange-ews-proxy
./gradlew build
```

This will produce a `.jar` file in `build/libs` directory that can be used to run the server as follows:

```bash
java -jar build/libs/*.jar
```

To validate that it is running, open `http://localhost:8080` in your browser.

### Build and Run via [Docker](https://www.docker.com/)

Run these commands after installing Docker.

  make build-server
  make run-server
