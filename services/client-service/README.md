# Client service

## Description
Basic resource client used for exposing data through for clients which access data directly (e.g. from a web browser).
This is used as a proxy to not allow accessing rest data directly and can be used for additional processing before providing data to the user.


## Functionality
Eureka and ssl are enabled similarly to bookmark service. For more details go to bookmark-service/README.adoc

Compared to bookmark-service security is enabled through sso client because we want to be able to access data through for example the browser.
As a result the browser will redirect the user to the authentication and authorisation service for login.

To enable this compared to the bookmark service instead of ResourceSecurityClientComponent class we will include WebSecurityClientComponent in SpringApplication.run() source classes

Additional functionality include:

- Feign to enable annotation based rest defined calls to other rest (micro)services.
- Zuul proxy to make requests to other microservices through the proxy (e.g. <url>/bookmark-service/bookmarks/ which forwards the request to bookmark-service).
  See also client-service-light.
- Hystrix for fallback methods that return a default value instead of throwing an error in chase the server to which a request is made is down
