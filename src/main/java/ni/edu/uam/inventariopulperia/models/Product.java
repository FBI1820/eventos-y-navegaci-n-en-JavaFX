package ni.edu.uam.inventariopulperia.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {
    private String codeProd;
    private String nameProd;
    private double Precio;
    private int cantidad;



}
