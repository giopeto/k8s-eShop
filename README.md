# k8s-eshop

## How to run
- Install virtual box
- Install vagrant
- Clone project
    - `git clone https://github.com/giopeto/k8s-eshop.git`
- Navigate to vagrant-env folder
	- `cd k8s-eshop/vagrant-env`
- Run vagrant provisioning
	- `vagrant up`
- Vagrant user:password => vagrant:vagrant
- Run bootstrap.sh
	- `cd k8s-eshop/scripts && sh bootstrap.sh`

## Links
### k8s-eshop client application [http://k8s-eshop.io](http://k8s-eshop.io/client)
### k8s-eshop admin application [http://k8s-eshop.io](http://k8s-eshop.io/admin)
### Jaeger tracing [http://jaeger.k8s-eshop.io](http://jaeger.k8s-eshop.io)
### Jenkins CI/CD [http://jenkins.k8s-eshop.io](http://jenkins.k8s-eshop.io)
### SonarQube Code Quality and Security [http://sonar.k8s-eshop.io/sonar](http://sonar.k8s-eshop.io/sonar) user:pass admin:admin
### Nexus repository [http://nexus.k8s-eshop.io](http://nexus.k8s-eshop.io) user:pass admin:admin123
### Grafana run `kubectl cluster-info` and get grafana url

## Optional setup
- Build maven-parent project
	- `cd maven-parent`
    - Get nexus server ip address
	- `kubectl get svc nexus-svc -o jsonpath='{.spec.clusterIP}'`
    - Build parent project
	- `sudo docker build --add-host=nexus.k8s-eshop.io:<NEXUS-SERVER-IP-ADDRESS> .`
- Get grafana default password
	- `cat /var/snap/microk8s/current/credentials/basic_auth.csv`
	
	MGJBOFh...,admin,admin,"system:masters"

	user is admin pass is first string(MGJBOFh...)
- Remote debug

Add debug port to service definition:
  ```
  ports:
    - name: store-service-web
      protocol: "TCP"
      port: 8081
      targetPort: 8081
      nodePort: 30102
    - name: store-service-debug
      protocol: "TCP"
      port: 5005
      targetPort: 5005
      nodePort: 30110   
   ```

- Change entrypoint in Dockerfile(add "-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n"):
	- `ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,address=5005,server=y,suspend=n","-jar","/usr/app/app.jar"]`
	
- forward 5005 port
	- `kubectl port-forward <NODE_NAME>  5005:5005`

## Run apps on localhost (dev/debug purpose)

### Backend apps
1. Get k8s activemq-svc and mongodb services CLUSTER-IP

```
vagrant@e-shop-microk8s-ubuntu ~ $ kubectl  get svc activemq-svc mongodb
NAME           TYPE           CLUSTER-IP        ... ...   
activemq-svc   LoadBalancer   10.152.183.84     ... ...
mongodb        ClusterIP      10.152.183.37     ... ...
```

2. Change application-local.properties
```
spring.data.mongodb.host=[CLUSTER-IP-MONGO] # eq spring.data.mongodb.host=10.152.183.37
...
spring.activemq.broker-url=tcp://[CLUSTER-IP-ACTIVEMQ]:61616 # eq spring.activemq.broker-url=tcp://10.152.183.84:61616 !!! Keep the port 61616 here
```

### Frontend apps

3. Add appropriate key in proxy.conf.json. Existing key "/api/*" need to be last, otherwise newly added key can't be fired
3.1 Authentication service key:
```
  "/api/authentication-svc/*": {
    "target": "http://localhost:8080",
    "secure": false,
    "logLevel": "debug",
    "pathRewrite": {
      "^/api/authentication-svc": "http://localhost:8080"
    }
  }
```
3.2 Store service key:
```
  "/api/store-svc/*": {
    "target": "http://localhost:8081",
    "secure": false,
    "logLevel": "debug",
    "pathRewrite": {
      "^/api/store-svc": "http://localhost:8081"
    }
  }
```
3.3 Files service key:
```
  "/api/files-svc/*": {
    "target": "http://localhost:8082",
    "secure": false,
    "logLevel": "debug",
    "pathRewrite": {
      "^/api/files-svc": "http://localhost:8082"
    }
  }
```
***** Use:
```
{
  "/api/files-svc/*": {
    ...
  },
  "/api/*": {
    ...
  }
}
```
***** Don't:
```
{
  "/api/*": {
    ...
  },
  "/api/files-svc/*": {
    ...
  }
}
```


## microk8s useful commands
********** To start **********

1. check node
kubectl get nodes

2. if status is not started run
microk8s.start


********** Dashboard UI **********

microk8s.enable dns dashboard

microk8s.kubectl get all --all-namespaces

Get dashboard url
get service/kubernetes-dashboard IP and PORT ang go to https://{IP}:{PORT}

Get token
token=$(microk8s.kubectl -n kube-system get secret | grep default-token | cut -d " " -f1) && microk8s.kubectl -n kube-system describe secret $token


********** Essential microk8s integrated commands **********

microk8s.status: Provides an overview of the MicroK8s state (running/not running) as well as the set of enabled addons
microk8s.enable: Enables an addon
microk8s.disable: Disables an addon
microk8s.kubectl: Interact with kubernetes
microk8s.config: Shows the kubernetes config file
microk8s.istioctl: Interact with the istio services. Needs the istio addon to be enabled
microk8s.inspect: Performs a quick inspectio of the MicroK8s intallation. Offers hints on what
microk8s.reset: Resets the infrastructure to a clean state
microk8s.stop: Stops all kubernetes services
microk8s.start: Starts MicroK8s after it is being stopped

test
[![test](https://j.gifs.com/OMX89B.gif)](https://www.youtube.com/)