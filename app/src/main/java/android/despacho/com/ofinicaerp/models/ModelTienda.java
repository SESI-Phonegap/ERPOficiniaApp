package android.despacho.com.ofinicaerp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelTienda {
    private int id_tienda;
    private String nombre;
    private String direccion;

    private int id_ruta;
    private String ruta;

    public ModelTienda(int id_tienda, String nombre, String direccion, int id_ruta){
        this.id_tienda = id_tienda;
        this.nombre = nombre;
        this.direccion = direccion;
        this.id_ruta = id_ruta;
    }
    public ModelTienda(String nombre, String direccion, int id_ruta){
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

    public String toJsonAddTienda() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("tienda", getNombre());
            jsonObject.put("direccion", getDireccion());
            jsonObject.put("idRuta", getId_ruta());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
