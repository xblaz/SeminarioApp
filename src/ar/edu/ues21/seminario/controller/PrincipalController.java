package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.view.SessionManager;
import ar.edu.ues21.seminario.view.Views;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {

    @FXML
    private BorderPane principalBorderPane;

    // Componentes de la interfaz
    @FXML
    private TextField idAdminField;
    @FXML
    private TextField idUsuarioField;
    @FXML
    private TextField nombreField;
    @FXML
    private TextField correoField;
    @FXML
    private PasswordField contrasenaField;
    @FXML
    private TextField numeroCuentaField;

    @FXML
    private Button btnConfiguracion;

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, String> colIdUsuario;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colCorreo;
    @FXML
    private TableColumn<Usuario, String> colNumeroCuenta;
    @FXML
    private TableColumn<Usuario, Double> colSaldo;
    @FXML
    private TableColumn<Usuario, String> colEstado;
    @FXML
    private TableColumn<Usuario, Void> colAcciones;

    @FXML
    private ComboBox<String> filtroUsuarios;
    @FXML
    private ComboBox<String> periodoEstadisticas;

    @FXML
    private Label statusLabel;
    @FXML
    private Label versionLabel;

    @FXML
    private PieChart graficaGastos;
    @FXML
    private BarChart<String, Number> graficaTransacciones;
    @FXML
    private LineChart<String, Number> graficaTendenciaSaldos;
    @FXML
    private AreaChart<String, Number> graficaActividad;

    @FXML
    private GridPane formUsuario;
    @FXML
    private TabPane tabPaneContenido;

    // Datos de ejemplo
    private final ObservableList<Usuario> listaUsuarios = FXCollections.observableArrayList();
    private boolean modoEdicion = false;
    private Usuario usuarioActual = null;


    /**
     * Segun la opción del menu seleccionada realiza la carga de la vista
     * en el centro de la aplicación
     *
     * @param fxmlFile
     */
    public void cargarFXML(Views vista) {
        try {
            // Limpiar el centro antes de cargar nueva vista
            principalBorderPane.setCenter(null);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/" + vista.getFxmlFile()));
            Parent root = loader.load();
            Object controller = loader.getController();
            if (controller instanceof SubController subControlador) {
                System.out.println("Entrando...");
                subControlador.setPrincipalController(this);
                subControlador.setUsuario(usuarioActual);
            }
            principalBorderPane.setCenter(root);

        } catch (IOException e) {
            e.printStackTrace();
            // Manejo de errores adecuado
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Inicializar componentes
        //cargarDatosEjemplo();
        //configurarTabla();

        //configurarFiltros();
        //configurarGraficas();
        //actualizarEstadisticas();

        configurarPermisos();

        // Actualizar barra de estado
        actualizarEstado("Sistema inicializado correctamente");
    }


    private void configurarPermisos(){
        if (!SessionManager.getUsuario().tienePermiso("crear_usuario")){
            btnConfiguracion.setDisable(true);
        }
    }
    private void configurarTabla() {
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCorreo.setCellValueFactory(new PropertyValueFactory<>("correo"));
        colNumeroCuenta.setCellValueFactory(new PropertyValueFactory<>("numeroCuenta"));
        colSaldo.setCellValueFactory(new PropertyValueFactory<>("saldo"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        // Configurar columna de acciones
        colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");

            {
                btnEditar.getStyleClass().add("table-button");
                btnEliminar.getStyleClass().add("table-button");

                btnEditar.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    editarUsuario(usuario);
                });

                btnEliminar.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    confirmarEliminarUsuario(usuario);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, btnEditar, btnEliminar));
                }
            }
        });

        tablaUsuarios.setItems(listaUsuarios);
    }

    private void cargarDatosEjemplo() {
        // Datos de ejemplo para la tabla
        listaUsuarios.addAll(
                new Usuario(1L, "Ana María López", "1234"),
                new Usuario(2L, "Carlos Rodríguez", "123")
        );
    }

    private void configurarFiltros() {
        filtroUsuarios.getItems().addAll("Todos", "Activos", "Inactivos", "Saldo > 1,000,000");
        filtroUsuarios.setValue("Todos");

        periodoEstadisticas.getItems().addAll("Último mes", "Últimos 3 meses", "Último año", "Todo el tiempo");
        periodoEstadisticas.setValue("Último mes");
    }

    private void configurarGraficas() {
        // Datos para gráfica de gastos
        ObservableList<PieChart.Data> datosGastos = FXCollections.observableArrayList(
                new PieChart.Data("Transferencias", 45),
                new PieChart.Data("Retiros", 25),
                new PieChart.Data("Pagos", 20),
                new PieChart.Data("Otros", 10)
        );
        graficaGastos.setData(datosGastos);

        // Datos para gráfica de transacciones
        XYChart.Series<String, Number> serieTransacciones = new XYChart.Series<>();
        serieTransacciones.setName("Transacciones");
        serieTransacciones.getData().add(new XYChart.Data<>("Ana", 28));
        serieTransacciones.getData().add(new XYChart.Data<>("Carlos", 15));
        serieTransacciones.getData().add(new XYChart.Data<>("Sofía", 32));
        serieTransacciones.getData().add(new XYChart.Data<>("Juan", 8));
        serieTransacciones.getData().add(new XYChart.Data<>("Valentina", 22));
        graficaTransacciones.getData().add(serieTransacciones);

        // Datos para gráfica de tendencia de saldos
        XYChart.Series<String, Number> serieSaldos = new XYChart.Series<>();
        serieSaldos.setName("Saldo Promedio");
        serieSaldos.getData().add(new XYChart.Data<>("Ene", 950000));
        serieSaldos.getData().add(new XYChart.Data<>("Feb", 1050000));
        serieSaldos.getData().add(new XYChart.Data<>("Mar", 1100000));
        serieSaldos.getData().add(new XYChart.Data<>("Abr", 1250000));
        serieSaldos.getData().add(new XYChart.Data<>("May", 1300000));
        serieSaldos.getData().add(new XYChart.Data<>("Jun", 1280000));
        graficaTendenciaSaldos.getData().add(serieSaldos);

        // Datos para gráfica de actividad
        XYChart.Series<String, Number> serieActividad = new XYChart.Series<>();
        serieActividad.setName("Transacciones");
        serieActividad.getData().add(new XYChart.Data<>("6am", 5));
        serieActividad.getData().add(new XYChart.Data<>("9am", 15));
        serieActividad.getData().add(new XYChart.Data<>("12pm", 25));
        serieActividad.getData().add(new XYChart.Data<>("3pm", 22));
        serieActividad.getData().add(new XYChart.Data<>("6pm", 30));
        serieActividad.getData().add(new XYChart.Data<>("9pm", 18));
        serieActividad.getData().add(new XYChart.Data<>("12am", 8));
        graficaActividad.getData().add(serieActividad);
    }

    // Métodos de acción

    @FXML
    private void nuevoUsuario() {
        limpiarFormulario();
        mostrarFormulario(true);
        modoEdicion = false;
        usuarioActual = null;
        actualizarEstado("Creando nuevo usuario");
    }

    @FXML
    private void guardarUsuario() {
        // Validar campos
        if (nombreField.getText().isEmpty() || correoField.getText().isEmpty() ||
                numeroCuentaField.getText().isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        if (modoEdicion && usuarioActual != null) {
            // Actualizar usuario existente
            usuarioActual.setNombre(nombreField.getText());


            tablaUsuarios.refresh();
            actualizarEstado("Usuario actualizado: " + usuarioActual.getNombre());
        } else {
            // Crear nuevo usuario
            Usuario nuevoUsuario = new Usuario(
                    Long.valueOf(listaUsuarios.size() + 1),
                    nombreField.getText(),
                    ""
            );

            listaUsuarios.add(nuevoUsuario);
            actualizarEstado("Usuario creado: " + nuevoUsuario.getNombre());
        }

        mostrarFormulario(false);
        actualizarEstadisticas();
    }

    @FXML
    private void cancelarEdicion() {
        mostrarFormulario(false);
        actualizarEstado("Operación cancelada");
    }

    private void editarUsuario(Usuario usuario) {
        usuarioActual = usuario;
        modoEdicion = true;

        idUsuarioField.setText(String.valueOf(usuario.getId()));
        nombreField.setText(usuario.getNombre());

        mostrarFormulario(true);
        actualizarEstado("Editando usuario: " + usuario.getNombre());
    }

    private void confirmarEliminarUsuario(Usuario usuario) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Está seguro de eliminar este usuario?");
        alert.setContentText("Esta acción no se puede deshacer.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                listaUsuarios.remove(usuario);
                actualizarEstadisticas();
                actualizarEstado("Usuario eliminado: " + usuario.getNombre());
            }
        });
    }

    @FXML
    private void exportarUsuarios() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Usuarios");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            // Aquí iría la lógica para exportar a CSV
            actualizarEstado("Usuarios exportados a: " + file.getName());
        }
    }

    @FXML
    public void actualizarEstadisticas() {
        // Actualizar etiquetas de estadísticas
        //totalUsuariosLabel.setText(String.valueOf(listaUsuarios.size()));
        //totalTransaccionesLabel.setText("125");

        double saldoPromedio = 0.0;

        //saldoPromedioLabel.setText(String.format("$%.2f", saldoPromedio));
        //nuevosUsuariosLabel.setText("3");

        // Actualizar indicadores de crecimiento
        /*crecimientoUsuariosLabel.setText("+15% vs mes anterior");
        crecimientoTransaccionesLabel.setText("+8% vs mes anterior");
        crecimientoSaldoLabel.setText("+5% vs mes anterior");
        crecimientoNuevosLabel.setText("+20% vs mes anterior");*/

        actualizarEstado("Estadísticas actualizadas");
    }

    @FXML
    private void exportarEstadisticas() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Exportar Estadísticas");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            // Aquí iría la lógica para exportar estadísticas
            actualizarEstado("Estadísticas exportadas a: " + file.getName());
        }
    }

    // Métodos de navegación

    @FXML
    private void mostrarDashboard() {
        // Cambiar a la vista de dashboard
        actualizarEstado("Mostrando Dashboard");
    }

    @FXML
    private void mostrarUsuarios() {
        tabPaneContenido.getSelectionModel().select(0);
        actualizarEstado("Mostrando Gestión de Usuarios");
    }

    @FXML
    private void mostrarTransacciones() {
        // Cambiar a la vista de transacciones
        actualizarEstado("Mostrando Transacciones");
    }

    @FXML
    private void mostrarEstadisticas() {
        tabPaneContenido.getSelectionModel().select(1);
        actualizarEstado("Mostrando Estadísticas");
    }

    @FXML
    private void mostrarConfiguracion() {
        // Cambiar a la vista de configuración
        actualizarEstado("Mostrando Configuración");
        cargarFXML(Views.CONFIGURACION);
    }

    @FXML
    private void cerrarSesion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesión");
        alert.setHeaderText("¿Está seguro de cerrar la sesión?");
        alert.setContentText("Se perderán los cambios no guardados.");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                SessionManager.cerrarSesion();
                Platform.exit();
            }
        });
    }

    // Métodos auxiliares

    private void limpiarFormulario() {
        idAdminField.setText("");
        idUsuarioField.setText("");
        nombreField.setText("");
        correoField.setText("");
        contrasenaField.setText("");
        numeroCuentaField.setText("");
    }

    private void mostrarFormulario(boolean mostrar) {
        formUsuario.setVisible(mostrar);
        formUsuario.setManaged(mostrar);
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public void actualizarEstado(String mensaje) {
        statusLabel.setText(mensaje);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String tiempo = LocalDateTime.now().format(formatter);
        System.out.println("[" + tiempo + "] " + mensaje);
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }
}
