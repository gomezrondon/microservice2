# microservice2
Testing spring microservice framework with spring boot 2.3


## to execute (master branch)
1) generate the image:
```
gradle clean bootBuildImage
```
2) ejecute docker compose
```
docker-compose up -d
```
3) check eureka server
```
http://localhost:8761/
```
4) check gateway
```
http://localhost:8080/cars
http://localhost:8080/v2/cool-cars
```
