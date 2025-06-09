
# Sistema de Gestión de Préstamos (SiGeP)
**Universidad Siglo 21** - Licenciatura en Informática  
**Materia**: Seminario de Práctica de Informática

---
## 📂 Estructura del Proyecto

| Carpeta                  | Rol Técnico               | Descripción Técnica                                                                 |
|--------------------------|---------------------------|-------------------------------------------------------------------------------------|
| `auth/`                  | Servicio de Autenticación | Implementa patrón *Factory* para múltiples proveedores de autenticación.            |
| `model/`                 | Entidades del Dominio     | Contiene las clases de negocio (DDD), desacopladas de vistas y controladores.       |
| `repository/`            | Capa de Persistencia      | Implementa DAO/Repository siguiendo el principio SRP (Single Responsibility).       |
| `service/`               | Lógica de Negocio         | Coordina operaciones entre repositorios y controladores (capa de aplicación).       |
| `controller/`            | Controladores (JavaFX)    | Media entre vistas y servicios, cumpliendo con el patrón MVC.                       |
| `view/`                  | Gestión de Vistas         | Define pantallas FXML con navegación desacoplada mediante *Router Pattern*.         |
| `util/`                  | Utilidades                | Funciones helper (validaciones, formateadores) reutilizables.                      |
| `config/`                | Configuración             | Centraliza propiedades (BD, endpoints) y constantes del sistema.                 |
| `resources/`             | Assets Estáticos          | Subcarpetas: `fxml/`, `css/`, `images/`, `sql_templates/` (separación clara).      |
| `exception/`             | Manejo de Errores         | Excepciones customizadas por capa (ej: `AuthException`, `RepositoryException`).     |
| `docs/`                  | Documentación             | Scripts SQL (`schema.sql`, `data.sql`) y manuales técnicos.                        |

---

## 📦 Dependencias Técnicas

