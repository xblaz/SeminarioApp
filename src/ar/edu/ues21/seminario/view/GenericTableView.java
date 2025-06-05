package ar.edu.ues21.seminario.view;

import ar.edu.ues21.seminario.repository.GenericRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.function.Consumer;

public class GenericTableView<T, ID> {
    private TableView<T> tableView;
    private Class<T> type;
    private GenericRepository<T, ID> repository;

    public GenericTableView(Class<T> type, GenericRepository<T, ID> repository) {
        this.type = type;
        this.repository = repository;
        this.tableView = new TableView<>();
    }

    /**
     * Configura las columnas de la tabla basado en los nombres de propiedades
     * @param columnNames Nombres de las columnas (para mostrar)
     * @param propertyNames Nombres de las propiedades en la clase T
     */
    public void setupColumns(String[] columnNames, String[] propertyNames) {
        if (columnNames.length != propertyNames.length) {
            throw new IllegalArgumentException("Los arrays de nombres deben tener la misma longitud");
        }
        tableView.getColumns().clear();
        for (int i = 0; i < columnNames.length; i++) {
            TableColumn<T, ?> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(new PropertyValueFactory<>(propertyNames[i]));
            tableView.getColumns().add(column);
        }
    }

    public void loadData() {
        try {
            List<T> items = repository.findAll();
            ObservableList<T> observableList = FXCollections.observableArrayList(items);
            tableView.setItems(observableList);
        } catch (Exception e) {
            // Manejo de errores
        }
    }

    public void setupCRUD(Consumer<T> onEdit, Consumer<T> onDelete) {
        TableColumn<T, Void> actionsCol = new TableColumn<>("Acciones");

        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button editBtn = new Button("Editar");
            private final Button deleteBtn = new Button("Eliminar");
            {
                editBtn.setOnAction(event -> onEdit.accept(getTableView().getItems().get(getIndex())));
                deleteBtn.setOnAction(event -> onDelete.accept(getTableView().getItems().get(getIndex())));
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(new HBox(5, editBtn, deleteBtn));
                }
            }
        });

        tableView.getColumns().add(actionsCol);
    }

    public TableView<T> getTableView() {
        return tableView;
    }
}
