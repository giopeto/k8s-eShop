#!binbash

get_nexus_ip_address () {
	kubectl get svc nexus-svc -o jsonpath='{.spec.clusterIP}'
}

# set the APP_VERSION and APP_PATH variables
APP_VERSION=$1
APP_PATH=$2

echo "USAGE: script.sh [APP_VERSION] [APP_PATH]"

# check input parameters
if [ -z "$APP_VERSION" ]; then
    echo "Error: APP_VERSION is not set"
    exit
fi

if [ -z "$APP_PATH" ]; then
    echo "Error: APP_PATH is not set"
    exit
fi

APP_VERSION="V$APP_VERSION"
APP_NAME="e-shop-$APP_PATH"
NEXUS_IP_ADDRESS=$(get_nexus_ip_address)

echo "Build docker image: $APP_NAME:$APP_VERSION ..."

# build, tag and push
cd ../$APP_PATH

docker build --add-host=nexus.k8s-eshop.io:$NEXUS_IP_ADDRESS -t $APP_NAME:$APP_VERSION -t $APP_NAME --pull --no-cache .

docker tag $APP_NAME:$APP_VERSION giopeto/$APP_NAME:$APP_VERSION

docker push giopeto/$APP_NAME:$APP_VERSION

