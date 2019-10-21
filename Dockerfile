#pull base image
FROM openjdk:8-jdk-alpine

#maintainer 
MAINTAINER test@yahoo.com

#expose port 8080
EXPOSE 8080

#default command
CMD java -jar /data/product-app-0.1.0.jar

#copy jar file to docker image
ADD ./data/product-app-0.1.0.jar /data/product-app-0.1.0.jar
