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
git clone https://github.com/usuario/mi-api-franquicia.git
cd mi-api-franquicia

---

2. **Crear archivo .env**
-Crea un archivo llamado .env en la raíz del proyecto con el siguiente contenido:
-MONGODB_URI=mongodb+srv://<usuario>:<password>@<cluster-url>/franquiciasdb?retryWrites=true&w=majority&appName=cluster-franquicia
-Reemplaza <usuario>, <password> y <cluster-url> por los datos de tu cluster de MongoDB Atlas.

---

## 🚧 Levantar el Proyecto con Docker Compose
Desde la raíz del proyecto, ejecuta:

docker-compose up --build

Esto construirá la imagen de la API y la levantará en el puerto 8080.

---

## 🚜 Endpoints Disponibles

Base URL: http://localhost:8080/api/franquicias

---

## Franquicias 


---

## 📚 Estructura del Proyecto
-domain: Modelos y repositorios
-application: Casos de uso
-infrastructure.repository.mongo: Adaptadores de persistencia
-infrastructure.rest.controller: Controladores REST
-infrastructure.rest.dto: DTOs
-infrastructure.rest.mapper: Mappers entre dominio y DTOs

---

## 🚨 Notas Finales

-El proyecto se conecta a MongoDB Atlas usando la URI definida en .env
-La aplicación se empaqueta y levanta con Docker
-Se utilizaron buenas prácticas como programación reactiva, Mono y Flux
-Requiere Java 17 para su construcción