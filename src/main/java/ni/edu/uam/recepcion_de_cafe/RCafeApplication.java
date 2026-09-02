package ni.edu.uam.recepcion_de_cafe;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;


public class RCafeApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(RCafeApplication.class.getResource("recepcion.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 450);
            stage.setTitle("Recepción de Cafe");
            stage.setScene(scene);
            stage.show();
        }
    }

