package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.config.Configuracion;
import ar.edu.ues21.seminario.exception.AuthException;
import ar.edu.ues21.seminario.exception.LogicaException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.service.AuthService;
import ar.edu.ues21.seminario.utils.Log;
import ar.edu.ues21.seminario.view.SessionManager;
import ar.edu.ues21.seminario.view.Views;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.stream.Collectors;

public class LoginController {

    @FXML
    private Label appTitulo;
    @FXML
    private Label appVersion;
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField txtClave;
    @FXML
    private Label lblError;
    @FXML
    private Button btnCancelar;

    private AuthService authService;

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        authService = new AuthService();
        lblError.setVisible(false);
        appTitulo.setText(Configuracion.APP_NAME);
        appVersion.setText(Configuracion.APP_VERSION);
    }

    @FXML
    public void onLogin() {
        try {
            Usuario usuario = authService.login(txtUsuario.getText(), txtClave.getText());
            Log.info(String.format("Usuario %s logueado correctamente", usuario.getNombre()));
            SessionManager.setUsuario(usuario);
            if (usuario != null) {
                cargarVistaPrincipal(usuario);
            }
        } catch (LogicaException | AuthException e) {
            mostrarError(e.getMessage());
            Log.error(e.getMessage());
        }
    }

    @FXML
    public void onCancelar(ActionEvent e) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setMaxWidth(Double.MAX_VALUE);
        lblError.setWrapText(true);
        lblError.setVisible(true);
        Tooltip tooltip = new Tooltip(mensaje);
        Tooltip.install(lblError, tooltip);
    }

    private void cargarVistaPrincipal(Usuario usuario) {
        try {
            // Cerrar la ventana de login actual
            Stage loginStage = (Stage) txtUsuario.getScene().getWindow();
            FXMLLoader loader;
            loader = new FXMLLoader(getClass().getResource("/fxml/" + Views.PRINCIPAL.getFxmlFile()));
            // Cargar la nueva escena
            Parent root = loader.load();

            // Lista de roles
            String roles = usuario.getListaRoles().stream()
                            .map(Rol::getDescripcion)
                            .collect(Collectors.joining(", ", "(", ")"));

            // Configurar y mostrar la nueva escena
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle( String.format("%s - Usuario como %s - Roles: %s  ", Configuracion.APP_NAME, usuario.getNombre(), roles));
            stage.initModality(Modality.APPLICATION_MODAL);

            // Configurar comportamiento al cerrar
            stage.setOnCloseRequest(e -> {
                SessionManager.cerrarSesion();
                Platform.exit();
            });

            // Mostrar la nueva ventana y cerrar la actual
            stage.show();
            loginStage.close();

        } catch (IOException e) {
            mostrarError("Error al cargar la interfaz: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
