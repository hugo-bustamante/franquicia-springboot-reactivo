=======
# 🏪 Franquicia API Reactiva

Esta es una API desarrollada en **Spring Boot con WebFlux** para manejar franquicias, sucursales y productos, utilizando una arquitectura basada en **programación reactiva** y persistencia en **MongoDB Atlas**.

---

## ✨ Características Principales

- Agregar, actualizar y eliminar franquicias, sucursales y productos.
- Consultar el producto con mayor stock por sucursal de una franquicia.
- Desarrollado usando **Spring Boot WebFlux (reactivo)**, **Java 17**, y **MongoDB Atlas**.
- Contenerizado con **Docker** y variables de entorno gestionadas con archivo `.env`.

---

## 🚀 Tecnologías Usadas

- Java 17
- Spring Boot WebFlux
- MongoDB Atlas
- Docker & Docker Compose
- Lombok
- Reactor Core (Mono, Flux)

---

## ⚙️ Requisitos Previos

- Tener Docker y Docker Compose instalados
- Tener una cuenta en MongoDB Atlas y la URI de conexión

---

## 🔧 Configuración Inicial

1. **Clonar el repositorio:**
git clone https://github.com/hugo-bustamante/franquicia-springboot-reactivo.git
cd franquicia-springboot-reactivo

---

2. **Crear archivo .env**
- Crea un archivo llamado .env en la raíz del proyecto con el siguiente contenido:
- MONGODB_URI=mongodb+srv://<usuario>:<password>@<cluster-url>/franquiciasdb?retryWrites=true&w=majority&appName=cluster-franquicia
- Reemplaza <usuario>, <password> y <cluster-url> por los datos de tu cluster de MongoDB Atlas.

---

## 🚧 Levantar el Proyecto con Docker Compose
Desde la raíz del proyecto, ejecuta:

- docker-compose up --build

Esto construirá la imagen de la API y la levantará en el puerto 8080.

---

## 🌐 Desplegar la Infraestructura con Terraform

Para desplegar la infraestructura (ECR, Lambda, API Gateway), sigue estos pasos:

1. **Configura las variables de entorno en `terraform.tfvars`**:  
Asegúrate de tener las credenciales de AWS y la URI de MongoDB Atlas configuradas correctamente en el archivo `terraform.tfvars` (puedes seguir el ejemplo ya configurado en terraform.tfvars.example).

2. **Ejecuta Terraform**:

Desde la carpeta raíz del proyecto, navega a `infra/terraform` y corre los siguientes comandos:

- cd infra/terraform
- terraform init
- terraform apply

3. **Automatiza con el Script deploy.sh**
Puedes usar el script deploy.sh para automatizar el despliegue completo. Este script se encargará de:

- Inicializar Terraform
- Crear el repositorio en ECR
- Construir y subir la imagen Docker a ECR
- Desplegar la infraestructura completa en AWS


*Simplemente corre el siguiente comando:*
- ./deploy.sh

## 🧪 Correr Pruebas Locales

Si deseas ejecutar las pruebas unitarias y de integración de la aplicación localmente, usa Maven con el siguiente comando:

- mvn clean test

Nota: Flapdoodle debe levantar automáticamente una instancia embebida de MongoDB cuando se ejecuten las pruebas integrales, los test realizados en las pruebas están bien a nivel de logica.
---

## 🚜 Endpoints Disponibles

Base URL: http://localhost:8080/api/franquicias

- `POST /api/franquicias`: Crear una nueva franquicia.
- `POST /api/franquicias/{id}/sucursales`: Agregar una nueva sucursal a una franquicia.
- `POST /api/franquicias/{id}/sucursales/{sucursalId}/productos`: Agregar un nuevo producto a una sucursal.
- `DELETE /api/franquicias/{id}/sucursales/{sucursalId}/productos/{productId}`: Eliminar un producto de una sucursal.
- `PUT /api/franquicias/{id}/sucursales/{sucursalId}/productos/{productId}/stock`: Modificar el stock de un producto.
- `GET /api/franquicias/{id}/productos-mayor-stock`: Consultar el producto con mayor stock por sucursal de una franquicia.
- `PUT /api/franquicias/{id}/nombre`: Actualizar el nombre de una franquicia.
- `PUT /api/franquicias/{id}/sucursales/{sucursalId}/nombre`: Actualizar el nombre de una sucursal.
- `PUT /api/franquicias/{id}/sucursales/{sucursalId}/productos/{productId}/nombre`: Actualizar el nombre de un producto.

---

## 📚 Estructura del Proyecto
- domain: Modelos y repositorios
- application: Casos de uso
- infrastructure.repository.mongo: Adaptadores de persistencia
- infrastructure.rest.controller: Controladores REST
- infrastructure.rest.dto: DTOs
- infrastructure.rest.mapper: Mappers entre dominio y DTOs

---

## 🚨 Notas Finales

- El proyecto se conecta a MongoDB Atlas usando la URI definida en .env
- La aplicación se empaqueta y levanta con Docker
- Se utilizaron buenas prácticas como programación reactiva, Mono y Flux
- Requiere Java 17 para su construcción
>>>>>>> develop
