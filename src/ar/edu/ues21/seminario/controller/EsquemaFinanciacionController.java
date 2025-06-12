package ar.edu.ues21.seminario.controller;

import ar.edu.ues21.seminario.exception.RepositoryException;
import ar.edu.ues21.seminario.model.aplicacion.Cuota;
import ar.edu.ues21.seminario.model.aplicacion.EsquemaFinanciacion;
import ar.edu.ues21.seminario.model.aplicacion.Prestamo;
import ar.edu.ues21.seminario.model.seguridad.Usuario;
import ar.edu.ues21.seminario.service.ConfiguracionService;
import ar.edu.ues21.seminario.service.UsuarioService;
import ar.edu.ues21.seminario.utils.AppContext;
import ar.edu.ues21.seminario.view.EsquemaFormDialog;
import ar.edu.ues21.seminario.view.GenericTableView;
import ar.edu.ues21.seminario.view.Helper;
import ar.edu.ues21.seminario.view.UsuarioFormDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class EsquemaFinanciacionController implements Initializable {


    @FXML
    private VBox tableContainer;
    private GenericTableView<EsquemaFinanciacion> tableView;

    private ConfiguracionService configuracionService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        configuracionService = AppContext.getConfiguracionService();
        tableView = new GenericTableView<>(EsquemaFinanciacion.class, () -> {
            try {
                return configuracionService.obtenerTodosEsquemaFinanciacion();
            } catch (RepositoryException e) {
                Helper.errorCritico(e.getMessage());
                throw new RuntimeException(e); // o mostrás alerta en UI
            }
        });
        // Configurar columnas del tableview
        String[] columnNames = {"ID", "Amortización", "Descripción", "Interes", "Max. Cuotas", "Estado"};
        String[] propertyNames = {"idEsquema", "tipoEsquema", "descripcion", "tasaInteres", "cantidadCuotas", "estado"};
        tableView.setupColumns(columnNames, propertyNames);
        tableView.loadData();
        tableContainer.getChildren().add(tableView.getTableView());
    }

    @FXML
    public void agregarNuevoParametro(){
        EsquemaFormDialog dialog = new EsquemaFormDialog(null, configuracionService);
        dialog.showAndWait().ifPresent(esquema -> {
            tableView.loadData();
        });
    }


}
