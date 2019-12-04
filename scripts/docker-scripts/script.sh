#!binbash

get_nexus_ip_address () {
	k get svc nexus-svc -o jsonpath='{.spec.clusterIP}'
}

# set the APP_VERSION, APP_PATH and SKIP_NEXUS_HOST(optional) variables
APP_VERSION=$1
APP_PATH=$2
SKIP_NEXUS_HOST=$3

echo "USAGE: script.sh [APP_VERSION] [APP_PATH] [SKIP_NEXUS_HOST](optional default to no)"
echo "Exmple: script.sh 1 store-service"
echo "Exmple(skip nexus): script.sh 2 ui-client y"

# check input parameters
if [ -z "$APP_VERSION" ]; then
    echo "Error: APP_VERSION is not set"
    exit
fi

if [ -z "$APP_PATH" ]; then
    echo "Error: APP_PATH is not set"
    exit
fi

if [ -z "$SKIP_NEXUS_HOST" ]; then
    ADD_HOST=--add-host=nexus.k8s-eshop.io:$(get_nexus_ip_address)
fi

APP_VERSION="V$APP_VERSION"
APP_NAME="e-shop-$APP_PATH"

echo "Build docker image: $APP_NAME:$APP_VERSION ..."

# build, tag and push
cd ../../$APP_PATH

docker build $ADD_HOST -t $APP_NAME:$APP_VERSION -t $APP_NAME --pull --no-cache .
docker tag $APP_NAME:$APP_VERSION giopeto/$APP_NAME:$APP_VERSION
echo "Push docker image: $APP_NAME:$APP_VERSION to registry ..."
docker push giopeto/$APP_NAME:$APP_VERSION