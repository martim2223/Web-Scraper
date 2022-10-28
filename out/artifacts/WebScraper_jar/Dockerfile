FROM eclipse-temurin

COPY * /tmp/

RUN mkdir -p /tmp/CSVFile

EXPOSE 8080
ENTRYPOINT ["java","-jar","/tmp/WebScraper.jar"]