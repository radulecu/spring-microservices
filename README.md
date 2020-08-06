# Spring Microservices

[![travis-ci](https://travis-ci.org/radulecu/spring-microservices.svg?branch=master)](https://travis-ci.org/radulecu/spring-microservices)

The purpose of the project is to demonstrate multiple ways in which you can create microservices.
The project is currently based on Java, Maven, Spring Boot and Spring Cloud.
The project also a WIP for developing alternatives like containerisation and kubernetes deployment.

## Using Microservices

This code demonstrates:

- how to handle service registration using Eureka (though Consul is also supported).
- how to use Ribbon client-side load-balancing to dispatch calls in various ways  using `DiscoveryClient`, using Feign.
- how  to use Hystrix and the  Hystrix dashboard to manage problematic service-to-service calls.
- How to use Zuul to expose and aggregate backend microservices via an edge gateway.
- How to secure your microservices with
* OAuth2 sso for clients (although usually this may not be done in a Java microservices in chase of apps Web Apps based on JavaScript based technologies )
* ResourceServersSecurity for resource servers

## Tools

### Docker

Details: https://docs.docker.com/engine/install/ubuntu/

WIP...mini

### Minikube and Kubectl

See more details [here](minikube/README.md).

## How to build

mvn clean install

## How to open new Intellij project

This readme file describes how to run using Intellij on a Linux OS.
For Eclipse or alternative IDE applications the configuration should be similar.
For Windows usage of a bash tool (git-bash) is recommended. 

Open -> <Project Root>/pom.xml -> Open file as project

## How to run locally

run config.sh to init ssl for local
Start needed services in your IDE

To check everything works fine run services
* EurekaServiceApplication :8761/
* GatewayServiceApplication :9024/
* SsoAuthServiceApplication :9999/
* BookmarkServiceApplication or ContactServiceApplication

Go to https://localhost:9024/swagger-ui.html. 
On the sso page use user 'jlong' and password 'spring'.
The Swagger page should load properly.

## How to start in Docker

Execute runOnDocker.sh

## How to check services are working locally

    cd postman-collections
    npm install
    npm test
   
Because of some redirect conflicts between Gateway and Security services GatewayServiceApplication is not yet working properly when started into a Docker container. 
The issue is that when SSO service redirects back the caller after authentication, it does so on the url provided internally by the Gateway service, not the one outside the containers.

The postman scripts work through ResourceGatewayServiceApplication which requires Authorisation provided as a bearer token. 
The token has to be acquired from sso service and used to access the Resource Gateway service as specified in [SSO client component Readme](components/sso-client-component/README.md).

## Further read
- Project is based on sources for course "Building Microservices with Spring Boot LiveLessons (Video Training)"
    * https://www.safaribooksonline.com/library/view/building-microservices-with/9780134192468/10_05.html?autoStart=True
        ** Lesson 8: Choreographing Microservices
            *** Using Microservices is based mostly on this chapter
        ** The course also goes through other interesting concepts like Spring Cloud Configuration, Batch Processing, Monitoring Microservices, Actuator, Logging
    * Git sources: https://github.com/livelessons-spring/building-microservices

[https://minikube.sigs.k8s.io/docs/tutorials/multi_node/]: https://minikube.sigs.k8s.io/docs/tutorials/multi_node/