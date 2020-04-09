echo "Packaging application"
mvn package

echo "Running application..."
sudo java -jar -Djava.security.egd=file:/dev/urandom  target/dependency/webapp-runner.jar target/app.war --port 9876



