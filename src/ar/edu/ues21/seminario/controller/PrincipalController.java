package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.config.Configuracion;
import ar.edu.ues21.seminario.view.SessionManager;
import ar.edu.ues21.seminario.view.Views;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    @FXML
    private BorderPane principalBorderPane;
    @FXML
    private Button btnConfiguracion;
    @FXML
    private Label statusLabel;
    @FXML
    private Label versionLabel;
    @FXML
    private Label appTitulo;

    /**
     * Segun la opción del menu seleccionada realiza la carga de la vista
     * en el centro de la aplicación
     *
     * @param
     */
    public void cargarFXML(Views vista) {
        try {
            // Limpiar el centro antes de cargar nueva vista
            principalBorderPane.setCenter(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + vista.getFxmlFile()));
            Parent root = loader.load();
            principalBorderPane.setCenter(root);

        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores adecuado
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configurarPermisos();
        // Actualizar barra de estado
        actualizarEstado("Sistema inicializado correctamente");
        appTitulo.setText(Configuracion.APP_NAME);
        versionLabel.setText(Configuracion.APP_VERSION);
    }

    private void configurarPermisos() {
        if (!SessionManager.getUsuario().tienePermiso("crear_usuario")) {
            btnConfiguracion.setDisable(true);
        }
    }

    @FXML
    private void mostrarConfiguracion() {
        // Cambiar a la vista de configuración
        actualizarEstado("Mostrando Configuración");
        cargarFXML(Views.CONFIGURACION);
        btnConfiguracion.getStyleClass().add("side-menu-button active-menu");
    }

    @FXML
    private void cerrarSesion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText("¿Está seguro de cerrar la sesión?");
        alert.setContentText("Se perderán los cambios no guardados.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                SessionManager.cerrarSesion();
                Platform.exit();
            }
        });
    }

    public void actualizarEstado(String mensaje) {
        statusLabel.setText(mensaje);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalDateTime.now().format(formatter);
        System.out.println("[" + tiempo + "] " + mensaje);
    }
}

