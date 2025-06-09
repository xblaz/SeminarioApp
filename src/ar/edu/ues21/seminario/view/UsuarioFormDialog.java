package ar.edu.ues21.seminario.view;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.exception.ValidacionException;
import ar.edu.ues21.seminario.model.seguridad.EstadoUsuario;
import ar.edu.ues21.seminario.model.seguridad.Rol;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.service.UsuarioService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import java.util.Arrays;

public class UsuarioFormDialog extends Dialog<Usuario> {
    private final TextField nombreField = new TextField();
    private final PasswordField claveField = new PasswordField();
    private final PasswordField claveConfirmacionField = new PasswordField();
    private final ChoiceBox<EstadoUsuario> choiceBoxEstado = new ChoiceBox<>();
    private final ListView<Rol> listViewRoles = new ListView<>();
    private final Label errorLabel = new Label();
    private final ObservableList<Rol> rolesDisponibles = FXCollections.observableArrayList();

    private final UsuarioService usuarioService;
    private final boolean esNuevo;

    public UsuarioFormDialog(Usuario usuario, UsuarioService usuarioService) {
        try {
            rolesDisponibles.setAll(usuarioService.buscarRolesTodos());
        } catch (RepositoryException e) {
            e.printStackTrace();
        }

        errorLabel.getStyleClass().add("error-label");
        this.usuarioService = usuarioService;
        this.esNuevo = usuario.getId() == null;

        setTitle(esNuevo ? "Crear Usuario" : "Editar Usuario");
        setHeaderText(esNuevo ? "Complete los datos del nuevo usuario" : "Edite los datos del usuario");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);

        // Setear contenido
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        choiceBoxEstado.getItems().setAll(EstadoUsuario.values());
        choiceBoxEstado.setConverter(new StringConverter<>() {
            @Override
            public String toString(EstadoUsuario estado) {
                return estado == null ? "" : estado.getDescripcion();
            }
            @Override
            public EstadoUsuario fromString(String string) {
                return Arrays.stream(EstadoUsuario.values())
                        .filter(e -> e.getDescripcion().equals(string))
                        .findFirst().orElse(null);
            }
        });

        listViewRoles.setPrefHeight(115.0);
        listViewRoles.setPrefWidth(371.0);
        listViewRoles.getItems().setAll(rolesDisponibles);
        listViewRoles.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        nombreField.setText(usuario.getNombre());
        nombreField.setDisable(esNuevo ? false : true);
        claveField.setText("");
        claveConfirmacionField.setText("");
        choiceBoxEstado.setValue(usuario.getEstado());
        listViewRoles.getSelectionModel().clearSelection();

        for (Rol rol : usuario.getListaRoles()) {
            listViewRoles.getSelectionModel().select(rol);
        }

        listViewRoles.setCellFactory(lv -> new ListCell<Rol>() {
            @Override
            protected void updateItem(Rol rol, boolean empty) {
                super.updateItem(rol, empty);

                if (empty || rol == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Cambiar estilo si está seleccionado
                    if (getListView().getSelectionModel().getSelectedItems().contains(rol)) {
                        setStyle("-fx-background-color: #1A55E3; -fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                    setText(rol.getDescripcion());
                }
            }
        });

        grid.add(new Label("Nombre:"), 0, 0);
        grid.add(nombreField, 1, 0);
        grid.add(new Label("Clave:"), 0, 1);
        grid.add(claveField, 1, 1);
        grid.add(new Label("Confirmar Clave:"), 0, 2);
        grid.add(claveConfirmacionField, 1, 2);
        grid.add(new Label("Estado:"), 0, 3);
        grid.add(choiceBoxEstado, 1, 3);
        grid.add(new Label("Roles:"), 0, 4);
        grid.add(listViewRoles, 1, 4);
        grid.add(errorLabel, 1, 5);

        getDialogPane().setContent(grid);

        // Validación y creación/edición
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            try {
                usuario.setNombre(nombreField.getText());
                usuario.setEstado(choiceBoxEstado.getValue());
                usuario.setClave(claveField.getText());
                usuario.getListaRoles().clear();
                usuario.getListaRoles().addAll(listViewRoles.getSelectionModel().getSelectedItems());

                if (esNuevo) {
                    usuarioService.crearUsuario(usuario, claveField.getText(), claveConfirmacionField.getText());
                } else {
                    usuarioService.actualizarUsuario(usuario, claveField.getText(), claveConfirmacionField.getText());
                }
            } catch (ValidacionException | RepositoryException e) {
                errorLabel.setText(e.getMessage());
                event.consume(); // No cerrar el diálogo
            }
        });
        setResultConverter(buttonType -> buttonType == ButtonType.OK ? usuario : null);
    }
}