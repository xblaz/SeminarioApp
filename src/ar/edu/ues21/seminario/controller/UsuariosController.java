package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.exception.ValidacionException;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.repository.seguridad.UsuarioRepository;
import ar.edu.ues21.seminario.service.UsuarioService;
import ar.edu.ues21.seminario.view.GenericTableView;
import ar.edu.ues21.seminario.view.Helper;
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

public class UsuariosController implements Initializable {
    private Usuario usuarioSelecionado = null;
    @FXML
    private VBox tableContainer;
    private GenericTableView<Usuario, Long> tableView;
    private UsuarioRepository usuarioRepository;
    private UsuarioService usuarioService;
    // Componentes de la interfaz
    @FXML
    private TextField nombreUsuarioField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ListView<Rol> viewRoles;
    @FXML
    private GridPane formUsuario;
    private boolean modoEdicion = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        usuarioRepository = new UsuarioRepository();
        usuarioService = new UsuarioService();

        // Inicializar la tabla genérica
        tableView = new GenericTableView<>(Usuario.class, usuarioRepository);
        // Configurar columnas
        String[] columnNames = {"ID", "Usuario", "Fecha alta", "Fecha baja", "Roles", "Estado"};
        String[] propertyNames = {"id", "nombre", "fechaAlta", "fechaBaja", "roles", "estado"};
        tableView.setupColumns(columnNames, propertyNames);

        // Configurar CRUD
        tableView.setupCRUD(
                this::editarUsuario,
                this::eliminarUsuario
        );

        tableView.loadData();
        tableContainer.getChildren().add(tableView.getTableView());

        // Ocultar formulario inicialmente
        formUsuario.setVisible(false);
        formUsuario.setManaged(false);
    }
    @FXML
    private void agregarNuevoUsuario() {
        limpiarFormulario();
        mostrarFormulario(true);
         //editarUsuario(nuevo); // Reutilizamos el mismo diálogo para edición
    }
    @FXML
    private void guardarUsuario(){

        try {
            usuarioService.crearUsuario(nombreUsuarioField.getText(), passwordField.getText(), null);
            tableView.loadData();
            limpiarFormulario();
            mostrarFormulario(false);
        }  catch (ValidacionException e) {
            Helper.errorValidacion(e.getMessage());
        }   catch (RepositoryException e) {
            Helper.errorValidacion(e.getMessage());
            limpiarFormulario();
            throw new RuntimeException(e);
        }

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
            /*try {
                usuarioService.crearUsuario(updateUsuario);
            } catch (RepositoryException e) {
                throw new RuntimeException(e);
            } catch (ValidacionException e) {
                throw new RuntimeException(e);
            }*/
            tableView.loadData(); // Refrescar datos

        });
    }

    @FXML
    private void nuevoUsuario() {
        limpiarFormulario();
        mostrarFormulario(true);
        modoEdicion = false;
        //usuarioActual = null;
        //actualizarEstado("Creando nuevo usuario");
    }

    @FXML
    private void cancelarEdicion() {
        mostrarFormulario(false);
        limpiarFormulario();
    }


    private void limpiarFormulario() {
        nombreUsuarioField.setText("");
        passwordField.setText("");
    }

    private void mostrarFormulario(boolean mostrar) {
        formUsuario.setVisible(mostrar);
        formUsuario.setManaged(mostrar);
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

}
