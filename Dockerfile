FROM bellsoft/liberica-openjdk-alpine:11.0.5
COPY build/libs/lac-0.0.1.jar .
ENTRYPOINT ["sh", "-c"]
CMD ["java -jar lac-0.0.1.jar"]