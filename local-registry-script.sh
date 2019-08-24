#!binbash

APP_VERSION="local"
APP_PATH=$1

echo "USAGE: script.sh [APP_PATH]"


if [ -z "$APP_PATH" ]; then
    echo "Error: APP_PATH is not set"
    exit
fi

APP_NAME="e-shop-$APP_PATH"

echo "Build docker image: $APP_NAME:$APP_VERSION ..."

# build, tag and push
cd $APP_PATH

docker build -t $APP_NAME:$APP_VERSION -t $APP_NAME --pull --no-cache .

docker tag $APP_NAME:$APP_VERSION localhost:32000/$APP_NAME:$APP_VERSION

docker push localhost:32000/$APP_NAME:$APP_VERSION

echo "Build docker image: $APP_NAME:$APP_VERSION completed"
