FROM adoptopenjdk/openjdk11:debian
ADD . /src
WORKDIR /src
EXPOSE 80 8080 81
ENTRYPOINT ["java", "-jar", "online-calculation/target/metrics-online-calculation.jar", "--server.port=80", "--management.server.port=81"]
