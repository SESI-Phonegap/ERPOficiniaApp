package android.despacho.com.ofinicaerp.models;

public class ModelTienda {
    private int id_tienda;
    private String nombre;
    private String direccion;

    public ModelTienda(int id_tienda, String nombre, String direccion){
        this.id_tienda = id_tienda;
        this.nombre = nombre;
        this.direccion = direccion;
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
}
