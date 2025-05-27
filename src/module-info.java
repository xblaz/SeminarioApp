module seminario {
    requires javafx.controls;
    requires javafx.fxml;

    opens seminario to javafx.fxml;
    exports seminario;
}