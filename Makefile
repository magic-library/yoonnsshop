# project variables
PROJECT_NAME := yoonnsshop
VERSION := $(shell grep projectVersion gradle.properties | cut -d'=' -f2)

# JAR file path
JAR_FILE := build/libs/$(PROJECT_NAME)-$(VERSION)-SNAPSHOT.jar

# docker variables
DOCKER_CONTAINER := $(PROJECT_NAME)-container

# gradle build
.PHONY: build
build:
	./gradlew clean build -x test

# check jar file
.PHONY: check-jar
check-jar:
	@if [ ! -f $(JAR_FILE) ]; then \
  		echo "JAR file이 존재하지 않습니다. 빌드를 먼저 수행해주세요."; \
		exit 1; \
 	fi

# build docker image
.PHONY: docker-build
docker-build: check-jar
	docker build -t $(PROJECT_NAME):latest .

# execute docker compose
.PHONY: docker-run
docker-run: docker-build
	docker compose --env-file .env up -d

.PHONY: docker-run-without-app
docker-run-without-app:
	APP_HOST=host.docker.internal:8080 docker compose --env-file .env up -d db redis prometheus grafana cadvisor

# stop docker compose
.PHONY: docker-down
docker-down:
	docker compose down

# status docker compose
.PHONY: docker-ps
docker-ps:
	docker compose ps

.PHONY: docker-logs-app
docker-logs-app:
	docker compose logs -f app

# execute docker compose(all)
.PHONY: all
all: build docker-run

.PHONY: test
test: ./gradlew test

# clean
.PHONY: clean
clean:
	./gradlew clean
	docker compose down
	docker rmi $(DOCKER_IMAGE)

.PHONY: jar
jar:
	./gradlew bootJar

.PHONY: run
run:
	./gradlew bootRun