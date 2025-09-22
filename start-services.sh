#!/bin/bash

echo "--- Construyendo ambos microservicios..."
(cd product-service && ./mvnw clean package)
(cd order-service && ./mvnw clean package)

echo ""
echo "--- Iniciando Product Service en segundo plano..."
(cd product-service && java -jar target/product-service-0.0.1-SNAPSHOT.jar) &

echo "--- Esperando 60 segundos para que Product Service se inicie correctamente..."
sleep 60

echo ""
echo "--- Iniciando Order Service en primer plano..."
(cd order-service && java -jar target/order-service-0.0.1-SNAPSHOT.jar)