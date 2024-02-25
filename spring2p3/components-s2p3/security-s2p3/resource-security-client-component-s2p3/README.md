# Security dependencies

To access any resource via SSO you need to get a token from the security service (see security-sso-auth-server module) and use it to get the resource.

In this chase we can have 2 types of secured services

- resource services
    * used for providing resources
    * to access it you (or the client who is accessing it) have to provide the token received from the security service
- client services
    * can be either a web application (javascript/angular)
    * has to authenticate
        ** in chase of simple spring boot rest/mvc service it is done via OAuth2 (@EnableOAuth2Sso)
        ** in chase of javascript you have to acquire the token from the security service and forward it in the header at future requests

## Usage
* Security for a resource server is enabled using security-sso-component by:
    ** using ro.rasel:ssl-component maven dependency
    ** include ResourceSecurityClientComponent class in SpringApplication.run() source classes
    ** default configuration by including security profile in configuration file (which ends up including application-security.yml
* To enable a client server instead of ResourceSecurityClientComponent class we will include WebSecurityClientComponent in SpringApplication.run() source classes

## Example services using security
* Resource servers:
    ** bookmark-service
    ** contact-service
    ** passport-service
* client service
    ** client-service
    ** client-service-light
    ** security-sso-ui

## Accessing a resource server
curl -k -X POST -vu acme:acmesecret https://localhost:9999/sso/oauth/token -H "Accept: application/json" -d "password=spring&username=jlong&grant_type=password&scope=openid&client_secret=acmesecret&client_id=acme"

You can request a new access token using the refresh token.

curl -k -X POST -vu acme:acmesecret https://localhost:9999/sso/oauth/token -H "Accept: application/json" -d "username=jlong&grant_type=refresh_token&refresh_token=<REFRESH_TOKEN>"

You can access a resource by running:

curl -k https://localhost:8082/v1/users/jlong/bookmarks -H "Authorization: Bearer <TOKEN>"
curl -k https://localhost:8083/v1/users/jlong/contacts -H "Authorization: Bearer <TOKEN>"
curl -k https://localhost:8084/v1/users/jlong/passport -H "Authorization: Bearer <TOKEN>"

If mutual tls is enabled you need to disable it for this test to work. alternatively you just need to set it to simple TLS (using property server.ssl.client-auth set to none or want on targeting server). Alternatively you can try to use curl providing the appropriate certificates.

Alternatively resource-gateway-service can be used.

curl -k https://localhost:9025/bookmark-service/v1/users/jlong/bookmarks -H "Authorization: Bearer <TOKEN>"
curl -k https://localhost:9025/contact-service/v1/users/jlong/contacts -H "Authorization: Bearer <TOKEN>"
curl -k https://localhost:9025/passport-service/v1/users/jlong/passport -H "Authorization: Bearer <TOKEN>"

## Configuring HPKP

For the certificate (.cer file) from ssl-component run following commands
openssl x509 -inform DER -in computername.cer -out computername.crt
openssl x509 -in computername.crt -pubkey -noout | openssl rsa -pubin -outform der | openssl dgst -sha256 -binary | openssl enc -base64

Returned value represents the pinning value

## Further read
- Securing Your Cloud-native Microservice Architecture in Spring
    * https://ordina-jworks.github.io/microservices/2017/09/26/Secure-your-architecture-part1.html
    * http://www.swisspush.org/security/2016/10/17/oauth2-in-depth-introduction-for-enterprises
        ** Also provides details about how to configure GlobalMethodSecurity
    * http://stytex.de/blog/2016/02/01/spring-cloud-security-with-oauth2/
    * https://piotrminkowski.wordpress.com/2017/02/22/microservices-security-with-oauth2/
- Keystore/Truststore
    * https://docs.spring.io/spring-cloud-dataflow/docs/1.1.0.M1/reference/html/getting-started-security.html
    * https://drissamri.be/blog/java/enable-https-in-spring-boot/
- Webinar Replay: Security for Microservices with Spring and OAuth2
    * https://spring.io/blog/2014/11/07/webinar-replay-security-for-microservices-with-spring-and-oauth2
- Security with spring: Baeldung
    * http://www.baeldung.com/security-spring
    * http://www.baeldung.com/rest-api-spring-oauth2-angularjs
- JWT (JSON Web Tokens)
    * https://jwt.io/
- Authentication and Authorisation
    * OAuth: https://oauth.net/
    * OpenID vs OAuth2 vs SAML https://spin.atomicobject.com/2016/05/30/openid-oauth-saml/
- HPKP (HTTP Public Key Pinning)
    * https://ordina-jworks.github.io/spring/2016/03/05/HTTP-Public-Key-Pinning-with-Spring-Security.html

