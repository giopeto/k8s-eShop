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
# Install nodejs 12.x, n and npm
curl -sL https://deb.nodesource.com/setup_12.x | sudo -E bash -
apt-get install -y nodejs
apt-get install -y build-essential
npm install -g npm
npm install -g n

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

# Clone project
echo "cd /vagrant" >> /home/vagrant/.bashrc
git clone https://github.com/giopeto/k8s-eShop.git
sudo chmod -R 777 k8s-eShop

# Add maven settings.xml file
mkdir .m2
sudo chmod -R 777 .m2
cp k8s-eShop/resources/settings.xml .m2/settings.xml

# grant premission to /data/my-nexus-data/ for nexus server
cd /
sudo mkdir data 
cd data
sudo mkdir my-nexus-data
sudo chmod 777 -R my-nexus-data/
# grant premission to /data/my-jenkins-data/ for jenkins server
sudo mkdir my-jenkins-data
sudo chmod 777 -R my-jenkins-data/

sudo sed -i "$ a 127.0.0.1	k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	nexus.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	jaeger.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	jenkins.k8s-eshop.io" /etc/hosts
sudo sed -i "$ a 127.0.0.1	sonar.k8s-eshop.io" /etc/hosts


sudo apt autoremove -y
echo -e '\n\n\n\n END \n\n\n\n'