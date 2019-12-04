echo -e '\n microk8s-ubuntu \n'

apt-get update

# Install snapd and kubernetes (https://microk8s.io/docs/)
echo -e '\n Install snapd and kubernetes (https://microk8s.io/docs/) \n'
sudo dpkg --configure -a
sudo apt update -y
sudo apt upgrade -y
sudo apt dist-upgrade -y
sudo apt install snapd

sudo snap install microk8s --classic
sudo usermod -a -G microk8s vagrant

echo -e 'Install curl, nodejs 12.x, n and npm'
# Install curl, nodejs 12.x, n and npm
sudo apt-get install -y curl
curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash -
sudo apt-get install -y nodejs
sudo apt-get install -y build-essential
sudo npm install -g npm
sudo npm install -g n
sudo npm install -g @angular/cli

echo -e 'Install java 11, maven, intellij-idea-community, chromium, visual studio code and sublime'
# Install java 11, maven, intellij-idea-community, chromium, visual studio code and sublime
sudo apt install openjdk-11-jdk -y
sudo apt install maven -y
sudo snap install intellij-idea-community --classic
sudo snap install chromium
sudo snap install code --classic
sudo snap install sublime-text --classic

# Install docker (https://unix.stackexchange.com/questions/363048/unable-to-locate-package-docker-ce-on-a-64bit-ubuntu)
echo -e '\n Install docker \n'

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

snap alias microk8s.kubectl k

# Fix dns
sudo iptables -P FORWARD ACCEPT

echo -e '\n K8s ingress, dns, dashboard, jaeger-operator \n'

# Install k8s addons
sudo microk8s.enable ingress dns dashboard

# Manual k8s ingress controller
k create -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/static/mandatory.yaml
k create -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/static/provider/baremetal/service-nodeport.yaml


# Jaeger Operator k8s setup
k create namespace observability
k create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/crds/jaegertracing.io_jaegers_crd.yaml
k create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/service_account.yaml
k create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/role.yaml
k create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/role_binding.yaml
k create -f https://raw.githubusercontent.com/jaegertracing/jaeger-operator/master/deploy/operator.yaml

# Clone project
echo "cd /vagrant" >> /home/vagrant/.bashrc
git clone https://github.com/giopeto/k8s-eshop.git
sudo chmod -R 777 k8s-eshop

# Remove 'cd /vagrant' from /home/vagrant/.bashrc
sed -i '$d' /home/vagrant/.bashrc

sudo apt autoremove -y
echo -e '\n END \n'