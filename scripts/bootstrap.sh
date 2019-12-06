# create project namespace(name=k8s-eshop)
k create -f /home/vagrant/k8s-eshop/k8s/namespace.yaml
# Set namespace k8s-eshop as default kubectl namespace
k config set-context --current --namespace=k8s-eshop

# Enable prometheus addon
#microk8s.enable prometheus

# Add maven settings.xml file
mkdir /home/vagrant/.m2
sudo chmod -R 777 /home/vagrant/.m2
cp /home/vagrant/k8s-eshop/resources/settings.xml /home/vagrant/.m2/settings.xml

# prepare folder for nexus k8s volume
mkdir /home/vagrant/persistent-volumes-k8s
mkdir /home/vagrant/persistent-volumes-k8s/nexus
sudo chmod -R 777 /home/vagrant/persistent-volumes-k8s/nexus
sudo chown -R 200:200 /home/vagrant/persistent-volumes-k8s/nexus
sudo chmod -R 777 /home/vagrant/persistent-volumes-k8s

# Update vm.max_map_count for sonarqube/postgres
echo "vm.max_map_count=262144" | sudo tee -a /etc/sysctl.conf
# Update max_user_watches for IntelliJ files scan
echo "fs.inotify.max_user_watches=524288" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p

# Prevent npm from creation of symbolic links
npm config set bin-links false

sudo sed -i "$ a 127.0.0.1	k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	admin.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	service.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	nexus.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	jaeger.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	jenkins.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	sonar.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	spring-boot-admin-server.k8s-eshop.io" /etc/hosts

# create postgresql secret for sonar deployment
k create secret generic postgres-pwd --from-literal=password=S0nar_P0stgress_Pass -n k8s-eshop

# Deploy k8s-eshop application via kubernetes
k create -R -f /home/vagrant/k8s-eshop/k8s/