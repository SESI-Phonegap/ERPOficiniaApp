package android.despacho.com.ofinicaerp.models;


public class ModelComprobanteGastos {
    private int id_comprobante;
    private String fecha;
    private String concepto;
    private String unidad;
    private double cantidad;
    private double monto;
    private int id_gasto;
    private int status_comprobante;
    private String tipo_gasto;

    public ModelComprobanteGastos(int id_comprobante, String fecha, String concepto,
                                  String unidad, double cantidad, double monto, int id_gasto,
                                  int status_comprobante, String tipo_gasto){
        this.id_comprobante = id_comprobante;
        this.fecha = fecha;
        this.concepto = concepto;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.monto = monto;
        this.id_gasto = id_gasto;
        this.status_comprobante = status_comprobante;
        this.tipo_gasto = tipo_gasto;

    }

    public int getId_comprobante() {
        return id_comprobante;
    }

    public String getFecha() {
        return fecha;
    }

    public String getConcepto() {
        return concepto;
    }

    public String getUnidad() {
        return unidad;
    }

    public double getCantidad() {
        return cantidad;
    }

    public double getMonto() {
        return monto;
    }

    public int getId_gasto() {
        return id_gasto;
    }

    public int getStatus_comprobante() {
        return status_comprobante;
    }

    public String getTipo_gasto() {
        return tipo_gasto;
    }
}
