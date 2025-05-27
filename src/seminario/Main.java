package seminario;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    /*@Override
    public void init() throws Exception {
        // Esto se ejecuta ANTES del start(), en segundo plano
        // Inicializa la DB
        // Verifica sesión activa
    }*/

    @Override
    public void start(Stage stage) throws Exception {
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Login App");
        stage.show();*/
         Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
         stage.setTitle("App Prestamo");
         stage.setScene(new Scene(root, 400, 300));
         stage.show();

    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
