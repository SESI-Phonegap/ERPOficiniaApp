package android.despacho.com.ofinicaerp.models;


public class ModelGastos {
    private int id_gasto;
    private int id_ruta;
    private double monto;
    private String fecha;
    private int id_empleado;

    public ModelGastos(int id_gasto, int id_ruta, double monto, String fecha, int id_empleado){
        this.id_gasto = id_gasto;
        this.id_ruta = id_ruta;
        this.monto = monto;
        this.fecha = fecha;
        this.id_empleado = id_empleado;
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
}
