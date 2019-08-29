FROM maven:3.5-jdk-8

#########################
### build environment ###
#########################

FROM maven:3.5-jdk-8

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml clean install