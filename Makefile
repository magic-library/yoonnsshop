# project variables
PROJECT_NAME := yoonnsshop
VERSION := 0.0.1

# JAR file path
JAR_FILE := build/libs/$(PROJECT_NAME)-$(VERSION)-SNAPSHOT.jar

# docker variables
DOCKER_IMAGE := $(PROJECT_NAME):$(VERSION)
DOCKER_CONTAINER := $(PROJECT_NAME)-container

# gradle build
.PHONY: build
build:
	./gradlew clean build

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
	docker build -t $(DOCKER_IMAGE) .

# execute docker-compose
.PHONY: docker-run
docker-run: docker-build
	docker-compose up -d

# stop docker-compose
.PHONY: docker-stop
docker-stop:
	docker-compose down

# status docker-compose
.PHONY: docker-ps
docker-ps:
	docker-compose ps

.PHONY: docker-logs-app
docker-logs-app:
	docker-compose logs -f app

# execute docker-compose(all)
.PHONY: all
all: build docker-run

# clean
.PHONY: clean
clean:
	./gradlew clean
	docker-compose down
	docker rmi $(DOCKER_IMAGE)