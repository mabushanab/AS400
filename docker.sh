echo "Packaging application"
mvn package

echo "Removing old docker container if exists"
sudo docker rm -f app 

echo "Build new docker image"
sudo docker build -t app:v1 .

echo "Run docker image"
#the -v /dev/urandom:/dev/urandom is using ubunto entropy to start tomcat faster
sudo docker run --name=app -d --restart always -v /dev/urandom:/dev/urandom -p 80:8080 app:v1

echo "App started on port 80"
 
