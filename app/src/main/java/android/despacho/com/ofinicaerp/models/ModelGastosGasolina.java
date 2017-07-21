package android.despacho.com.ofinicaerp.models;


public class ModelGastosGasolina {
    private int id_gasto_gas;
    private int id_vehiculo;
    private String fecha;
    private String tipo_gas;
    private double cantidad_litros;
    private double costo;

    public ModelGastosGasolina(int id_gasto_gas, int id_vehiculo, String fecha,
                               String tipo_gas, double cantidad_litros, double costo){
        this.id_gasto_gas = id_gasto_gas;
        this.id_vehiculo = id_vehiculo;
        this.fecha = fecha;
        this.tipo_gas = tipo_gas;
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
}
