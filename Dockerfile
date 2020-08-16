FROM gradle:jdk8 as build
COPY . .
RUN gradle bootJar
RUN ls build/libs
RUN mkdir /root/jar && mv build/libs/lac-0.0.1.jar /root/jar/ && cd && rm -rf /root/app

FROM harisekhon/ubuntu-java
USER root
RUN apt-get update && ACCEPT_EULA=Y apt-get install ttf-mscorefonts-installer -y
COPY --from=build /root/jar/lac-0.0.1.jar .
EXPOSE 5000
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar -Dspring.profiles.active=local lac-0.0.1.jar"]