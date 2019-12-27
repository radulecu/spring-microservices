del localhost-identity.jks
del computername-identity.jks
del localhost.cer
del computername.cer
del truststore.jks

keytool -genkey -alias localhost -keyalg RSA -keystore localhost-identity.jks -deststoretype JKS -validity 1825 -keypass jkspass -storepass jkspass -dname "CN=localhost, OU=N/A, O=N/A, L=Bucharest, ST=Romania, C=RO correct?"
keytool -genkey -alias desktop-1gn7l5l -keyalg RSA -keystore computername-identity.jks -deststoretype JKS -validity 1825 -keypass jkspass -storepass jkspass -dname "CN=%COMPUTERNAME%, OU=N/A, O=N/A, L=Bucharest, ST=Romania, C=RO correct?"
keytool -export -alias localhost -keystore localhost-identity.jks -file localhost.cer -storepass jkspass
keytool -export -alias desktop-1gn7l5l -keystore computername-identity.jks -file computername.cer -storepass jkspass
keytool -importcert -keystore truststore.jks -alias localhost -storepass jkspass -file localhost.cer -noprompt
keytool -importcert -keystore truststore.jks -alias desktop-1gn7l5l -storepass jkspass -file computername.cer -noprompt
