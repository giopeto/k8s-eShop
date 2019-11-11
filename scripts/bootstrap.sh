# Enable prometheus addon
#microk8s.enable prometheus

# Manual k8s ingress controller
kubectl create -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/static/mandatory.yaml

# Jaeger Operator k8s setup
kubectl create namespace observability
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/crds/jaegertracing_v1_jaeger_crd.yaml
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/service_account.yaml
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/role.yaml
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/role_binding.yaml
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/operator.yaml



# git ignore filemode change (https://stackoverflow.com/questions/1257592/how-do-i-remove-files-saying-old-mode-100755-new-mode-100644-from-unstaged-cha)
git config core.filemode false

# create postgresql secret for sonar deployment
kubectl create secret generic postgres-pwd --from-literal=password=S0nar_P0stgress_Pass

# Deploy k8s-eShop application via kubernetes
kubectl apply -f ../k8s/
# Deploy SonarQube
kubectl apply -f ../k8s/k8s-sonarqube/