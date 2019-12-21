#########################
### build environment ###
#########################

FROM maven:3.5-jdk-8 AS build

COPY settings.xml /root/.m2/settings.xml

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app
COPY jenkins /usr/src/app/jenkins

RUN mvn -f /usr/src/app/pom.xml clean install -Pk8s -DskipTests

##################
### production ###
##################

FROM gcr.io/distroless/java  

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar 

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n","-jar","/usr/app/app.jar"]
