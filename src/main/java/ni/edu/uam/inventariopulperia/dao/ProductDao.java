package ni.edu.uam.inventariopulperia.dao;

import lombok.NoArgsConstructor;
import ni.edu.uam.inventariopulperia.interfaces.CRUD;
import ni.edu.uam.inventariopulperia.models.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDao implements CRUD<Product> {
    List<Product> products;



    public ProductDao() {

        this.products = new ArrayList<>();
    }

    @Override
    public void agregar(Product entidad) {
        products.add(entidad);

    }

    @Override
    public List<Product> obtenerRegistro() {
        return products;
    }

    public boolean existe(String codigo) {
        for (Product p : products) {
            if (p.getCodeProd().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }
}
