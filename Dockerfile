FROM adoptopenjdk/openjdk11:jdk-11.0.5_10-alpine as builder
ADD . /src
WORKDIR /src

