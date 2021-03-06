package android.despacho.com.ofinicaerp.models;

import org.json.JSONException;
import org.json.JSONObject;



public class ModelPagoEmpleado {
    private int id_pago;
    private int id_empleado;
    private double monto;
    private String fecha;
    private int mes;
    private int semana;
    private String ano;

    private String nombreEmpleado;

    public ModelPagoEmpleado(int id_pago, int id_empleado, double monto,String fecha, int mes, int semana, String ano, String nombreEmpleado){
        this.id_pago = id_pago;
        this.id_empleado = id_empleado;
        this.monto = monto;
        this.fecha = fecha;
        this.mes = mes;
        this.semana = semana;
        this.ano = ano;
        this.nombreEmpleado = nombreEmpleado;

    }

    public ModelPagoEmpleado(int id_pago, int id_empleado, double monto, int mes, int semana, String ano, String nombreEmpleado){
        this.id_pago = id_pago;
        this.id_empleado = id_empleado;
        this.monto = monto;
        this.mes = mes;
        this.semana = semana;
        this.ano = ano;
        this.nombreEmpleado = nombreEmpleado;

    }

    public ModelPagoEmpleado(int id_empleado, double monto,String fecha, int mes, int semana, String ano){
        this.id_empleado = id_empleado;
        this.monto = monto;
        this.fecha = fecha;
        this.mes = mes;
        this.semana = semana;
        this.ano = ano;
    }

    public ModelPagoEmpleado(int mes,String ano){
        this.mes = mes;
        this.ano = ano;
    }

    public int getId_pago() {
        return id_pago;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public double getMonto() {
        return monto;
    }

    public int getMes() {
        return mes;
    }

    public int getSemana() {
        return semana;
    }

    public String getFecha() {
        return fecha;
    }

    public String getAno() {
        return ano;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public String toJSONAddNomina(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("idEmpleado", getId_empleado());
            jsonObject.put("fecha", getFecha());
            jsonObject.put("monto", getMonto());
            jsonObject.put("mes",getMes());
            jsonObject.put("semana",getSemana());
            jsonObject.put("ano",getAno());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String toJSONQueryNomina(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("mes",getMes());
            jsonObject.put("ano",getAno());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
