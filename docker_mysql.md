# Crear contenedor MySQL y configurar base de datos `seminario_db`

Este instructivo detalla cómo levantar un contenedor MySQL con Docker y configurar una base de datos con un usuario que tenga permisos limitados de acceso.

---

## ✅ Paso 1: Crear el contenedor MySQL

Ejecutá el siguiente comando en tu terminal para crear un contenedor llamado `mysql_ues21` con MySQL y una contraseña de root:

```bash
docker run --name mysql_ues21 -e MYSQL_ROOT_PASSWORD=951951 -d mysql:latest
```

- `--name mysql_ues21`: asigna el nombre al contenedor.
- `-e MYSQL_ROOT_PASSWORD=951951`: define la contraseña del usuario `root`.
- `-d mysql:latest`: usa la última imagen oficial de MySQL en segundo plano.

---

## ✅ Paso 2: Ingresar al contenedor

Una vez creado el contenedor, ingresá con:

```bash
docker exec -it mysql_ues21 bash
```

Esto abrirá una terminal dentro del contenedor.

---

## ✅ Paso 3: Ingresar a la consola de MySQL

Desde dentro del contenedor, ejecutá:

```bash
mysql -u root -p
```

Cuando se solicite, ingresá la contraseña: `951951`.

---

## ✅ Paso 4: Ejecutar los comandos SQL

Una vez dentro de la consola de MySQL (`mysql>`), ejecutá los siguientes comandos en orden:

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

---

## 🔒 Seguridad (opcional)

- Para un entorno de producción, se recomienda reemplazar `'%'` por `'localhost'` o una IP específica:

```sql
CREATE USER 'seminario'@'localhost' IDENTIFIED BY 'password123';
```

- Además, se recomienda utilizar contraseñas más seguras en producción.

---

## 🧪 Verificar acceso (opcional)

Podés probar la conexión con el nuevo usuario fuera del contenedor o con un cliente MySQL compatible:

```bash
mysql -u seminario -p -h 127.0.0.1 -P 3306 seminario_db
```

---