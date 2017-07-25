package android.despacho.com.ofinicaerp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelEmpleado {
    private int id_empleado;
    private String nombre;
    private String puesto;
    private double sueldo;
    private String empresa;
    private String photoBase64;
    private String telefono;


    public ModelEmpleado(int id_empleado, String nombre, String puesto, double sueldo, String empresa, String photoBase64, String telefono){
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.puesto = puesto;
        this.sueldo = sueldo;
        this.empresa = empresa;
        this.photoBase64 = photoBase64;
        this.telefono = telefono;
    }

    public ModelEmpleado(String nombre, String puesto, double sueldo, String empresa, String photoBase64, String telefono){
        this.nombre = nombre;
        this.puesto = puesto;
        this.sueldo = sueldo;
        this.empresa = empresa;
        this.photoBase64 = photoBase64;
        this.telefono = telefono;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public double getSueldo() {
        return sueldo;
    }

    public String getEmpresa() {
        return empresa;
    }

    public String getPhotoBase64() {
        return photoBase64;
    }

    public String getTelefono() {
        return telefono;
    }

    public String toJsonAddEmpleado() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("nombre", getNombre());
            jsonObject.put("puesto", getPuesto());
            jsonObject.put("sueldo", getSueldo());
            jsonObject.put("empresa", getEmpresa());
            jsonObject.put("photoBase64", getPhotoBase64());
            jsonObject.put("telefono", getTelefono());
            
            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
