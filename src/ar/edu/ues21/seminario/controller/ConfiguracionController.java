package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.model.seguridad.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfiguracionController implements SubController, Initializable {

    @FXML
    private BorderPane configuracionBorderPane;
    private Usuario usuarioActual = null;
    private PrincipalController principalController = null;

    @Override
    public void setPrincipalController(PrincipalController principalController) {
        System.out.println("Seteando Main Controller...");
        this.principalController = principalController;
    }

    @Override
    public void setUsuario(Usuario usuario) {
        System.out.println("Seteando Usuario...");
        this.usuarioActual = usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("initialize...");

        //this.principalController.actualizarEstado("OK..");
    }
}
