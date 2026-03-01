# 🛒 PROYECTO TIENDA - CLIENTE SERVIDOR

> Sistema de gestión de tienda con arquitectura cliente-servidor. Servidor REST desarrollado en Spring Boot + Cliente Java de consola.

**Versión:** 1.0.0  
**Autor:** Luis  
**Tecnologías:** Java 17, Spring Boot 3.3.2, MySQL 8.0, Maven

---

## 📋 Índice

- [Descripción](#-descripción)
- [Tecnologías](#-tecnologías)
- [Estructura del Proyecto](#-estructura-del-proyecto)
- [Requisitos Previos](#-requisitos-previos)
- [Instalación y Configuración](#-instalación-y-configuración)
- [Uso](#-uso)
- [API REST](#-api-rest)
- [Tests](#-tests)
- [Documentación](#-documentación)
- [Contribución](#-contribución)

---

## 📖 Descripción

Sistema completo de gestión de tienda que implementa una arquitectura cliente-servidor:

- **Servidor**: API REST que gestiona categorías y productos, con persistencia en MySQL
- **Cliente**: Aplicación de consola con menús interactivos para realizar operaciones CRUD
- **Base de Datos**: MySQL con dos tablas relacionadas (categorías y productos)

### Características principales

✅ CRUD completo de categorías y productos  
✅ Documentación interactiva con Swagger/OpenAPI  
✅ Tests unitarios e integración con JUnit 5  
✅ Persistencia con JPA/Hibernate  
✅ Pool de conexiones con HikariCP  
✅ Documentación Javadoc generada  
✅ Cliente HTTP nativo de Java (sin librerías externas)

---

## 🛠 Tecnologías

### Servidor (Spring Boot)
- **Spring Boot** 3.3.2 - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **MySQL Connector** 8.0.33 - Driver JDBC
- **Hibernate** 6.5.2 - ORM
- **Swagger/OpenAPI** 2.6.0 - Documentación API
- **JUnit 5** - Testing
- **Maven** - Gestión de dependencias

### Cliente (Java Console)
- **Java SE** 17 - Plataforma
- **Gson** 2.10.1 - Serialización JSON
- **HttpURLConnection** - Cliente HTTP (nativo)
- **Maven** - Gestión de dependencias

### Base de Datos
- **MySQL** 8.0 - Sistema de gestión de base de datos

---

## 📁 Estructura del Proyecto

```
Projecto_ClienteServidor/
│
├── servidor/                          # API REST Spring Boot
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/luistienda/
│   │   │   │   ├── ServerApplication.java       # Clase principal
│   │   │   │   ├── controller/                  # Controladores REST
│   │   │   │   │   ├── CategoriaController.java
│   │   │   │   │   └── ProductoController.java
│   │   │   │   ├── service/                     # Lógica de negocio
│   │   │   │   │   ├── CategoriaService.java
│   │   │   │   │   └── ProductoService.java
│   │   │   │   ├── repository/                  # Acceso a datos (JPA)
│   │   │   │   │   ├── CategoriaRepository.java
│   │   │   │   │   └── ProductoRepository.java
│   │   │   │   └── model/                       # Entidades JPA
│   │   │   │       ├── Categoria.java
│   │   │   │       └── Producto.java
│   │   │   └── resources/
│   │   │       └── application.properties       # Configuración
│   │   └── test/                                # Tests JUnit
│   ├── pom.xml                                  # Dependencias Maven
│   └── DESPLIEGUE.txt
│
├── cliente/                           # Aplicación de consola
│   ├── src/
│   │   ├── main/
│   │   │   └── java/com/luistienda/
│   │   │       ├── ClienteApp.java              # Clase principal
│   │   │       ├── api/
│   │   │       │   └── ApiClient.java           # Cliente HTTP
│   │   │       └── model/                       # DTOs
│   │   │           ├── Categoria.java
│   │   │           └── Producto.java
│   │   └── test/                                # Tests JUnit
│   ├── pom.xml
│   └── DESPLIEGUE.txt
│
├── MEMORIA_PROYECTO.html              # Documentación completa del proyecto
├── README.md                          # Este archivo
└── .gitignore                         # Archivos ignorados por Git
```

---

## ⚙️ Requisitos Previos

Antes de comenzar, asegúrate de tener instalado:

- **JDK 17** o superior ([descargar](https://adoptium.net/))
- **MySQL 8.0** o superior ([descargar](https://dev.mysql.com/downloads/))
- **Maven 3.6** o superior ([descargar](https://maven.apache.org/download.cgi))
- **Git** para clonar el repositorio

Verificar instalación:
```bash
java -version    # Debe mostrar Java 17+
mvn -version     # Debe mostrar Maven 3.6+
mysql --version  # Debe mostrar MySQL 8.0+
```

---

## 🚀 Instalación y Configuración

### 1. Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/Projecto_ClienteServidor.git
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
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

### 4. Compilar el Proyecto

**Servidor:**
```bash
cd servidor
mvn clean package
```

**Cliente:**
```bash
cd ../cliente
mvn clean package
```

---

## ▶️ Uso

### Iniciar el Servidor

```bash
cd servidor
java -jar target/servidor-tienda-1.0.0.jar
```

El servidor iniciará en el puerto **12345**:
- API REST: http://localhost:12345/api
- Swagger UI: http://localhost:12345/swagger-ui.html

### Iniciar el Cliente

En otra terminal:

```bash
cd cliente
java -jar target/cliente-tienda-1.0.0.jar
```

El cliente mostrará un menú interactivo:

```
MENÚ PRINCIPAL - TIENDA LUIS
1. Gestionar Categorías
2. Gestionar Productos
0. Salir

Elige opción:
```

### Datos de Ejemplo

Para probar la aplicación, puedes insertar datos de ejemplo:

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

## 🌐 API REST

### Endpoints Disponibles

#### Categorías

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/categorias` | Obtener todas las categorías |
| GET | `/api/categorias/{id}` | Obtener categoría por ID |
| POST | `/api/categorias` | Crear nueva categoría |
| PUT | `/api/categorias/{id}` | Actualizar categoría |
| DELETE | `/api/categorias/{id}` | Eliminar categoría |

#### Productos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Obtener todos los productos |
| GET | `/api/productos/{id}` | Obtener producto por ID |
| GET | `/api/productos/categoria/{id}` | Obtener productos de una categoría |
| POST | `/api/productos` | Crear nuevo producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |

### Ejemplo de Petición (POST)

```bash
curl -X POST http://localhost:12345/api/categorias \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Deportes","descripcion":"Artículos deportivos"}'
```

### Documentación Interactiva

Accede a Swagger UI para probar los endpoints interactivamente:
👉 http://localhost:12345/swagger-ui.html

---

## 🧪 Tests

### Ejecutar Tests del Servidor

```bash
cd servidor
mvn test
```

Tests incluidos:
- ✅ CRUD de categorías (crear, leer, actualizar, eliminar)
- ✅ CRUD de productos
- ✅ Filtrado de productos por categoría
- ✅ Validaciones de datos
- ✅ Manejo de errores

### Ejecutar Tests del Cliente

```bash
cd cliente
mvn test
```

### Ver Resultados

Los tests generan informes en:
- `servidor/target/surefire-reports/`
- `cliente/target/surefire-reports/`

---

## 📚 Documentación

### Javadoc

**Generar Javadoc del Servidor:**
```bash
cd servidor
mvn javadoc:javadoc
```
Ver en: `servidor/target/site/apidocs/index.html`

**Generar Javadoc del Cliente:**
```bash
cd cliente
mvn javadoc:javadoc
```
Ver en: `cliente/target/javadoc/index.html`

### Documentación Completa

Consulta `MEMORIA_PROYECTO.html` para documentación técnica detallada incluyendo:
- Arquitectura del sistema
- Diagramas de estructura
- Explicación de cada componente
- Guía de despliegue completa

---

## 👤 Contribución

Este es un proyecto académico de 2º DAM (Desarrollo de Aplicaciones Multiplataforma).

Si deseas contribuir:
1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/nueva-funcionalidad`)
3. Commit tus cambios (`git commit -m 'Añade nueva funcionalidad'`)
4. Push a la rama (`git push origin feature/nueva-funcionalidad`)
5. Abre un Pull Request

---

## 📄 Licencia

Este proyecto es de uso educativo.

---

## 📞 Contacto

**Autor:** Luis  
**Proyecto:** Sistema Cliente-Servidor de Gestión de Tienda  
**Año:** 2026
