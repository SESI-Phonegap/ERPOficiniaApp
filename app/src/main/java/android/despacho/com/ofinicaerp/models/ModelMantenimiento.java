package android.despacho.com.ofinicaerp.models;

import org.json.JSONException;
import org.json.JSONObject;

import static android.despacho.com.ofinicaerp.activity.FormMantenimiento.montoActual_Gasto_Mantenimiento;

public class ModelMantenimiento {
    private int id_mantenimiento;
    private String mantenimiento;
    private String descripcion;
    private double costo;
    private String fecha;
    private int id_vehiculo;
    private String vehiculo;
    private String imgVehiculo;

    public ModelMantenimiento(int id_mantenimiento, String mantenimiento, String descripcion, double costo,
                               String fecha, int id_vehiculo){
        this.id_mantenimiento = id_mantenimiento;
        this.mantenimiento = mantenimiento;
        this.descripcion = descripcion;
        this.costo = costo;
        this.fecha = fecha;
        this.id_vehiculo = id_vehiculo;
    }

    public ModelMantenimiento(int id_mantenimiento, String mantenimiento, String descripcion, double costo,
                              String fecha, int id_vehiculo, String vehiculo, String imgVehiculo){
        this.id_mantenimiento = id_mantenimiento;
        this.mantenimiento = mantenimiento;
        this.descripcion = descripcion;
        this.costo = costo;
        this.fecha = fecha;
        this.id_vehiculo = id_vehiculo;
        this.vehiculo = vehiculo;
        this.imgVehiculo = imgVehiculo;
    }

    public ModelMantenimiento(String mantenimiento, String descripcion, double costo,
                              String fecha, int id_vehiculo){

        this.mantenimiento = mantenimiento;
        this.descripcion = descripcion;
        this.costo = costo;
        this.fecha = fecha;
        this.id_vehiculo = id_vehiculo;
    }

    public int getId_mantenimiento() {
        return id_mantenimiento;
    }


    public String getMantenimiento() {
        return mantenimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public double getCosto() {
        return costo;
    }

    public String getFecha() {
        return fecha;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public String getImgVehiculo() {
        return imgVehiculo;
    }

    public String toJsonAddMantenimiento() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("fecha", getFecha());
            jsonObject.put("mantenimiento", getMantenimiento());
            jsonObject.put("descripcion", getDescripcion());
            jsonObject.put("monto", getCosto());
            jsonObject.put("idVehiculo", getId_vehiculo());
            jsonObject.put("caja", montoActual_Gasto_Mantenimiento);


            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
