package android.despacho.com.ofinicaerp.models;


import org.json.JSONException;
import org.json.JSONObject;

public class ModelRutas {
    private int id_ruta;
    private String ruta;

    public ModelRutas(int id_ruta, String ruta){
        this.id_ruta = id_ruta;
        this.ruta = ruta;
    }

    public ModelRutas(String ruta){
        this.ruta = ruta;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public String getRuta() {
        return ruta;
    }

    public String toJsonAddRuta() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("ruta", getRuta());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
