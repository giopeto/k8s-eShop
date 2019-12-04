# create project namespace(name=k8s-eshop)
k create -f ../k8s/namespace.yaml
# Set namespace k8s-eshop as default kubectl namespace
k config set-context --current --namespace=k8s-eshop
k create secret generic postgres-pwd --from-literal=password=S0nar_P0stgress_Pass -n k8s-eshop


k apply -f ../k8s/mongo-dep.yaml

k apply -f ../k8s/authentication-service-dep.yml

k apply -f ../k8s/e-shop-gateway-ingress.yml