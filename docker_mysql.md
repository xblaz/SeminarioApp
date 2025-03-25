# Crear contenedor

docker run --name seminario_mysql -e MYSQL_ROOT_PASSWORD=951951 -d mysql:latest

# Entrar para administrar

docker exec -it seminario_mysql bash
