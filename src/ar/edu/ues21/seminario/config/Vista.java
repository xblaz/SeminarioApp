package ar.edu.ues21.seminario.config;

public enum Vista {
    USUARIO_ABM("Usuarios", "configuracion/usuarios.fxml"),
    CONFIGURACION("Configuracion", "configuracion/configuracion.fxml"),
    PRINCIPAL("Principal", "principal.fxml");

    private final String titulo;
    private final String fxmlFile;

    Vista(String titulo, String fxmlFile) {
        this.titulo = titulo;
        this.fxmlFile = fxmlFile;
    }

    public String getTitulo() { return titulo; }
    public String getFxmlFile() { return fxmlFile; }
}
