package android.despacho.com.ofinicaerp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelIngresos {

    private int id_ingresos;
    private int id_ruta;
    private String fecha;
    private String concepto;
    private double cantidad;

    private String ruta;

    public ModelIngresos(int id_ingresos, int id_ruta, String fecha, String concepto, double cantidad){
        this.id_ingresos = id_ingresos;
        this.id_ruta = id_ruta;
        this.fecha = fecha;
        this.concepto = concepto;
        this.cantidad = cantidad;
    }

    public ModelIngresos(int id_ruta, String fecha, String concepto, double cantidad){
        this.id_ruta = id_ruta;
        this.fecha = fecha;
        this.concepto = concepto;
        this.cantidad = cantidad;
    }

    public ModelIngresos(int id_ingresos, int id_ruta, String fecha, String concepto, double cantidad, String ruta){
        this.id_ingresos = id_ingresos;
        this.id_ruta = id_ruta;
        this.fecha = fecha;
        this.concepto = concepto;
        this.cantidad = cantidad;
        this.ruta = ruta;
    }

    public int getId_ingresos() {
        return id_ingresos;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public String getFecha() {
        return fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public double getCantidad() {
        return cantidad;
    }

    public String getRuta() {
        return ruta;
    }

    public String toJsonAddIngreso() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("idRuta", getId_ruta());
            jsonObject.put("fecha", getFecha());
            jsonObject.put("concepto", getConcepto());
            jsonObject.put("monto", getCantidad());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
