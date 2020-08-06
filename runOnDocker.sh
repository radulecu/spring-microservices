(cd components/ssl-component/src/main/resources; bash createDockerCertificates.sh)
mvn clean install -DskipTests -Pdocker
docker container ls -a | grep springmicroservices | awk '{print $1}' | xargs docker container rm -f
docker image ls -a | grep springmicroservices | awk '{print $3}' | xargs docker image rm -f
docker-compose up
