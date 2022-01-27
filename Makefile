build-server:
	@echo Starting build of docker image
	docker build -f Dockerfile -t docker-ews-server . --build-arg JAR_PATH=$(JAR_PATH) --build-arg SERVER_PORT=$(SERVER_PORT)

run-server:
	@echo Starting ews-server in a docker container
	docker run -p $(SERVER_PORT):$(SERVER_PORT) --env-file=.env docker-ews-server .
