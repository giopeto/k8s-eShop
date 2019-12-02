# Enable prometheus addon
#microk8s.enable prometheus

# git ignore filemode change (https://stackoverflow.com/questions/1257592/how-do-i-remove-files-saying-old-mode-100755-new-mode-100644-from-unstaged-cha)
git config core.filemode false

# save credentials
git config credential.helper store

# Add maven settings.xml file
mkdir /home/vagrant/.m2
sudo chmod -R 777 /home/vagrant/.m2
cp /home/vagrant/k8s-eshop/resources/settings.xml /home/vagrant/.m2/settings.xml

# prepare folder for nexus k8s volume
mkdir /home/vagrant/persistent-volumes-k8s
sudo chmod -R 777 /home/vagrant/persistent-volumes-k8s
mkdir /home/vagrant/persistent-volumes-k8s/nexus
sudo chown -R 200:200 /home/vagrant/persistent-volumes-k8s/nexus

# Update vm.max_map_count for sonarqube/postgres
echo "vm.max_map_count=262144" | sudo tee -a /etc/sysctl.conf
# Update max_user_watches for IntelliJ files scan
echo "fs.inotify.max_user_watches=524288" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p

sudo sed -i "$ a 127.0.0.1	k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	nexus.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	jaeger.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	jenkins.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	sonar.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	spring-boot-admin-server.k8s-eshop.io" /etc/hosts

# create postgresql secret for sonar deployment
k create secret generic postgres-pwd --from-literal=password=S0nar_P0stgress_Pass

# Deploy k8s-eshop application via kubernetes
k apply -R -f ../k8s/