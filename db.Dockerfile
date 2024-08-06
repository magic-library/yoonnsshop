FROM mysql:latest

RUN apt-get update && apt-get install -y tcpdump

VOLUME /var/lib/mysql

EXPOSE 3306
