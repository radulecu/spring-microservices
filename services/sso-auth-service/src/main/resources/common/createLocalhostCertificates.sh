# if CN is "localhost" thee services should be on thee same machine
rm *.jks
rm *.cer

keytool -genkey -alias jwkalias -keyalg RSA -keystore jwk.jks -deststoretype JKS -validity 1825 -keypass jwkpass -storepass jwkpass -dname "CN=https://localhost:9999/sso, OU=N/A, O=N/A, L=Bucharest, ST=Romania, C=RO"

