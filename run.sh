#!/bin/bash
# Importar configuración
source ./config.sh

# --- Ejecución ---
echo "🚀 Ejecutando aplicación en entorno $APP_ENVIRONMENT"
"$JAVA_HOME/bin/java" \
  --module-path "$JAVAFX_SDK/lib" \
  --add-modules javafx.controls,javafx.fxml \
  -Djava.library.path="$JAVAFX_SDK/lib" \
  -classpath "$OUT_DIR:$JAVAFX_SDK/lib/*" \
  -Dapp.environment="$APP_ENVIRONMENT" \
  "$MAIN_CLASS"