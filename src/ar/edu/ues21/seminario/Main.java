package ar.edu.ues21.seminario;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    /*@Override
    public void init() throws Exception {
        // Esto se ejecuta ANTES del start(), en segundo plano
        // Inicializa la DB
        // Verifica sesión activa
    }*/

    @Override
    public void start(Stage stage) throws Exception {
         Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setScene(new Scene(root, 600, 400));
         stage.show();

    }

   public static void main(String[] args) {
        launch(args);
    }
}
