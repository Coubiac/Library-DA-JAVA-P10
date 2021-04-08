# Library-DA-JAVA-P10

## installation

L'application est composé de 9 microservices:

Edge Microservices:
- zuul-server
- Eureka
- config-server

Back Microservices:
- books
- emprunts
- reservation
- users

Front Microservices:
- batch
- appliweb

Pour lancer l'application, il faut lancer chaque microservice en allant dans le dossier correspondant au microservice et en executant les commande suivantes:

```
./mvnw install
./mvnw package
java -jar target/<nom du microservice>.jar
```

Par exemple:
```
java -jar target/books-2.0.jar
```
