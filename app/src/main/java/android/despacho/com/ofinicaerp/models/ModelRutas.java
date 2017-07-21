package android.despacho.com.ofinicaerp.models;


public class ModelRutas {
    private int id_ruta;
    private String ruta;

    public ModelRutas(int id_ruta, String ruta){
        this.id_ruta = id_ruta;
        this.ruta = ruta;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
