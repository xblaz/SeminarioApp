package ar.edu.ues21.seminario.view;

import ar.edu.ues21.seminario.model.aplicacion.EsquemaFinanciacion;
import ar.edu.ues21.seminario.model.aplicacion.EstadoEsquema;
import ar.edu.ues21.seminario.model.aplicacion.TipoEsquema;
import ar.edu.ues21.seminario.model.seguridad.EstadoUsuario;
import ar.edu.ues21.seminario.service.ConfiguracionService;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import javax.swing.*;
import java.util.Arrays;


public class EsquemaFormDialog extends Dialog<EsquemaFinanciacion> {

    private final ConfiguracionService configuracionService;
    private final ChoiceBox<TipoEsquema> choiceBoxEsquema = new ChoiceBox<>();
    private TextField descripcionField = new TextField();
    private TextField tasaField = new TextField();
    private TextField maxCuotaField = new TextField();
    private TextField conGaranteField = new TextField();

    private final boolean esNuevo;
    public EsquemaFormDialog(EsquemaFinanciacion esquemaFinanciacion, ConfiguracionService configuracionService) {

        this.configuracionService = configuracionService;
        this.esNuevo = esquemaFinanciacion == null;

        getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);

        choiceBoxEsquema.getItems().setAll(TipoEsquema.values());
        choiceBoxEsquema.setConverter(new StringConverter<>() {
            @Override
            public String toString(TipoEsquema estado) {
                return estado == null ? "" : estado.getDescripcion();
            }
            @Override
            public TipoEsquema fromString(String string) {
                return Arrays.stream(TipoEsquema.values())
                        .filter(e -> e.getDescripcion().equals(string))
                        .findFirst().orElse(null);
            }
        });

        setTitle(esNuevo ? "Crear un nuevo esquema" : "Editar esquema");
        setHeaderText(esNuevo ? "Complete los datos del nuevo esquema" : "Edite los datos del esquema");

        // Setear contenido
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Sistema de amortización:"), 0, 0);
        grid.add(choiceBoxEsquema, 1, 0);

        grid.add(new Label("Descripción:"), 0, 1);
        grid.add(descripcionField, 1, 1);

        grid.add(new Label("Tasa:"), 0, 2);
        grid.add(tasaField, 1, 2);






        getDialogPane().setContent(grid);
    }
}
