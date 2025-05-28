package ar.edu.ues21.seminario.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ar.edu.ues21.seminario.service.LoginService;

public class LoginController {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    private final LoginService loginService = new LoginService();

    public void initialize() {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");

    }

    @FXML
    public void onLogin() {
        String user = txtUsername.getText();
        String pass = txtPassword.getText();

        //if (loginService.login(user, pass)) {
            // AlertHelper.showInfo("Login exitoso", "Bienvenido " + user);
        //} else {
            // AlertHelper.showError("Error de autenticación", "Usuario o contraseña
            // incorrectos.");
       // }
    }
}
