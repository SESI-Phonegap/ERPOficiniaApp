package android.despacho.com.ofinicaerp.models;


import org.json.JSONException;
import org.json.JSONObject;

public class ModelGastosGasolina {
    private int id_gasto_gas;
    private int id_vehiculo;
    private String fecha;
    private String tipo_gas;
    private double cantidad_litros;
    private double costo;

    private String carro;

    public ModelGastosGasolina(int id_gasto_gas, int id_vehiculo, String fecha,
                               String tipo_gas, double cantidad_litros, double costo){
        this.id_gasto_gas = id_gasto_gas;
        this.id_vehiculo = id_vehiculo;
        this.fecha = fecha;
        this.tipo_gas = tipo_gas;
        this.cantidad_litros = cantidad_litros;
        this.costo = costo;
    }

    public ModelGastosGasolina(int id_vehiculo, String fecha,
                               String tipo_gas, double cantidad_litros, double costo){

        this.id_vehiculo = id_vehiculo;
        this.fecha = fecha;
        this.tipo_gas = tipo_gas;
        this.cantidad_litros = cantidad_litros;
        this.costo = costo;
    }

    public ModelGastosGasolina(String fecha, String carro, double cantidad_litros, double costo){

        this.fecha = fecha;
        this.carro = carro;
        this.cantidad_litros = cantidad_litros;
        this.costo = costo;
    }
    public int getId_gasto_gas() {
        return id_gasto_gas;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getTipo_gas() {
        return tipo_gas;
    }

    public double getCantidad_litros() {
        return cantidad_litros;
    }

    public double getCosto() {
        return costo;
    }

    public String getCarro() {
        return carro;
    }

    public String toJsonAddGasolina() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("idvehiculo", getId_vehiculo());
            jsonObject.put("fecha", getFecha());
            jsonObject.put("gas", getTipo_gas());
            jsonObject.put("litros", getCantidad_litros());
            jsonObject.put("costo", getCosto());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
