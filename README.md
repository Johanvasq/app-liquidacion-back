# Liquidation API
API project for liquidations of the company IAS software, developed by Johan Vasquez.

## Deployment
  1. Download the project from github using the following command in your terminal 
 ```bash
  git clone https://github.com/Johanvasq/app-liquidacion-back.git
 ```
  2. Open the console in the folder where you downloaded the project.
  3. Navigate to the following path: 
  ```bash
  cd app-back/src/main/java/co/com/ias/appback/deployment
  ```
  4. Deploy the application with Docker Compose using the following command:
```bash
docker-compose up
```
  5. This instruction will create 3 images and deploy the containers to the following ports:
  - **Postgres:** port: 5432
-    **Adminer:** port: 8090
-    **App-back:** port: 8087
  6. Once the application is deployed, the API will listen for requests through the following URL: http://localhost:8087/

For more information on how to deploy a Docker Compose, download Docker Desktop and visit its [Documentation](https://docs.docker.com/compose/)

**Note:** it will be deployed on a Docker network called "app-liquidation" using the "bridge" network driver.
