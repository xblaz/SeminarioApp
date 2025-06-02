package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.auth.AuthException;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.utils.DatabaseConexion;
import ar.edu.ues21.seminario.view.SessionManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ar.edu.ues21.seminario.service.AuthService;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtClave;
    @FXML private Label lblError;
    @FXML private Button btnCancelar;

    private AuthService authService;

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        authService = new AuthService();
        lblError.setVisible(false);
    }

    @FXML
    public void onLogin() {
         try {
            Usuario usuario = authService.login(txtUsuario.getText(),txtClave.getText());
            System.out.println("Logueado!");
            SessionManager.setUsuario(usuario);
            if (usuario != null) {
                cargarVistaPrincipal(usuario);
            }

            //
        } catch (AuthException e) {
             mostrarError(e.getMessage());
        }
    }

    @FXML
    public void onCancelar(ActionEvent e) {
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    private void mostrarError(String mensaje) {
        lblError.setText(mensaje);
        lblError.setVisible(true);
    }

    private void cargarVistaPrincipal(Usuario usuario) {
        try {
            // Cerrar la ventana de login actual
            Stage loginStage = (Stage) txtUsuario.getScene().getWindow();

            // Determinar qué vista cargar basado en los roles
            FXMLLoader loader;
            /*if (usuario.tieneRol("ADMINISTRADOR")) {
                loader = new FXMLLoader(getClass().getResource("/vistas/AdminDashboard.fxml"));
            } else if (usuario.tieneRol("OPERADOR")) {
                loader = new FXMLLoader(getClass().getResource("/vistas/OperadorDashboard.fxml"));
            } else {
                loader = new FXMLLoader(getClass().getResource("/vistas/UserDashboard.fxml"));
            }*/
            loader = new FXMLLoader(getClass().getResource("/fxml/principal.fxml"));

            // Cargar la nueva escena
            Parent root = loader.load();

            // Pasar el usuario al controlador de la nueva vista
           Object controller = loader.getController();
           if (controller instanceof PrincipalController) {
                ((PrincipalController) controller).setUsuario(usuario);
           }

            // Configurar y mostrar la nueva escena
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Sistema de Préstamos - Usuario: " + usuario.getNombre());
            stage.initModality(Modality.APPLICATION_MODAL);

            // Configurar ícono
            /*Image icon = new Image(getClass().getResourceAsStream("/images/app-icon.png"));
            stage.getIcons().add(icon);*/

            // Configurar comportamiento al cerrar
            stage.setOnCloseRequest(e -> {
                SessionManager.cerrarSesion();
                DatabaseConexion.cerrarConexion();
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
