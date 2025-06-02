package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.model.seguridad.Usuario;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable, SubController {

    private Usuario usuarioActual = null;
    private PrincipalController principalController = null;

    @Override
    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @Override
    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.principalController.actualizarEstado("Gestión de usuarios");
    }
}
