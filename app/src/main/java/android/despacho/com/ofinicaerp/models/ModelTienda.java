package android.despacho.com.ofinicaerp.models;

public class ModelTienda {
    private int id_tienda;
    private String nombre;
    private String direccion;

    private int id_ruta;
    private String ruta;

    public ModelTienda(int id_tienda, String nombre, String direccion, int id_ruta, String ruta){
        this.id_tienda = id_tienda;
        this.nombre = nombre;
        this.direccion = direccion;
        this.id_ruta = id_ruta;
        this.ruta = ruta;
    }


    public int getId_tienda() {
        return id_tienda;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public String getRuta() {
        return ruta;
    }
}
