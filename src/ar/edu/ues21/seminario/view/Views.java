package ar.edu.ues21.seminario.view;

public enum Views {
    USUARIO_ABM("Usuarios", "configuracion/usuarios.fxml"),
    CONFIGURACION("Configuración", "configuracion/configuracion.fxml"),
    ESQUEMA_FINANCIACION_ABM("Esquemas financiación", "configuracion/esquema_financiacion.fxml"),
    PRINCIPAL("Principal", "principal.fxml");

    private final String titulo;
    private final String fxmlFile;

    Views(String titulo, String fxmlFile) {
        this.titulo = titulo;
        this.fxmlFile = fxmlFile;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getFxmlFile() {
        return fxmlFile;
    }
}
