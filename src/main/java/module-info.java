module ni.edu.uam.inventariopulperia {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.desktop;

    opens ni.edu.uam.inventariopulperia to javafx.fxml;
    opens ni.edu.uam.inventariopulperia.controllers to javafx.fxml;


    exports ni.edu.uam.inventariopulperia;
    exports ni.edu.uam.inventariopulperia.controllers;
}