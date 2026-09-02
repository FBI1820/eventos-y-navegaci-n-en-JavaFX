package ni.edu.uam.inventariopulperia;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ProductAplicattion extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(ProductAplicattion.class.getResource("product-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Registro App");
        stage.setScene(scene);
        stage.show();

    }
}
