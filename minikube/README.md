# Minikube

## Tools

### Kubectl

Install Kubectl details: https://kubernetes.io/docs/tasks/tools/install-kubectl/

Example:

    curl -LO https://storage.googleapis.com/kubernetes-release/release/`curl -s https://storage.googleapis.com/kubernetes-release/release/stable.txt`/bin/linux/amd64/kubectl
    chmod +x ./kubectl
    sudo mv ./kubectl /usr/local/bin/kubectl
    kubectl version --client

### Kubernetes/Minikube

#### Install Minikube
Minikube is installed and runs directly on a local Linux, macOS, or Windows workstation. However, in order to fully take advantage of all the features Minikube has to offer, a Type-2 Hypervisor should be installed on the local workstation, to run in conjunction with Minikube.
Alternatively you can also install docker and k8s will run on it.

    curl -Lo minikube https://storage.googleapis.com/minikube/releases/v1.12.2/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/

#### Start Minikube

    minikube start

or you can start it as a [multi-node][https://minikube.sigs.k8s.io/docs/tutorials/multi_node/]

    minikube start --nodes 4

#### Add Minikube node

    minikube nodde add

#### Check Minikube

    minikube status

    kubectl cluster-info

    kubectl get deploy,rs,po --all-namespaces

#### Stop Minikube

    minikube stop

#### Start Dashboard

    minikube dashboard
### Start Minikube and kubernetes proxy

	nohup kubectl proxy &
	xdg-open "http://localhost:8001/api/v1/namespaces"
	kubectl apply -f https://raw.githubusercontent.com/kubernetes/dashboard/v2.0.3/aio/deploy/recommended.yaml
	xdg-open "http://localhost:8001/api/v1/namespaces/kubernetes-dashboard/services/https:kubernetes-dashboard:/proxy/#/overview?namespace=default"
	TOKEN=$(kubectl -n kube-system describe secret default| awk '$1=="token:"{print $2}')
	kubectl config set-credentials kubernetes-admin --token="${TOKEN}"
	kubectl config view

It may take few minutes for the dashboard to load.

To authenticate use TOKEN from above.

#### Proxy URLs
* http://localhost:
* http://localhost:8001/api/v1
* http://localhost:8001/apis/apps/v1
* http://localhost:8001/healthz
* http://localhost:8001/metrics

#### Curl example to directly access the server (no proxy required)

    TOKEN=$(kubectl describe secret -n kube-system $(kubectl get secrets -n kube-system | grep default | cut -f1 -d ' ') | grep -E '^token' | cut -f2 -d':' | tr -d '\t' | tr -d " ")
    APISERVER=$(kubectl config view | grep https | cut -f 2- -d ":" | tr -d " ")
    curl $APISERVER --header "Authorization: Bearer $TOKEN" --insecure

## Usage

### Kubectl deployment

Get details about Pods, replicationSets, Deployments

    kubectl get deploy,rs,po

Create deployment

    kubectl create deployment mynginx --image=nginx: 1.15-alpine

Get details based on label

    kubectl get deploy,rs,po -l app=mynginx
    
Scale deployment

    kubectl scale deploy mynginx --replicas=3

Describe deployment

    kubectl describe deploy mynginx 

Rollout history

    kubectl rollout history deployment mynginx
    kubectl rollout history deployment mynginx --revision=1
    for i in $(kubectl rollout history deployment mynginx | awk '{print $1}' | grep -v REVISION | grep -v deployment); do kubectl rollout history deployment mynginx --revision=$i; done

Update image name/version

    kubectl set image deployment mynginx nginx=nginx:1.16-alpine

Rollback to previous revision

    kubectl rollout undo deployment mynginx --to-revision=1

### RBAC

TODO: WIP...