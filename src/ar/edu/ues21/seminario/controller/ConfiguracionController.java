package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.view.Vista;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
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
    }

    public void cargarFXML(Vista vista) {
        try {
            // Limpiar el centro antes de cargar nueva vista
            configuracionBorderPane.setCenter(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + vista.getFxmlFile()));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controller instanceof SubController subControlador) {
                System.out.println("Entrando...");
                //subControlador.setPrincipalController(this);
                subControlador.setUsuario(usuarioActual);
            }
            configuracionBorderPane.setCenter(root);
        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores adecuado
        }
    }


    @FXML
    private void mostrarAbmUsuario() {
        cargarFXML(Vista.USUARIO_ABM);
    }
}
