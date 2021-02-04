FROM gradle:jdk8 as build
COPY . .
RUN gradle bootJar
RUN ls build/libs
RUN mkdir /root/jar && mv build/libs/lac-0.0.1.jar /root/jar/ && cd && rm -rf /root/app

FROM openjdk:8u181-jdk-stretch
USER root
RUN apt-get update -y && apt-get install -y cabextract && apt-get install -y xfonts-utils
RUN wget http://ftp.de.debian.org/debian/pool/contrib/m/msttcorefonts/ttf-mscorefonts-installer_3.6_all.deb \
    && dpkg -i ttf-mscorefonts-installer_3.6_all.deb
COPY --from=build /root/jar/lac-0.0.1.jar .
EXPOSE 5000
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar -Dspring.profiles.active=local lac-0.0.1.jar"]