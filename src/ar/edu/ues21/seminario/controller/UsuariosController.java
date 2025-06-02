package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.model.seguridad.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuariosController implements Initializable, SubController {
    private Usuario usuarioLogueado = null;
    private Usuario usuarioSelecionado = null;
    private PrincipalController principalController = null;
    @FXML private TableView<Usuario> tablaUsuarios;
    @FXML private TableColumn<Usuario, String> colIdUsuario;
    @FXML private TableColumn<Usuario, String> colNombre;
    @FXML private TableColumn<Usuario, String> colFechaBaja;
    @FXML private TableColumn<Usuario, Void> colAcciones;
    @FXML private GridPane formUsuario;
    private boolean modoEdicion = false;
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
        // Ocultar formulario inicialmente
        formUsuario.setVisible(false);
        formUsuario.setManaged(false);
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
}
