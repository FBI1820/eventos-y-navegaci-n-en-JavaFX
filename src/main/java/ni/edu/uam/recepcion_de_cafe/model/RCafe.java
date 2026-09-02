package ni.edu.uam.recepcion_de_cafe.model;

public class RCafe {

    private int id;
    private String productor;
    private String variedad;
    private double kilos;

    public RCafe(int id, String productor, String variedad, double kilos) {
        this.id = id;
        this.productor = productor;
        this.variedad = variedad;
        this.kilos = kilos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getProductor() { return productor; }
    public void setProductor(String productor) { this.productor = productor; }

    public String getVariedad() { return variedad; }
    public void setVariedad(String variedad) { this.variedad = variedad; }

    public double getKilos() { return kilos; }
    public void setKilos(double kilos) { this.kilos = kilos; }
}

