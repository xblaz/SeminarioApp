package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.view.datamodel.UsuarioDataModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

public class UsuariosController implements Initializable, SubController {

    private Usuario usuarioLogueado = null;
    private Usuario usuarioSelecionado = null;
    private PrincipalController principalController = null;

    // Componentes de la interfaz
    @FXML private TextField nombreUsuarioField;
    @FXML private PasswordField passwordField;
    @FXML private ListView<Rol> viewRoles;

    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, Long> colIdUsuario;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colFechaAlta;
    @FXML private TableColumn<Usuario, String> colFechaBaja;
    @FXML private TableColumn<Usuario, String> colEstado;
    @FXML private TableColumn<Usuario, Void> colAcciones;
    @FXML private GridPane formUsuario;
    private boolean modoEdicion = false;

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
        // Inicializa tabla
        colIdUsuario.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        colFechaAlta.setCellValueFactory(cellData -> {
            Date fecha = cellData.getValue().getFechaAlta();
            String fechaFormateada = fecha != null ? new SimpleDateFormat("dd/MM/yyyy").format(fecha) : "";
            return new SimpleStringProperty(fechaFormateada);
        });

        colFechaBaja.setCellValueFactory(cellData -> {
            Date fecha = cellData.getValue().getFechaBaja();
            String fechaFormateada = fecha != null ? new SimpleDateFormat("dd/MM/yyyy").format(fecha) : "";
            return new SimpleStringProperty(fechaFormateada);
        });

        colEstado.setCellValueFactory(cellData -> {
            Boolean estado = cellData.getValue().getEstado();
            return new SimpleStringProperty(estado != null && estado ? "Activo" : "Inactivo");
        });

        /*colRoles.setCellValueFactory(cellData -> {
            List<Rol> roles = cellData.getValue().getListaRoles();
            String nombres = roles.stream()
                    .map(Rol::getNombre) // suponiendo que Rol tiene getNombre()
                    .collect(Collectors.joining(", "));
            return new SimpleStringProperty(nombres);
        });*/

        // Configurar columna de acciones
        colAcciones.setCellFactory(param -> new TableCell<>() {
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
        });


        // Ocultar formulario inicialmente
        formUsuario.setVisible(false);
        formUsuario.setManaged(false);

        cargarDatosDePrueba();
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
        List<Usuario> usuarios = new ArrayList<>();

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
        u2.setListaRoles(Arrays.asList(new Rol("Guest")));

        usuarios.add(u1);
        usuarios.add(u2);

        tablaUsuarios.setItems(FXCollections.observableArrayList(usuarios));
    }
}
