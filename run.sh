#!/bin/bash

# Ruta a tu instalación de Java 11
JAVA_HOME="/home/cristian/.sdkman/candidates/java/17.0.14-sem"

# Ruta al JavaFX SDK (JARs y librerías nativas)
JAVAFX_SDK="/sda2/proyecto_seminario/javafx-sdk-21.0.7"

# Ruta de clases compiladas del proyecto
CLASSES="/sda2/proyecto_seminario/SeminarioApp/out/production/SeminarioApp"

# Ejecutar la aplicación
"$JAVA_HOME/bin/java" \
  --module-path "$JAVAFX_SDK/lib" \
  --add-modules javafx.controls,javafx.fxml \
  -Djava.library.path="$JAVAFX_SDK/lib" \
  -classpath "$CLASSES:$JAVAFX_SDK/lib/*" \
  ar.edu.ues21.seminario.App
