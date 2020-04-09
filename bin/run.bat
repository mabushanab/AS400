cls
call mvn clean package -U
start java -jar target/dependency/webapp-runner.jar target/app.war --port 9876
start http://localhost:9876


