# SSL component

Used to enable https and provide truststore for self signed certificates.

## Usage
* Same is for truststore and keystore dependency for ssl certificates
    ** add ro.rasel:ssl-component
    ** for trustStore include TrustStoreComponent class in SpringApplication.run()  source classes
    ** include ssl and sslTrustStore in configuration file depending if you want to enable ssl or access a ssl service (can be bot used)

## Example services using security
* bookmark-service
* contact-service
* passport-service
* client-service
* client-service-light
* security-sso-ui

## SSL security

### Generating keystore, certificate and truststore files command (for this demo it is automatically done by maven and groovy plugin)

keytool -genkey -alias jkslocalhostalias -keyalg RSA -keystore my.jks -deststoretype JKS -validity 1825 -keypass jkspass -storepass jkspass -dname "CN=localhost, OU=N/A, O=N/A, L=Bucharest, ST=Romania, C=RO"
keytool -genkey -alias jkscomputernamealias -keyalg RSA -keystore my.jks -deststoretype JKS -validity 1825 -keypass jkspass -storepass jkspass -dname "CN=%COMPUTERNAME%, OU=N/A, O=N/A, L=Bucharest, ST=Romania, C=RO"

keytool -export -alias jkslocalhostalias -keystore my.jks -file localhost.cer -storepass jkspass
keytool -export -alias jkscomputernamealias -keystore my.jks -file computername.cer -storepass jkspass

keytool -importcert -keystore my.truststore -alias jkslocalhostalias -storepass jkspass -file localhost.cer -noprompt
keytool -importcert -keystore my.truststore -alias jkscomputernamealias -storepass jkspass -file computername.cer -noprompt

The first command is to generate a jks keystore. It will be used to enable the SSL/TSL for the server.
Next a certificate for the keystore is generated.
Using the certificate we can nou create the truststore. The role of the trustore is to be able to use the SSL/TSL signed service.

### Viewing  keystore details

keytool -list -v -alias jksalias -keystore ./my.jks -storepass jkspass

openssl x509 -inform DER -outform PEM -in computername.cer -out computername.cer.pem

keytool -importkeystore -srckeystore my.jks -destkeystore computername.p12 -srcalias jkscomputernamealias -srcstoretype jks -deststoretype pkcs8 -keypass jkspass -storepass jkspass
openssl pkcs12 -in computername.p12 -out computername.pem



