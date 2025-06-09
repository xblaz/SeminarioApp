package ar.edu.ues21.seminario.view;

import ar.edu.ues21.seminario.config.Configuracion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GenericTableView<T> {
    private Class<T> type;
    private final TableView<T> tableView;
    private final Supplier<List<T>> dataSupplier;
    private final ObservableList<T> observableList = FXCollections.observableArrayList();

    public GenericTableView(Class<T> type, Supplier<List<T>> dataSupplier) {
        this.tableView = new TableView<>();
        this.dataSupplier = dataSupplier;
        this.type = type;
        this.tableView.setItems(observableList);
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

            String columnName = columnNames[i];
            String propertyName = propertyNames[i];

            TableColumn<T, Object> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new PropertyValueFactory<>(propertyName));
            try {
                Field field = type.getDeclaredField(propertyName);
                Class<?> fieldType = field.getType();

                if (fieldType == LocalDate.class) {
                    column.setCellFactory(col -> new TextFieldTableCell<>(new StringConverter<>() {
                        private final DateTimeFormatter formatter = Configuracion.DEFAULT_FORMATO_FECHA;
                        @Override
                        public String toString(Object object) {
                            return object instanceof LocalDate ? formatter.format((LocalDate) object) : "";
                        }
                        @Override
                        public LocalDate fromString(String string) {
                            return LocalDate.parse(string, formatter);
                        }
                    }));
                } else if (fieldType.isEnum()) {
                    column.setCellFactory(col -> new TextFieldTableCell<>(new StringConverter<>() {
                        @Override
                        public String toString(Object object) {
                            if (object == null) return "";
                            try {
                                return (String) object.getClass().getMethod("getDescripcion").invoke(object);
                            } catch (Exception e) {
                                return object.toString();
                            }
                        }
                        @Override
                        public Object fromString(String string) {
                            return null; // No soporta edición desde la tabla
                        }
                    }));
                }
            } catch (Exception e) {
                // Campo no encontrado, se deja sin celda custom
            }

            tableView.getColumns().add(column);
        }
    }

    public void loadData() {
        try {
            List<T> items = dataSupplier.get();
            observableList.setAll(items);
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
