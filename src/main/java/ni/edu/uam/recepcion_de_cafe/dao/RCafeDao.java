package ni.edu.uam.recepcion_de_cafe.dao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ni.edu.uam.recepcion_de_cafe.model.RCafe;
public class RCafeDao {
        private final ObservableList<RCafe> listaLotes = FXCollections.observableArrayList();

        public RCafeDao() {
            // Carga inicial de lotes de prueba
            listaLotes.add(new RCafe(1, "Juan Pérez", "Arabica", 450.5));
            listaLotes.add(new RCafe(2, "María Gómez", "Robusta", 320.0));
            listaLotes.add(new RCafe(3, "Carlos Ruiz", "Bourbon", 610.2));
        }

        public ObservableList<RCafe> obtenerLotes() {
            return listaLotes;
        }

        public void eliminarLote(RCafe lote) {
            listaLotes.remove(lote);
        }
    }

