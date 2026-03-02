# PROYECTO TIENDA - CLIENTE SERVIDOR

Sistema de gestión de tienda con arquitectura cliente-servidor. Servidor REST desarrollado en Spring Boot y cliente Java de consola.

**Versión:** 1.0.0  
**Autor:** Luis  
**Tecnologías:** Java 17, Spring Boot 3.3.2, MySQL 8.0, Maven

---

## Índice

- [Descripción](#descripción)
- [Tecnologías](#tecnologías)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Requisitos Previos](#requisitos-previos)
- [Instalación y Configuración](#instalación-y-configuración)
- [Uso](#uso)
- [API REST](#api-rest)
- [Tests](#tests)
- [Documentación](#documentación)

---

## Descripción

Sistema completo de gestión de tienda que implementa una arquitectura cliente-servidor:

- **Servidor**: API REST que gestiona categorías y productos, con persistencia en MySQL
- **Cliente**: Aplicación de consola con menús interactivos para realizar operaciones CRUD
- **Base de Datos**: MySQL con dos tablas relacionadas (categorías y productos)

### Características principales

- CRUD completo de categorías y productos
- Documentación interactiva con Swagger/OpenAPI
- Tests unitarios e integración con JUnit 5
- Persistencia con JPA/Hibernate
- Pool de conexiones con HikariCP
- Documentación Javadoc generada
- Cliente HTTP nativo de Java

---

## Tecnologías

### Servidor (Spring Boot)
- Spring Boot 3.3.2
- Spring Data JPA
- MySQL Connector 8.0.33
- Hibernate 6.5.2
- Swagger/OpenAPI 2.6.0
- JUnit 5
- Maven

### Cliente (Java Console)
- Java SE 17
- Gson 2.10.1
- HttpURLConnection (cliente HTTP nativo)
- Maven

### Base de Datos
- MySQL 8.0

---

## Estructura del Proyecto

```
Projecto_ClienteServidor/
│
├── servidor/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/luistienda/
│   │   │   │   ├── ServerApplication.java
│   │   │   │   ├── controller/
│   │   │   │   │   ├── CategoriaController.java
│   │   │   │   │   └── ProductoController.java
│   │   │   │   ├── service/
│   │   │   │   │   ├── CategoriaService.java
│   │   │   │   │   └── ProductoService.java
│   │   │   │   ├── repository/
│   │   │   │   │   ├── CategoriaRepository.java
│   │   │   │   │   └── ProductoRepository.java
│   │   │   │   └── model/
│   │   │   │       ├── Categoria.java
│   │   │   │       └── Producto.java
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
│
├── cliente/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/com/luistienda/
│   │   │       ├── ClienteApp.java
│   │   │       ├── api/
│   │   │       │   └── ApiClient.java
│   │   │       └── model/
│   │   │           ├── Categoria.java
│   │   │           └── Producto.java
│   │   └── test/
│   └── pom.xml
│
├── README.md
└── .gitignore
```

---

## Requisitos Previos

- JDK 17 o superior
- MySQL 8.0 o superior
- Maven 3.6 o superior
- Git

Verificar instalación:
```bash
java -version
mvn -version
mysql --version
```

---

## Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/Luiscerve/Projecto_ClienteServidor.git
cd Projecto_ClienteServidor
```

### 2. Configurar Base de Datos

Crear la base de datos en MySQL:

```bash
mysql -u root -p
```

```sql
CREATE DATABASE tienda_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
EXIT;
```

### 3. Configurar Servidor

Editar `servidor/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tienda_db
spring.datasource.username=root
spring.datasource.password=tu_contraseña
```

### 4. Compilar el Proyecto

Servidor:
```bash
cd servidor
mvn clean package
```

Cliente:
```bash
cd ../cliente
mvn clean package
```

---

## Uso

### Iniciar el Servidor

```bash
cd servidor
java -jar target/servidor-tienda-1.0.0.jar
```

El servidor iniciará en el puerto 12345:
- API REST: http://localhost:12345/api
- Swagger UI: http://localhost:12345/swagger-ui.html

### Iniciar el Cliente

En otra terminal:

```bash
cd cliente
java -jar target/cliente-tienda-1.0.0.jar
```

El cliente mostrará un menú interactivo para gestionar categorías y productos.

### Datos de Ejemplo

Para probar la aplicación:

```sql
USE tienda_db;

INSERT INTO categorias (nombre, descripcion) VALUES
('Electrónica', 'Dispositivos electrónicos y accesorios'),
('Ropa', 'Prendas de vestir'),
('Alimentación', 'Productos alimenticios');

INSERT INTO productos (nombre, precio, stock, categoria_id) VALUES
('Ordenador Portátil', 899.99, 15, 1),
('Smartphone Samsung', 549.00, 30, 1),
('Camiseta Básica', 19.99, 100, 2),
('Arroz Integral', 2.50, 200, 3);
```

---

## API REST

### Endpoints Disponibles

**Categorías:**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/categorias` | Obtener todas las categorías |
| GET | `/api/categorias/{id}` | Obtener categoría por ID |
| POST | `/api/categorias` | Crear nueva categoría |
| PUT | `/api/categorias/{id}` | Actualizar categoría |
| DELETE | `/api/categorias/{id}` | Eliminar categoría |

**Productos:**

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Obtener todos los productos |
| GET | `/api/productos/{id}` | Obtener producto por ID |
| GET | `/api/productos/categoria/{id}` | Obtener productos de una categoría |
| POST | `/api/productos` | Crear nuevo producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |

### Ejemplo de Petición

```bash
curl -X POST http://localhost:12345/api/categorias \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Deportes","descripcion":"Artículos deportivos"}'
```

### Documentación Interactiva

Swagger UI: http://localhost:12345/swagger-ui.html

---

## Tests

### Ejecutar Tests del Servidor

```bash
cd servidor
mvn test
```

Tests incluidos:
- CRUD de categorías
- CRUD de productos
- Filtrado de productos por categoría
- Validaciones de datos
- Manejo de errores

### Ejecutar Tests del Cliente

```bash
cd cliente
mvn test
```

Los tests generan informes en los directorios `target/surefire-reports/`.

---

## Documentación

### Javadoc

Generar Javadoc del Servidor:
```bash
cd servidor
mvn javadoc:javadoc
```
Ver en: `servidor/target/site/apidocs/index.html`

Generar Javadoc del Cliente:
```bash
cd cliente
mvn javadoc:javadoc
```
Ver en: `cliente/target/javadoc/index.html`

### Memoria del Proyecto

Consulta el archivo **MEMORIA_PROYECTO.pdf** para documentación técnica completa incluyendo:
- Arquitectura del sistema
- Diagramas de estructura
- Explicación detallada de componentes
- Guía de despliegue
- Capturas de pantalla del funcionamiento

---

**Autor:** Luis  
**Proyecto:** Sistema Cliente-Servidor de Gestión de Tienda  
**Año:** 2026
