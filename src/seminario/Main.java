package seminario;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seminario.view.SplashScreen;

public class Main extends Application {

    @Override
    public void init() throws Exception {
        // Esto se ejecuta ANTES del start(), en segundo plano
        // Inicializa la DB
        // Verifica sesión activa
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("seminario.fxml"));
        primaryStage.setTitle("App Prestamo");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) throws Exception {
        System.setProperty("javafx.preloader", SplashScreen.class.getName());
        launch(args);
    }
}
