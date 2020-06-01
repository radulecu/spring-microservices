mvn clean install -DskipTests -Pdocker
docker container ls -a | awk '{print $1}' | xargs docker container rm -f
docker image ls -a | awk '{print $3}' | xargs docker image rm -f
docker-compose up
