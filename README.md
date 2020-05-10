# graphql-spring-boot

The aim of this project is to experiment **GraphQL** features in order to evaluate its potentialities.

The project is a Spring Boot application exposing a GraphQL endpoint using **graphql-java**

The GrapQL Schema is generated automatically starting from the source code thanks to the **graphql-spqr** project

One of the features explored by this project is the ability to dynamically generate Entity Graphs and use them with Spring Data JPA (or whatever JPA framework) in order to optimize queries on DB doing only the joins that are required in order to fetch the data requested by the client side.

The dynamic Entity Graphs are build extracting the required fields and relations from GraphQL query and are used by Spring Data's repositories thanks to **spring-data-jpa-entity-graph** project.



## How to build the application

```
mvn clean package
```



## How to run the application

```
mvn spring-boot:run
```



## GraphQL Playground

You can interact with GraphQL endpoint and send some queries using GraphQL Playground exposed at this url:  http://localhost:8080/gui



## Dynamic Entity Graphs

Thanks to the ability to explicitly declare the required fields and relations in a GraphQL query, one of the features that are implemented in this project is the generation of dynamic Entity Graphs in order to perform the queries on the underlying DB having only the requided joins in order to fulfill the data structure required by client side.



## References



**GraphQL**   <img src="https://github.com/m-daros/graphql-spring-boot/blob/master/docs/graphql.png" alt="graphql" style="zoom:40%;" />

https://graphql.org/



**graphql-java**

https://github.com/graphql-java/graphql-java



**graphql-spqr**

https://github.com/leangen/graphql-spqr



**spring-data-jpa-entity-graph**

https://github.com/Cosium/spring-data-jpa-entity-graph



