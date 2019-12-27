# Spring config service

## Create Encryption public/private key

In resource folder run:

`keytool -genkeypair -alias configAlias -keyalg RSA -dname "CN=Web Server,OU=Unit,O=Organization,L=City,S=State,C=US" -keypass keySecret -keystore configserver.jks -storepass storeSecret`

## Encrypt

`curl http://localhost:8888/encrypt -d mysecret`

## Further read

https://cloud.spring.io/spring-cloud-config/reference/html/#_key_management