package android.despacho.com.ofinicaerp.models;


import org.json.JSONException;
import org.json.JSONObject;

import static android.despacho.com.ofinicaerp.activity.MenuPrincipal.caja;
import static android.despacho.com.ofinicaerp.activity.MenuPrincipal.montoActual_Gasto;

public class ModelGastos {
    private int id_gasto;
    private int id_ruta;
    private double monto;
    private String fecha;
    private int id_empleado;

    private String ruta;
    private String empleado;

    public ModelGastos(int id_gasto, int id_ruta, double monto, String fecha, int id_empleado){
        this.id_gasto = id_gasto;
        this.id_ruta = id_ruta;
        this.monto = monto;
        this.fecha = fecha;
        this.id_empleado = id_empleado;
    }

    public ModelGastos(int id_ruta, double monto, String fecha, int id_empleado){
        this.id_ruta = id_ruta;
        this.monto = monto;
        this.fecha = fecha;
        this.id_empleado = id_empleado;
    }

    public ModelGastos(int id_gasto, int id_ruta, double monto, String fecha, int id_empleado, String ruta, String empleado){
        this.id_gasto = id_gasto;
        this.id_ruta = id_ruta;
        this.monto = monto;
        this.fecha = fecha;
        this.id_empleado = id_empleado;
        this.ruta = ruta;
        this.empleado = empleado;
    }

    public ModelGastos(int id_gasto, double monto , String fecha){
        this.id_gasto = id_gasto;
        this.monto = monto;
        this.fecha = fecha;
    }

    public int getId_gasto() {
        return id_gasto;
    }

    public int getId_ruta() {
        return id_ruta;
    }

    public double getMonto() {
        return monto;
    }

    public String getFecha() {
        return fecha;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public String getRuta() {
        return ruta;
    }

    public String getEmpleado() {
        return empleado;
    }

    public String toJsonAddGasto() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("idRuta", getId_ruta());
            jsonObject.put("fecha", getFecha());
            jsonObject.put("monto", getMonto());
            jsonObject.put("idEmpleado", getId_empleado());
            jsonObject.put("caja",montoActual_Gasto);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
