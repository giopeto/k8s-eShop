# k8s-eShop

## How to run
- Install virtual box
- Install vagrant
- Clone project
    - `git clone https://github.com/giopeto/k8s-eShop.git`
- Navigate to vagrant-env folder
	- `cd k8s-eShop/vagrant-env`
- Run vagrant provisioning
	- `vagrant up`
- Vagrant user:password => vagrant:vagrant
- Run bootstrap.sh
	- `cd k8s-eShop/scripts && sh bootstrap.sh`
	
## Links
### k8s-eShop [http://k8s-eshop.io](http://k8s-eshop.io)
### Jaeger tracing [http://jaeger.k8s-eshop.io](http://jaeger.k8s-eshop.io)
### Jenkins CI/CD [http://jenkins.k8s-eshop.io](http://jenkins.k8s-eshop.io) user:pass admin:admin
### SonarQube Code Inspection [http://sonar.k8s-eshop.io/sonar](http://sonar.k8s-eshop.io/sonar) user:pass admin:admin
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