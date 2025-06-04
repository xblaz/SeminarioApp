package ar.edu.ues21.seminario.view.datamodel;

import ar.edu.ues21.seminario.model.seguridad.Usuario;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuarioDataModel {

    private final ObservableList<Usuario> usuariosList = FXCollections.observableArrayList(
            new Usuario(-1L, "administrador", "123"),
            new Usuario(-2L, "operador", "123")
    );

    private final ObjectProperty<Usuario> usuarioActual = new SimpleObjectProperty<>();

    public ObservableList<Usuario> getUsuariosList() {
        return usuariosList;
    }

    public Usuario getUsuarioActual() {
        return usuarioActual.get();
    }

    public ObjectProperty<Usuario> usuarioActualProperty() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual.set(usuarioActual);
    }
}
