package seminario.view;

import javafx.application.Preloader;
import javafx.stage.Stage;

public class SplashScreen extends Preloader {

    private Stage splashStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.splashStage = primaryStage;
        // Diseña tu Splash Screen aquí (ImageView, ProgressBar, etc.)

        System.out.println("Splash...");

        splashStage.show();
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            splashStage.hide(); // Oculta el Splash cuando la app principal inicia
        }
    }
}
