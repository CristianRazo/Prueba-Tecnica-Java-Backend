@echo off
REM Este script automatiza el inicio de ambos microservicios en Windows.

echo --- Construyendo ambos microservicios (esto puede tardar un momento)...
call product-service\mvnw.cmd clean package
call order-service\mvnw.cmd clean package

echo.
echo --- Iniciando Product Service en una nueva ventana...
start "Product Service" cmd /c "cd product-service && java -jar target\product-service-0.0.1-SNAPSHOT.jar"

echo --- Esperando 60 segundos para que Product Service se inicie correctamente...
timeout /t 60 /nobreak > NUL

echo.
echo --- Iniciando Order Service en esta ventana...
cd order-service
java -jar target\order-service-0.0.1-SNAPSHOT.jar