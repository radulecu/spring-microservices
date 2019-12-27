# Spring Microservices
[![travis-ci](https://travis-ci.org/radulecu/spring-microservices.svg?branch=master)](https://travis-ci.org/radulecu/spring-microservices)
## Using Microservices

This code demonstrates:

- how to handle service registration using Eureka (though Consul is also supported).
- how to use Ribbon client-side load-balancing to dispatch calls in various ways  using `DiscoveryClient`, using the Feign.
- how  to use Hystrix and the  Hystrix dashboard to manage problematic service-to-service calls.
- How to use Zuul to expose and aggregate backend microservices via an edge gateway.
- How to secure your microservices with
* OAuth2 sso for clients (although usually this may not be done in a Java microservices in chase of apps Web Apps based on JavaScript based technologies )
* ResourceServersSecurity for resource servers

## How to run :

mvn clean install

## How to open new Intellij project

Open -> <Project Root>/pom.xml -> Open file as project

## Further read
- Project is based on sources for course "Building Microservices with Spring Boot LiveLessons (Video Training)"
    * https://www.safaribooksonline.com/library/view/building-microservices-with/9780134192468/10_05.html?autoStart=True
        ** Lesson 8: Choreographing Microservices
            *** Using Microservices is based mostly on this chapter
        ** The course also goes through other interesting concepts like Spring Cloud Configuration, Batch Processing, Monitoring Microservices, Actuator, Logging
    * Git sources: https://github.com/livelessons-spring/building-microservices