# config.sh

# Ruta al JDK
JAVA_HOME="/home/cristian/.sdkman/candidates/java/23.0.2-tem"

# Ruta al SDK de JavaFX
JAVAFX_SDK="/home/cristian/Escritorio/Siglo_21/Seminario/proyecto/javafx-sdk-24.0.1"

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

# Nombre del archivo JAR con versión
JAR_FILE="SeminarioApp-${GIT_COMMIT_HASH}.jar"

# Entorno
APP_ENVIRONMENT=develop


