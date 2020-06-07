rm *.jks
rm *.cer

keytool -genkey -alias keyalias -keyalg RSA -keystore localhost-identity.jks -deststoretype JKS -validity 1825 -keypass jkspass -storepass jkspass -dname "CN=localhost, OU=N/A, O=N/A, L=Bucharest, ST=Romania, C=RO correct?"
keytool -export -alias keyalias -keystore localhost-identity.jks -file localhost.cer -storepass jkspass
keytool -importcert -keystore truststore.jks -alias localhost -storepass jkspass -file localhost.cer -noprompt
