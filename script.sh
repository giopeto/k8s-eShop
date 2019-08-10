#!binbash

# set the APP_VERSION variable
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

echo "Build docker image: $APP_NAME:$APP_VERSION ..."

# build, tag and push
cd $APP_PATH

docker build -t $APP_NAME:$APP_VERSION -t $APP_NAME --pull --no-cache .

docker tag $APP_NAME:$APP_VERSION giopeto/$APP_NAME:$APP_VERSION

docker push giopeto/$APP_NAME:$APP_VERSION

echo "Build docker image: $APP_NAME:$APP_VERSION completed"