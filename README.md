## Getting Started

# 📂 Estructura del Proyecto

Este documento describe la estructura de carpetas y archivos de la aplicación.

## Dependencias

Java 23
-sdk
-jenv

JavaFX 24
https://download2.gluonhq.com/openjfx/24/openjfx-24_linux-x64_bin-sdk.zip

MySQL
https://cdn.mysql.com/archives/mysql-connector-java-9.1/mysql-connector-j-9.1.0.tar.gz
Server (requiere docker)
docker run \
--name mysqldev \
-e MYSQL_ROOT_PASSWORD=strong_password \
-p 3306:3306 \
-v final-mysql-data:/var/lib/mysql \
-d mysql
