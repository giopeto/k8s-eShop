#########################
### build environment ###
#########################

FROM maven:3.5-jdk-8 AS build

RUN mkdir -p /usr/src/app
WORKDIR /usr/src/app

COPY src /usr/src/app/src  
COPY pom.xml /usr/src/app
COPY jenkins /usr/src/app/jenkins

RUN mvn -f /usr/src/app/pom.xml clean install -DskipTests


##################
### production ###
##################

FROM gcr.io/distroless/java  

COPY --from=build /usr/src/app/target/*.jar /usr/app/app.jar 

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/app/app.jar"] 
