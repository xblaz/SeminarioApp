package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.repository.seguridad.UsuarioRepository;
import ar.edu.ues21.seminario.view.GenericTableView;
import ar.edu.ues21.seminario.view.datamodel.UsuarioDataModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class UsuariosController implements Initializable, SubController {

    private Usuario usuarioLogueado = null;
    private Usuario usuarioSelecionado = null;
    private PrincipalController principalController = null;

    @FXML
    private VBox tableContainer;

    private GenericTableView<Usuario, Long> tableView;
    private UsuarioRepository usuarioRepository;

    // Componentes de la interfaz
    @FXML
    private TextField nombreUsuarioField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ListView<Rol> viewRoles;

    @FXML
    private TableView<Usuario> tablaUsuarios;
    @FXML
    private TableColumn<Usuario, Long> colIdUsuario;
    @FXML
    private TableColumn<Usuario, String> colNombre;
    @FXML
    private TableColumn<Usuario, String> colFechaAlta;
    @FXML
    private TableColumn<Usuario, String> colFechaBaja;
    @FXML
    private TableColumn<Usuario, String> colEstado;
    @FXML
    private TableColumn<Usuario, Void> colAcciones;
    @FXML
    private GridPane formUsuario;
    private final boolean modoEdicion = false;

    private UsuarioDataModel model;

    @Override
    public void setPrincipalController(PrincipalController principalController) {
        this.principalController = principalController;
    }

    @Override
    public void setUsuario(Usuario usuario) {
        this.usuarioLogueado = usuario;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Repository
        usuarioRepository = new UsuarioRepository();
        // Inicializar la tabla genérica
        tableView = new GenericTableView<>(Usuario.class, usuarioRepository);
        // Configurar columnas
        String[] columnNames = {"ID", "Nombre de usuario", "Fecha alta", "Fecha baja", "Roles", "Estado"};
        String[] propertyNames = {"id", "nombre", "fechaAlta", "fechaBaja", "roles", "estado"};
        tableView.setupColumns(columnNames, propertyNames);

        // Configurar CRUD
        tableView.setupCRUD(
                this::editarUsuario,
                this::eliminarUsuario
        );

        tableView.loadData();
        tableContainer.getChildren().add(tableView.getTableView());
        /*colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
*/
        /*colFechaAlta.setCellValueFactory(cellData -> {
            Date fecha = cellData.getValue().getFechaAlta();
            String fechaFormateada = fecha != null ? new SimpleDateFormat("dd/MM/yyyy").format(fecha) : "";
            return new SimpleStringProperty(fechaFormateada);
        });

        colFechaBaja.setCellValueFactory(cellData -> {
            Date fecha = cellData.getValue().getFechaBaja();
            String fechaFormateada = fecha != null ? new SimpleDateFormat("dd/MM/yyyy").format(fecha) : "";
            return new SimpleStringProperty(fechaFormateada);
        });*/

        /*colEstado.setCellValueFactory(cellData -> {
            Boolean estado = cellData.getValue().getEstado();
            return new SimpleStringProperty(estado != null && estado ? "Activo" : "Inactivo");
        });*/

        /*colRoles.setCellValueFactory(cellData -> {
            List<Rol> roles = cellData.getValue().getListaRoles();
            String nombres = roles.stream()
                    .map(Rol::getNombre) // suponiendo que Rol tiene getNombre()
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(nombres);
        });*/

        // Configurar columna de acciones
        /*colAcciones.setCellFactory(param -> new TableCell<>() {
            private final Button btnEditar = new Button("Editar");
            private final Button btnEliminar = new Button("Eliminar");

            {
                btnEditar.getStyleClass().add("table-button");

                btnEliminar.getStyleClass().add("table-button");

                btnEditar.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    //editarUsuario(usuario);
                });

                btnEliminar.setOnAction(event -> {
                    Usuario usuario = getTableView().getItems().get(getIndex());
                    //confirmarEliminarUsuario(usuario);
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
        });*/


        // Ocultar formulario inicialmente
        formUsuario.setVisible(false);
        formUsuario.setManaged(false);

        //cargarDatosDePrueba();
    }
    @FXML
    private void agregarNuevoUsuario() {
        Usuario nuevo = new Usuario();
        nuevo.setNombre("Nueva Persona");


        editarUsuario(nuevo); // Reutilizamos el mismo diálogo para edición
    }
    private void eliminarUsuario(Usuario usuario) {
        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION,
                "¿Estás seguro de eliminar a " + usuario.getNombre() + "?",
                ButtonType.YES, ButtonType.NO
        );

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {

                    usuarioRepository.delete(usuario.getId());
                    tableView.loadData(); // Refrescar datos

                } catch (RepositoryException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void editarUsuario(Usuario usuario) {
        // Crear diálogo de edición
        Dialog<Usuario> dialog = new Dialog<>();
        dialog.setTitle("Editar Usuario");
        dialog.setHeaderText("Editando a " + usuario.getNombre());

        // Configurar botones
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Crear formulario
        TextField nombreField = new TextField(usuario.getNombre());

        GridPane grid = new GridPane();
        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.setHgap(10);
        grid.setVgap(10);

        dialog.getDialogPane().setContent(grid);

        // Convertir resultado
        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                usuario.setNombre(nombreField.getText());
                return usuario;
            }
            return null;
        });

        // Mostrar diálogo y procesar resultado
        dialog.showAndWait().ifPresent(updateUsuario -> {
            try {
                usuarioRepository.save(updateUsuario);
                tableView.loadData(); // Refrescar datos
            } catch (RepositoryException e) {
                e.printStackTrace();
            }

        });
    }

    @FXML
    private void nuevoUsuario() {
        limpiarFormulario();
        mostrarFormulario(true);
        //modoEdicion = false;
        //usuarioActual = null;
        //actualizarEstado("Creando nuevo usuario");
    }

    @FXML
    private void cancelarEdicion() {
        mostrarFormulario(false);
    }


    private void limpiarFormulario() {
        /*idAdminField.setText("");
        idUsuarioField.setText("");
        nombreField.setText("");
        correoField.setText("");
        contrasenaField.setText("");
        numeroCuentaField.setText("");*/
    }

    private void mostrarFormulario(boolean mostrar) {
        formUsuario.setVisible(mostrar);
        formUsuario.setManaged(mostrar);
    }

    public Usuario getUsuarioLogueado() {
        return usuarioLogueado;
    }

    public void setUsuarioLogueado(Usuario usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    private void cargarDatosDePrueba() {
        /*List<Usuario> usuarios = new ArrayList<>();

        Usuario u1 = new Usuario();
        u1.setId(1L);
        u1.setNombre("Juan Pérez");
        u1.setFechaAlta(new Date());
        u1.setEstado(true);
        u1.setListaRoles(Arrays.asList(new Rol("Admin"), new Rol("User")));

        Usuario u2 = new Usuario();
        u2.setId(2L);
        u2.setNombre("Ana García");
        u2.setFechaAlta(new Date());
        u2.setEstado(false);
        u2.setListaRoles(List.of(new Rol("Guest")));

        usuarios.add(u1);
        usuarios.add(u2);

        tablaUsuarios.setItems(FXCollections.observableArrayList(usuarios));*/
    }
}