| Tecnología           | Versión | Uso Principal                          | Enlace de Descarga                                                                 |
|----------------------|---------|----------------------------------------|-----------------------------------------------------------------------------------|
| **Java**            | 23      | Lenguaje base                          | Instalación via SDKMAN! (ver sección instalación)                                 |
| **JavaFX**          | 24      | Interfaz gráfica                       | [SDK Linux (x64)](https://download2.gluonhq.com/openjfx/24.0.1/openjfx-24.0.1_linux-x64_bin-sdk.zip) |
| **MySQL**           | 9.3     | Base de datos                          | Oficial (Docker)                                                                  |
| **MySQL Connector/J** | 9.3.0  | Conexión JDBC                          | [Maven Central](https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.3.0/mysql-connector-j-9.3.0.jar) |
| **FontAwesomeFX**   | 8.2     | Iconografía UI                         | [Maven Central](https://repo1.maven.org/maven2/de/jensd/fontawesomefx/8.2/fontawesomefx-8.2.jar) |

> 🔍 **Nota**: Las dependencias deben ubicarse en `~/Seminario/libs/` (ver configuración).

---

## 🛠️ Instalación (Linux)

### 1. Instalar Java 23 con SDKMAN!
```bash
# Instalar SDKMAN! (requiere curl y zip)
curl -s "https://get.sdkman.io" | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"

# Instalar JDK
sdk install java 23.0.2-tem
```

---

## 🐳 Configurar MySQL con Docker

```bash
docker run --name mysql_ues21 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest
```

- `--name mysql_ues21`: asigna el nombre al contenedor.
- `-e MYSQL_ROOT_PASSWORD=123456`: define la contraseña del usuario `root`.
- `-d mysql:latest`: usa la última imagen oficial de MySQL en segundo plano.

#### Obtener la IP del contenedor MySQL

Ejecuta el siguiente comando para obtener la IP asignada al contenedor llamado `mysql_ues21`:

```bash
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mysql_ues21
```

---

#### Ingresar al contenedor

Una vez creado el contenedor, ingresa con:

```bash
docker exec -it mysql_ues21 bash
```

Esto abrirá una terminal dentro del contenedor.

---

#### Ingresar a la consola de MySQL

Desde dentro del contenedor, ejecuta:

```bash
mysql -u root -p
```

Cuando se solicite, ingresa la contraseña: `123456`.

---

#### Ejecutar los comandos SQL

Una vez dentro de la consola de MySQL (`mysql>`), ejecuta los siguientes comandos en orden:

```sql
-- Crear la base de datos
CREATE DATABASE seminario_db;

-- Crear el usuario 'seminario' con contraseña 'password123'
CREATE USER 'seminario'@'%' IDENTIFIED BY 'password123';

-- Asignar permisos limitados solo sobre la base seminario_db
GRANT SELECT, INSERT, UPDATE, DELETE ON seminario_db.* TO 'seminario'@'%';

-- Aplicar los cambios de privilegios
FLUSH PRIVILEGES;
```

> ⚠️ **IMPORTANTE:**  
> En la carpeta `docs/sql` deben crear las tablas utilizando `seminario_schema.sql` y cargar la información de prueba con `seminario_data.sql`.

---

#### 🔒 Seguridad (opcional)

- Para un entorno de producción, se recomienda reemplazar `'%'` por `'localhost'` o una IP específica:

```sql
CREATE USER 'seminario'@'localhost' IDENTIFIED BY 'password123';
```

- Además, se recomienda utilizar contraseñas más seguras en producción.

---

---

### 📦 Organización de las bibliotecas (JavaFX, MySQL Connector/J, FontAwesomeFX)

Para facilitar la gestión, crea una carpeta común para todas las librerías `.jar` que se necesitan:

```bash
mkdir -p ~/Seminario/libs
```

#### Descargar y copiar los archivos `.jar` a la carpeta `libs`

#### JavaFX SDK

1. Descarga el SDK de JavaFX desde [aquí](https://download2.gluonhq.com/openjfx/24.0.1/openjfx-24.0.1_linux-x64_bin-sdk.zip).
2. Extrae el contenido y copia todos los `.jar` ubicados en la carpeta `lib/` al directorio `libs`:

```bash
unzip openjfx-24.0.1_linux-x64_bin-sdk.zip
cp openjfx-24.0.1/lib/*.jar ~/Seminario/libs/
```

#### MySQL Connector/J

```bash
wget https://repo1.maven.org/maven2/com/mysql/mysql-connector-j/9.3.0/mysql-connector-j-9.3.0.jar -P ~/Seminario/libs/
```

#### FontAwesomeFX

```bash
wget https://repo1.maven.org/maven2/de/jensd/fontawesomefx/8.2/fontawesomefx-8.2.jar -P ~/Seminario/libs/
```

---

#### 🛠️ Configuración del archivo `config.sh`

Luego de instalar las dependencias y descargar las librerías, configura el archivo `config.sh` con las siguientes variables:

```bash
# config.sh

# Ruta al JDK
JAVA_HOME="$HOME/.sdkman/candidates/java/23.0.2-tem"

# Ruta al directorio con todos los JAR necesarios
JAVAFX_SDK="$HOME/Seminario/libs"

# Directorio del código fuente
SRC_DIR="src"

# Directorio de salida
OUT_DIR="out/production/SeminarioApp"

# Clase principal con paquete completo
MAIN_CLASS="ar.edu.ues21.seminario.Main"

# Directorio de recursos
RESOURCES_DIR="resources/"

# Obtener el hash corto del último commit
GIT_COMMIT_HASH=$(git rev-parse --short HEAD 2>/dev/null || echo "no-git")

# Nombre del archivo JAR con identificador del último commit
JAR_FILE="SeminarioApp-${GIT_COMMIT_HASH}.jar"

# Entorno 
# develop|testing|produccion
APP_ENVIRONMENT=develop
```

---

Con este instructivo ya tenés todo para instalar, configurar y ejecutar el proyecto SiGeP.

---