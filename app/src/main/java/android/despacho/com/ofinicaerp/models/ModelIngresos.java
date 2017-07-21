package android.despacho.com.ofinicaerp.models;

public class ModelIngresos {

    private int id_ingresos;
    private int id_ruta;
    private String fecha;
    private String concepto;
    private double cantidad;

    public ModelIngresos(int id_ingresos, int id_ruta, String fecha, String concepto, double cantidad){
        this.id_ingresos = id_ingresos;
        this.id_ruta = id_ruta;
        this.fecha = fecha;
        this.concepto = concepto;
        this.cantidad = cantidad;
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
}
