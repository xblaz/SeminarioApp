package ar.edu.ues21.seminario.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Helper {
    public static void cambiarEscena(Stage stage, String fxml) throws IOException {
        Parent root = FXMLLoader.load(Helper.class.getResource("/fxml/" + fxml));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void mostrarError(String pMensaje) {
        Alert mensaje = new Alert(Alert.AlertType.ERROR);
        mensaje.setTitle("Error");
        mensaje.setContentText(pMensaje);
        mensaje.showAndWait();
    }
}
