mysql> create database seminario_db;
CREATE USER 'seminario'@'%' IDENTIFIED BY 'password123';
GRANT ALL PRIVILEGES ON *.* TO 'seminario'@'%';
FLUSH PRIVILEGES;
