#!/bin/bash

# Ruta al SDK de JavaFX
JAVAFX_SDK="/sda2/proyecto_seminario/javafx-sdk-17.0.15"

# Carpeta de salida
OUTPUT_DIR="out/production/SeminarioApp"

# Carpeta de fuentes
SRC_DIR="src"

# Crear carpeta de salida si no existe
mkdir -p "$OUTPUT_DIR"

# Compilar los archivos .java
find "$SRC_DIR" -name "*.java" > sources.txt

javac \
  --module-path "$JAVAFX_SDK/lib" \
  --add-modules javafx.controls,javafx.fxml \
  -d "$OUTPUT_DIR" \
  @sources.txt

# Limpieza
rm sources.txt

echo "✅ Compilación completada."

