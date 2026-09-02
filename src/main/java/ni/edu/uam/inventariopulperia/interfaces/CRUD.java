package ni.edu.uam.inventariopulperia.interfaces;

import java.util.List;

public interface CRUD<T> {
    public void agregar(T entidad);
    public List<T> obtenerRegistro();
}
