package ni.edu.uam.recepcion_de_cafe.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import ni.edu.uam.recepcion_de_cafe.dao.RCafeDao;
import ni.edu.uam.recepcion_de_cafe.model.RCafe;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class CafeController implements Initializable {

    // --- COMPONENTES DE LA TABLA ---
    @FXML private TableView<RCafe> tablaLotes;
    @FXML private TableColumn<RCafe, Integer> colId;
    @FXML private TableColumn<RCafe, String> colProductor;
    @FXML private TableColumn<RCafe, String> colVariedad;
    @FXML private TableColumn<RCafe, Double> colKilos;
    @FXML private Label lblDetalles;

    // --- COMPONENTES PARA REGISTRAR NUEVO LOTE ---
    @FXML private TextField txtProductor;
    @FXML private TextField txtVariedad;
    @FXML private TextField txtKilos;

    private final RCafeDao cafeDao = new RCafeDao();
    private int contadorId = 4; // Incrementador para nuevos IDs

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // 1. Mapeo de columnas con la clase RCafe
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colProductor.setCellValueFactory(new PropertyValueFactory<>("productor"));
        colVariedad.setCellValueFactory(new PropertyValueFactory<>("variedad"));
        colKilos.setCellValueFactory(new PropertyValueFactory<>("kilos"));

        // 2. Cargar datos iniciales
        tablaLotes.setItems(cafeDao.obtenerLotes());

        // 3. Ajustar el tamaño de las columnas al ancho de la tabla
        tablaLotes.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 4. Configurar eventos de ratón (MouseEvent y ContextMenu)
        configurarMenuContextualYEventos();
    }

    // --- MÉTODO PARA GUARDAR UN NUEVO LOTE ---
    @FXML
    private void guardarLote() {
        try {
            String productor = txtProductor.getText().trim();
            String variedad = txtVariedad.getText().trim();
            String kilosStr = txtKilos.getText().trim();

            if (productor.isEmpty() || variedad.isEmpty() || kilosStr.isEmpty()) {
                mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor complete todos los campos.");
                return;
            }

            double kilos = Double.parseDouble(kilosStr);

            // Crear y agregar el nuevo objeto a la lista
            RCafe nuevoLote = new RCafe(contadorId++, productor, variedad, kilos);
            cafeDao.obtenerLotes().add(nuevoLote);

            // Limpiar los campos
            txtProductor.clear();
            txtVariedad.clear();
            txtKilos.clear();
            lblDetalles.setText("Lote #" + nuevoLote.getId() + " registrado exitosamente.");

        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de entrada", "Ingrese un valor numérico válido para los kilos.");
        }
    }

    // --- EVENTOS DE TABLA (CLIC Y CLIC DERECHO) ---
    private void configurarMenuContextualYEventos() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem itemEditar = new MenuItem("Editar");
        MenuItem itemEliminar = new MenuItem("Eliminar");
        contextMenu.getItems().addAll(itemEditar, itemEliminar);

        tablaLotes.setRowFactory(tv -> {
            TableRow<RCafe> row = new TableRow<>();
            row.setOnMouseClicked((MouseEvent event) -> {
                if (!row.isEmpty()) {
                    RCafe seleccionado = row.getItem();

                    // Clic izquierdo: Muestra detalles
                    if (event.getButton() == MouseButton.PRIMARY) {
                        lblDetalles.setText(String.format("Lote #%d | Productor: %s | Variedad: %s | Kilos: %.2f kg",
                                seleccionado.getId(), seleccionado.getProductor(), seleccionado.getVariedad(), seleccionado.getKilos()));
                    }
                    // Clic derecho: Despliega menú contextual
                    else if (event.getButton() == MouseButton.SECONDARY) {
                        contextMenu.show(row, event.getScreenX(), event.getScreenY());
                    }
                } else {
                    contextMenu.hide();
                }
            });
            return row;
        });

        // Evento Editar
        itemEditar.setOnAction(e -> {
            RCafe seleccionado = tablaLotes.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                mostrarDialogoEditar(seleccionado);
            }
        });

        // Evento Eliminar con confirmación Alert
        itemEliminar.setOnAction(e -> {
            RCafe seleccionado = tablaLotes.getSelectionModel().getSelectedItem();
            if (seleccionado != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar eliminación");
                alert.setHeaderText("¿Desea eliminar el lote seleccionado?");
                alert.setContentText("Productor: " + seleccionado.getProductor());

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    cafeDao.eliminarLote(seleccionado);
                    lblDetalles.setText("Lote eliminado correctamente.");
                }
            }
        });
    }

    private void mostrarDialogoEditar(RCafe lote) {
        TextInputDialog dialog = new TextInputDialog(String.valueOf(lote.getKilos()));
        dialog.setTitle("Editar Lote");
        dialog.setHeaderText("Editar kilos de: " + lote.getProductor());
        dialog.setContentText("Nuevos Kilos:");

        dialog.showAndWait().ifPresent(kilosStr -> {
            try {
                lote.setKilos(Double.parseDouble(kilosStr));
                tablaLotes.refresh();
                lblDetalles.setText("Lote #" + lote.getId() + " actualizado.");
            } catch (NumberFormatException ex) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "Valor numérico no válido.");
            }
        });
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}