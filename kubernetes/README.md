# How to run

Run following commands:

    k apply -f persistence-volumes/nfs-persistence-volumes.yaml
    k apply -f maria-db/maria-db.yaml

Wait for the database pot to be Running

Connect to the database and execute [initMariaDB.sql](maria-db%2FinitMariaDB.sql)

Start the rest of the services

    for f in applications/*; do kubectl apply -f $f; done

When sso service is up run [initSsoService.sql](maria-db%2FinitSsoService.sql) to add some test users and values

To be able to access ingress locally you must run the following command

    kubectl port-forward --namespace=ingress-nginx service/ingress-nginx-controller 8080:80

# Other commands

## Rollout restart

    kubectl rollout restart deployment/contact-service

To restart all pods for all statefulsets and deployments you can do the following

    kubectl get deploy,statefulset --all-namespaces | grep -v "NAME" | grep -v '^[[:space:]]*$' | awk '{print "kubectl rollout restart -n=" $1 " "$2}' | bash

## Scale

    kubectl scale --replicas=0 statefulset/maria-db --force

## Delete pods

    kubectl k delete pod maria-db-0 --force

Delete all pods in state Terminating 

This may happen when you have some nods down and you want to force deletion of those pods

    kubectl get po --all-namespaces | grep -v Running | grep -v Completed | grep -v Creating | awk '{print "kubectl delete pod " $2 " -n=" $1 " --force &"}' | bash