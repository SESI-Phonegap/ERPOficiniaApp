package android.despacho.com.ofinicaerp.models;

public class ModelMantenimiento {
    private int id_mantenimiento;
    private int id_tipo_mantenimiento;
    private double costo;
    private String fecha;
    private int id_vehiculo;

    public ModelMantenimiento(int id_mantenimiento, int id_tipo_mantenimiento, double costo,
                              String fecha, int id_vehiculo){
        this.id_mantenimiento = id_mantenimiento;
        this.id_tipo_mantenimiento = id_tipo_mantenimiento;
        this.costo = costo;
        this.fecha = fecha;
        this.id_vehiculo = id_vehiculo;
    }

    public int getId_mantenimiento() {
        return id_mantenimiento;
    }

    public int getId_tipo_mantenimiento() {
        return id_tipo_mantenimiento;
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
}
