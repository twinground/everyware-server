FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-11-jdk -y
LABEL authors="realisshomyang"

ENTRYPOINT ["top", "-b"]