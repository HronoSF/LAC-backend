FROM harisekhon/ubuntu-java
USER root
RUN apt-get update && ACCEPT_EULA=Y apt-get install ttf-mscorefonts-installer -y
COPY build/libs/lac-0.0.1.jar .
EXPOSE 5000
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar lac-0.0.1.jar"]