# eShop

## How to run

cd into folder

Go to root folder where docker-compose.yml file is and run

### `docker-compose up`

Open [http://localhost:4200](http://localhost:4200) to view it in the browser.

In order to stops all containers if any container fail use

### `docker-compose up --abort-on-container-exit`

Run specific compose file compose 

### docker-compose -f docker-compose-images.yml up --abort-on-container-exit

## Links

### Discovery [http://localhost:8761/](http://localhost:8761/)

### Edge service [http://localhost:8080/](http://localhost:8080/)

##### - Access store-service items test url through edge service [http://localhost:8080/store-service/store/V1/items/test](http://localhost:8080/store-service/store/V1/items/test)

### UI [http://localhost:4200/](http://localhost:4200/)

### Zipkin [http://localhost:9411/zipkin/](http://localhost:9411/zipkin/)