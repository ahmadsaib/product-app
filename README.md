Build project with Maven
Step 1 : mvn clean install

Move application to data folder
Step 2 : move .\target\product*.jar .\data

Build Docker image
Step 3 : docker build -t="product-app" .

Run Docker image
Step 4 : docker run -p 8080:8080 -it --rm product-app

Check status
docker ps
