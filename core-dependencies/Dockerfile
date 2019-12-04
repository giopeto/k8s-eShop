FROM maven:3.5-jdk-8

#########################
### build environment ###
#########################

FROM maven:3.5-jdk-8

COPY settings.xml /root/.m2/settings.xml

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY pom.xml /usr/src/app

RUN mvn -f /usr/src/app/pom.xml deploy
