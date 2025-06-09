#!/bin/bash

# Incluir la configuración
source ./config.sh

# --- Compilar ---
echo "🛠️ Compilando fuentes..."
mkdir -p "$OUT_DIR"
find "$SRC_DIR" -name "*.java" > sources.txt

"$JAVA_HOME/bin/javac" \
  --module-path "$JAVAFX_SDK/lib" \
  --add-modules javafx.controls,javafx.fxml \
  -d "$OUT_DIR" \
  @sources.txt

rm sources.txt
echo "✅ Compilación completada."

# --- Copiar recursos ---
echo "📁 Copiando recursos..."
mkdir -p "$OUT_DIR/fxml"
mkdir -p "$OUT_DIR/css"
mkdir -p "$OUT_DIR/images"
mkdir -p "$OUT_DIR/sql_templates"

cp -r "$RESOURCES_DIR"/fxml/* "$OUT_DIR/fxml"
cp -r "$RESOURCES_DIR"/css/* "$OUT_DIR/css"
cp -r "$RESOURCES_DIR"/images/* "$OUT_DIR/images"
cp -r "$RESOURCES_DIR"/sql_templates/* "$OUT_DIR/sql_templates"

# --- Crear MANIFEST.MF dentro de OUT_DIR ---
echo "🗒️ Creando MANIFEST.MF..."
MANIFEST_FILE="$OUT_DIR/manifest.txt"
echo "Main-Class: $MAIN_CLASS" > "$MANIFEST_FILE"
echo "Class-Path: ." >> "$MANIFEST_FILE"

# --- Crear JAR ---
echo "📦 Creando JAR $JAR_FILE..."
cd "$OUT_DIR" || exit
jar cfm "../../$JAR_FILE" "manifest.txt" *

cd - > /dev/null
rm "$MANIFEST_FILE"

echo "✅ JAR creado: $JAR_FILE"

# --- Instrucción de ejecución ---
echo ""
echo "🚀 Para ejecutar el JAR, usá este comando:"
echo "java --module-path $JAVAFX_SDK/lib --add-modules javafx.controls,javafx.fxml -jar $JAR_FILE"
