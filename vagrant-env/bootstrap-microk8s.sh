# Install kubernetes (https://microk8s.io/docs/)

sudo apt install snapd
sudo snap install microk8s --classic
snap alias microk8s.kubectl kubectl

# Manual k8s ingress controller
microk8s.kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/master/deploy/static/mandatory.yaml

# Fix dns
sudo ufw allow in on cbr0 && sudo ufw allow out on cbr0
sudo ufw default allow routed

# Fix dns (maybe ufw fix issue) <<<<<<<====== TODO: Check if ufw is enough
sudo iptables -P FORWARD ACCEPT
sudo apt-get install iptables-persistent --yes

sudo microk8s.enable ingress dns dashboard registry