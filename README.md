# mattermost-plugin-exchange-ews-proxy

## Table of Contents

1. [Overview](#overview)
2. [Installation and Setup](#installation-and-setup)
3. [Getting started](#getting-started)

## Overview
A mattermost plugin to add capabilities for two-way interaction between Mattermost plugin and Microsoft Outlook Calendar.
This plugin acts as a proxy in-between that connects Mattermost Exchange MS-Calendar Plugin with Microsoft Outlook Calendar.
For a stable production release, please download the latest version from the Plugin Marketplace and follow [these instructions](#building-the-plugins) to install the plugin.

## Installation and Setup

### Platform and Tools
- Make sure you have following components installed:
  - [JDK 6](https://openjdk.java.net/install/) or later
  - [Gradle](https://gradle.org/install/)
  - [Docker](https://docs.docker.com/engine/install/)
  - Make

### Building the plugin

- Run the following commands to prepare a compiled, distributable plugin jar:
```bash
$ git clone git@github.com:Brightscout/mattermost-plugin-exchange-ews-proxy.git
$ cd mattermost-plugin-exchange-ews-proxy
$ ./gradlew build
```
- This will produce a `.jar` file in `/build/libs` directory that can be used to run server.

### Environment variables

Create a `.env` file in the root folder and add the following environment variables:

* **EWS_USERNAME** : Username of the service account.
* **EWS_PASSWORD** : Password of the service account.
* **EWS_DOMAIN** : Domain of exchange server.
* **EWS_EXCHANGE_SERVER_URL** : URL of exchange server.
* **EWS_SECRET_AUTH_KEY** : Key used for authentication between mattermost-plugin-exchange-ews-proxy and mattermost-plugin-exchange-mscalendar.
* **JAR_PATH** : Path of the jar file which will be used to create docker image.
* **SERVER_PORT** : Port on which server will run.

**Note**: When trying to run using java command, make sure to export these variables in the terminal.
![image](https://user-images.githubusercontent.com/85667960/155085353-7cd41275-9d10-41bc-9917-3eac23b26e43.png)

## Getting started

#### Try it out with Java

You'll need Java installed.

    java -jar build/libs/*.jar

To test that it works, open a browser tab at http://localhost:8080 .

#### Try it out with [Docker](https://www.docker.com/)

You'll need Docker installed.
	
    make build-server
    make run-server
