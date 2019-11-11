echo -e '\n microk8s-ubuntu \n'

apt-get update

# Install kubernetes (https://microk8s.io/docs/)
echo -e 'Install snapd and kubernetes (https://microk8s.io/docs/)'
sudo dpkg --configure -a
sudo apt update -y
sudo apt upgrade -y
sudo apt dist-upgrade -y
sudo apt install snapd

sudo snap install microk8s --classic
sudo usermod -a -G microk8s vagrant

echo -e 'Install nodejs 12.x, n and npm'
# Install curl, nodejs 12.x, n and npm
sudo apt-get install -y curl
curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo apt-get install -y build-essential
sudo npm install -g npm
sudo npm install -g n

# Install java 11, maven, intellij-idea-community, chromium, visual studio code and sublime
sudo apt install openjdk-11-jdk -y
sudo apt install maven -y
sudo snap install intellij-idea-community --classic
sudo snap install chromium
sudo snap install code --classic
sudo snap install sublime-text --classic

# Install docker (https://unix.stackexchange.com/questions/363048/unable-to-locate-package-docker-ce-on-a-64bit-ubuntu)
echo -e 'Install docker'

sudo apt install apt-transport-https ca-certificates curl software-properties-common -y
curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"
sudo apt update -y
apt-get update -y && apt-get install docker-ce=18.06.2~ce~3-0~ubuntu -y

# Setup daemon.
cat > /etc/docker/daemon.json <<EOF
{
  "exec-opts": ["native.cgroupdriver=systemd"],
  "log-driver": "json-file",
  "log-opts": {
    "max-size": "100m"
  },
  "storage-driver": "overlay2"
}
EOF

mkdir -p /etc/systemd/system/docker.service.d

# Restart docker.
systemctl daemon-reload
systemctl restart docker

sudo systemctl enable docker

snap alias microk8s.kubectl kubectl

# Fix dns
sudo iptables -P FORWARD ACCEPT

# Install k8s addons
sudo microk8s.enable ingress dns dashboard

# Manual k8s ingress controller
kubectl create -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/static/mandatory.yaml

# Jaeger Operator k8s setup
kubectl create namespace observability
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/crds/jaegertracing.io_jaegers_crd.yaml
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/service_account.yaml
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/role.yaml
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/role_binding.yaml
kubectl create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/operator.yaml

# Update vm.max_map_count for sonarqube/postgres
#sudo sysctl -w vm.max_map_count=262144

# Clone project
echo "cd /vagrant" >> /home/vagrant/.bashrc
git clone https://github.com/giopeto/k8s-eShop.git
sudo chmod -R 777 k8s-eShop

# grant premission to /data/my-nexus-data/ for nexus server
# cd /
# sudo mkdir data 
# cd data
# sudo mkdir my-nexus-data
# sudo chmod 777 -R my-nexus-data/
# grant premission to /data/my-jenkins-data/ for jenkins server
#sudo cp -R /home/vagrant/k8s-eShop/resources/my-jenkins-data my-jenkins-data
#sudo chmod 777 -R my-jenkins-data/

# Remove 'cd /vagrant' from /home/vagrant/.bashrc
sed -i '$d' /home/vagrant/.bashrc



sudo apt autoremove -y
echo -e '\n\n\n\n END \n\n\n\n'