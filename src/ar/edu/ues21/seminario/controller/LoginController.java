package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.auth.AuthException;
import ar.edu.ues21.seminario.auth.AuthType;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.view.SessionManager;
import ar.edu.ues21.seminario.view.ViewHelper;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ar.edu.ues21.seminario.service.AuthService;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblError;

    private AuthService authService;

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        authService = new AuthService();
    }

    @FXML
    public void onLogin() {
         try {
            Usuario usuario = authService.login(txtUsername.getText(),txtPassword.getText());
            System.out.println("Logueado!");
            SessionManager.setUsuario(usuario);
            //cargarVistaPrincipal(usuario);
        } catch (AuthException e) {
            lblError.setText(e.getMessage());
             ViewHelper.mostrarError(e.getMessage());
        }
    }
}
