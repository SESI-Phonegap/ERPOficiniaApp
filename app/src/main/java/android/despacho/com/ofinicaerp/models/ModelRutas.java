package android.despacho.com.ofinicaerp.models;


public class ModelRutas {
    private int id_ruta;
    private int id_tienda;
    private String ruta;

    public ModelRutas(int id_ruta,int id_tienda, String ruta){
        this.id_ruta = id_ruta;
        this.id_tienda = id_tienda;
        this.ruta = ruta;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public int getId_tienda() {
        return id_tienda;
    }

    public String getRuta() {
        return ruta;
    }
}
