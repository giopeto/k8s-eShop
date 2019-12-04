# k8s info

## Services

| Service | Port | NodePort | Type |
| ------- | ---- | -------- | ---- |
| authentication-service-svc | 8080 | 30101 | LoadBalancer |
| store-service-svc | 8081 | 30102 | LoadBalancer |
| files-service-svc | 8082 | 30103 | LoadBalancer |
| basket-service-svc | 8084 | 30105 | LoadBalancer |
| payment-service-svc | 8085 | 30106 | LoadBalancer |
| nodejs-socket-service-svc | 3000 | 30107 | LoadBalancer |
| ui-admin-svc | 80 | 30100 | LoadBalancer |
| ui-client-svc | 80 | 30104 | LoadBalancer |
| admin-service | 8083 | 30303 | LoadBalancer |
| nexus-svc | 80 | 30181 | LoadBalancer |
| sonar-svc | 80 | 31625 | LoadBalancer |
| jenkins-svc | 80 | 30123 | LoadBalancer |
| mongodb | 27017 | | ClusterIP |
| sonar-postgres | 5432 | | ClusterIP |
| jenkins-jnlp-svc | 50000 | | ClusterIP |

## Ingress
| Service | DNS |
| ------- | ---- |
| authentication-service-svc | k8s-eshop.io/api/authentication-svc |
| store-service-svc | k8s-eshop.io/api/store-svc |
| files-service-svc | k8s-eshop.io/api/files-svc |
| nodejs-socket-service-svc | k8s-eshop.io/api/nodejs-socket-svc |
| ui-client-svc | k8s-eshop.io/client |
| ui-admin-svc | k8s-eshop.io/admin |
| nexus-svc | nexus-svc.k8s-eshop.io |
| jenkins-svc | jenkins.k8s-eshop.io |
| sonar-svc | sonar.k8s-eshop.io |
| sonar-svc | sonar.k8s-eshop.io |
| spring-boot-admin-server.k8s-eshop.io | admin-service |