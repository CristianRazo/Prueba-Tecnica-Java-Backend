# Prueba Técnica - Microservicios Java Backend

## 📋 Descripción

La solución está implementada con una **arquitectura de microservicios**, compuesta por dos servicios independientes que se comunican entre sí a través de APIs REST:

- **Product Service** (Puerto 8081)
- **Order Service** (Puerto 8082)

## 🛠️ Stack Tecnológico

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database**
- **Maven Wrapper**
- **JUnit 5 y Mockito**
- **Lombok**
- **Swagger (OpenAPI)**

## 🚀 Instrucciones(Instalacion Manual)

### Requisitos Previos
Tener instalado un JDK 17 o superior.

### Windows
#### Paso 1: Construir los Microservicios
Abre tu terminal, navega a la raíz del proyecto y ejecuta los siguientes comandos para construir cada servicio.

```
# 1. Construir el Product Service
cd product-service
.\mvnw.cmd clean package
cd ..

# 2. Construir el Order Service
cd order-service
.\mvnw.cmd clean package
cd ..
```

### Linux

```
# 1. Construir el Product Service
(cd product-service && ./mvnw clean package)

# 2. Construir el Order Service
(cd order-service && ./mvnw clean package)

# 3. Ejecutar java jar 

java -jar product-service/target/product-service-0.0.1-SNAPSHOT.jar

java -jar order-service/target/order-service-0.0.1-SNAPSHOT.jar
```

## 🚀 Instrucciones(Instalacion con Script)

En la raíz del proyecto se incluyen archivos **start-services** para levantar los servicios automáticamente.

### 🪟 Windows
1. Abrir terminal en la raíz del proyecto
2. Ejecutar el script:
```batch
start-services.bat
```

### 🐧 Linux/Mac
1. Abrir terminal en la raíz del proyecto
2. Dar permisos de ejecución:
```bash
chmod +x start-services.sh && chmod +x product-service/mvnw && chmod +x order-service/mvnw
```
3. Ejecutar el script:
```bash
./start-services.sh
```

## 🌐 URLs de Acceso

### Servicios
- **Product Service**: http://localhost:8081
- **Order Service**: http://localhost:8082

### 📖 Documentación Swagger
- **Product Service**: http://localhost:8081/swagger-ui/index.html
- **Order Service**: http://localhost:8082/swagger-ui/index.html

### 🗄️ Consolas H2 Database
- **Product Service**: http://localhost:8081/h2-console
  - JDBC URL: `jdbc:h2:mem:productdb`
- **Order Service**: http://localhost:8082/h2-console
  - JDBC URL: `jdbc:h2:mem:orderdb`

## 💡 Ejemplos de Uso (cURL)(Se puede usar Swagger para reralizar las consultas) 

### Product Service

#### Crear un nuevo producto
```bash
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Monitor Curvo 27 pulgadas",
    "price": 250.00,
    "stock": 20,
    "active": true
  }'
```

#### Obtener todos los productos
```bash
curl -X GET http://localhost:8081/api/products
```

### Order Service

#### Crear un pedido exitoso
```bash
curl -X POST http://localhost:8082/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "customerName": "Carlos Santana",
    "customerEmail": "carlos.santana@example.com",
    "items": [{
      "productId": 1,
      "quantity": 2,
      "unitPrice": 250.00
    }]
  }'
```

#### Obtener un pedido por su ID
```bash
curl -X GET http://localhost:8082/api/orders/1
```

