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

### Configuration

The EWS Proxy is configured through the use of environment variables that are optionally provided via an env file (to the docker container) as follows:

```bash
EWS_DOMAIN=your-exchange-domain
EWS_EXCHANGE_SERVER_URL=https://ews.your-company.com
EWS_USERNAME=ServiceAccount
EWS_PASSWORD=<password>
EWS_SECRET_AUTH_KEY=<secret>
EWS_SUBSCRIPTION_LIFETIME_IN_MINUTES=30
JAR_PATH=build/libs/*.jar
SERVER_PORT=8080
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
| SERVER_PORT | Port on which the server listens.

### Run with Docker

The simplest way to run this service is to do so via Docker. A public [Docker image is available on Docker Hub](https://hub.docker.com/layers/mattermost-plugin-exchange-ews-proxy/brightscout/mattermost-plugin-exchange-ews-proxy/latest/images/sha256-f1172b134258a2bf9d75231ba4c7fd92c8161f7a4ffe4ec16adb3ea74b977e5c?context=explore).

**Pull the image:**

```bash
docker pull brightscout/mattermost-plugin-exchange-ews-proxy:latest
```

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
3. Ensure that the environment variables are exported to the shell.
4. Start the server

    ```bash
     java -jar mattermost-plugin-exchange-ews-proxy-<version>.jar
     ```

## Development

Make sure you have the following components installed:
  - [JDK 6+](https://openjdk.java.net/install/) or later
  - [Gradle](https://gradle.org/install/)
  - [Docker](https://docs.docker.com/engine/install/)
  - Make

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
