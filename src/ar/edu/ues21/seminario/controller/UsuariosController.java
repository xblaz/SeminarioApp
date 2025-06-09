package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.seguridad.EstadoUsuario;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.service.UsuarioService;
import ar.edu.ues21.seminario.utils.AppContext;
import ar.edu.ues21.seminario.view.GenericTableView;
import ar.edu.ues21.seminario.view.UsuarioFormDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable {
    @FXML
    private VBox tableContainer;
    private GenericTableView<Usuario> tableView;
    private UsuarioService usuarioService;
    private ListView<Rol> listViewRoles = new ListView<>();  ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Obtengo servicios desde el contexto
        usuarioService = AppContext.getUsuarioService();
        // Inicializar la tabla genérica
        tableView = new GenericTableView<>(Usuario.class, () -> {
            try {
                return usuarioService.buscarTodos();
            } catch (RepositoryException e) {
                throw new RuntimeException(e); // o mostrás alerta en UI
            }
        });
        // Configurar columnas del tableview
        String[] columnNames = {"ID", "Usuario", "Fecha alta", "Fecha baja", "Roles", "Estado"};
        String[] propertyNames = {"id", "nombre", "fechaAlta", "fechaBaja", "roles", "estado"};
        tableView.setupColumns(columnNames, propertyNames);
        // Configurar acciones del los botones del tableview
        tableView.setupCRUD(
                this::editarUsuario,
                this::eliminarUsuario
        );
        tableView.loadData();
        tableContainer.getChildren().add(tableView.getTableView());
    }

    @FXML
    private void agregarNuevoUsuario() {
        Usuario nuevo = new Usuario();
        UsuarioFormDialog dialog = new UsuarioFormDialog(nuevo, usuarioService);
        dialog.showAndWait().ifPresent(usuario -> {
            tableView.loadData();
        });
    }
    private void eliminarUsuario(Usuario usuarioSeleccionado) {

        if (usuarioSeleccionado.getId() < 0) {
            Alert advertencia = new Alert(
                    Alert.AlertType.WARNING,
                    String.format("El usuario %s no puede ser eliminado.", usuarioSeleccionado.getNombre()),
                    ButtonType.OK
            );
            advertencia.showAndWait();
            return;
        }
        if (EstadoUsuario.ELIMINADO.equals(usuarioSeleccionado.getEstado())) {
            Alert advertencia = new Alert(
                    Alert.AlertType.WARNING,
                    "El usuario ya está eliminado.",
                    ButtonType.OK
            );
            advertencia.showAndWait();
            return;
        }


        Alert confirmacion = new Alert(
                Alert.AlertType.CONFIRMATION,
                "¿Estás seguro de eliminar a " + usuarioSeleccionado.getNombre() + "?",
                ButtonType.YES, ButtonType.NO
        );

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                try {
                   usuarioService.eliminarUsuario(usuarioSeleccionado.getId());
                   tableView.loadData(); // Refrescar datos
                } catch (RepositoryException e) {
                    e.printStackTrace();
                }

            }
        });
    }
    private void editarUsuario(Usuario usuarioSeleccionado) {
        if (usuarioSeleccionado.getId() < 0) {
            Alert advertencia = new Alert(
                    Alert.AlertType.WARNING,
                    String.format("El usuario %s no puede ser editado.", usuarioSeleccionado.getNombre()),
                    ButtonType.OK
            );
            advertencia.showAndWait();
            return;
        }
        UsuarioFormDialog dialog = new UsuarioFormDialog(usuarioSeleccionado, usuarioService);
        dialog.showAndWait().ifPresent(usuario -> tableView.loadData());
    }
}
