package android.despacho.com.ofinicaerp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelVehiculo {
    private int id_vehiculo;
    private String nombre;
    private String modelo;
    private String marca;
    private String serie;
    private int id_empleado;
    private String photoBase64;
    private String placas;
    private String color;

    public ModelVehiculo(int id_vehiculo, String nombre, String modelo, String marca,
                         String serie, int id_empleado, String photoBase64){
        this.id_vehiculo = id_vehiculo;
        this.nombre = nombre;
        this.modelo = modelo;
        this.marca = marca;
        this.serie = serie;
        this.id_empleado = id_empleado;
        this.photoBase64 = photoBase64;
    }

    public ModelVehiculo(String nombre, String modelo, String marca,
                         String serie, int id_empleado, String photoBase64){
        this.nombre = nombre;
        this.modelo = modelo;
        this.marca = marca;
        this.serie = serie;
        this.id_empleado = id_empleado;
        this.photoBase64 = photoBase64;

    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getSerie() {
        return serie;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public String getPlacas() {
        return placas;
    }

    public String getColor() {
        return color;
    }

    public String toJsonAddVehiculo() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("nombre", getNombre());
            jsonObject.put("modelo", getModelo());
            jsonObject.put("marca", getMarca());
            jsonObject.put("serie", getSerie());
            jsonObject.put("idEmpleado", getId_empleado());
            jsonObject.put("photoBase64", getPhotoBase64());
            jsonObject.put("placas", getPlacas());
            jsonObject.put("color", getColor());


            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
