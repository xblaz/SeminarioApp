#!/bin/bash

# Importar configuración
source ./config.sh

echo "🗑️ Limpiando compilados previos..."
rm -rf $OUT_DIR
mkdir -p $OUT_DIR

# --- Compilación ---
echo "🛠️ Compilando..."
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

# --- Ejecución ---
echo "🚀 Ejecutando aplicación en entorno $APP_ENVIRONMENT"
"$JAVA_HOME/bin/java" \
  --module-path "$JAVAFX_SDK/lib" \
  --add-modules javafx.controls,javafx.fxml \
  -Djava.library.path="$JAVAFX_SDK/lib" \
  -classpath "$OUT_DIR:$JAVAFX_SDK/lib/*" \
  -Dapp.environment="$APP_ENVIRONMENT" \
  "$MAIN_CLASS"
