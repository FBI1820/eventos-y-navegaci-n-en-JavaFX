package ni.edu.uam.inventariopulperia.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ni.edu.uam.inventariopulperia.dao.ProductDao;
import ni.edu.uam.inventariopulperia.models.Product;



public class ProductController {
    private ProductDao listado = new ProductDao();
    @FXML
    private TextField txtCode;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TextField txtBuscarCode;

    @FXML
    private Label lblProducto;

    @FXML
    protected void registrar(ActionEvent event) {
        if (!validarDatos()) {
            mostrarAlerta(Alert.AlertType.ERROR, "Campos vacíos", "Por favor llene todos los campos.");
            return;
        }

        try {
            if (leerDatos()) {
                limpiar();
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Producto registrado correctamente.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de formato", "Precio y Cantidad deben ser valores numéricos válidos.");
        }
    }

    @FXML
    protected void onActionBuscar(KeyEvent event){
        if(event.getCode() == KeyCode.ENTER){
            String code = txtBuscarCode.getText();
            Product product = listado.obtenerRegistro().stream()
                    .filter(p -> p.getCodeProd().equalsIgnoreCase(code))
                    .findFirst()
                    .orElse(null);

            if(product != null){
                lblProducto.setText("Producto: " + product.getNameProd() + ", Precio: " + product.getPrecio() + ", Cantidad: " + product.getCantidad());
            } else {
                lblProducto.setText("Producto no encontrado.");
            }
        }

    }

    private boolean leerDatos() {
        String code = txtCode.getText().trim();
        if (listado.existe(code)) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de registro", "El código del producto ya existe.");
            return false;
        }

        String name = txtName.getText().trim();
        Double precio = Double.parseDouble(txtPrecio.getText().trim());
        Integer cantidad = Integer.parseInt(txtCantidad.getText().trim());

        if (precio < 0 || cantidad < 0) {
            mostrarAlerta(Alert.AlertType.WARNING, "Valores Inválidos", "El precio y la cantidad deben ser mayores o iguales a 0.");
            return false;
        }

        agregarDatos(new Product(code, name, precio, cantidad));
        return true;
    }



    private void agregarDatos(Product product){
        listado.agregar(product);
    }


    private void limpiar(){
        txtCode.setText("");
        txtName.setText("");
        txtPrecio.setText("");
        txtCantidad.setText("");
        txtCode.requestFocus();
    }

    private Boolean validarDatos(){
        return !txtCode.getText().trim().isEmpty() &&
                !txtName.getText().trim().isEmpty() &&
                !txtPrecio.getText().trim().isEmpty() &&
                !txtCantidad.getText().trim().isEmpty();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }







}
