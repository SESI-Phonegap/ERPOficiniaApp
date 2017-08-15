package android.despacho.com.ofinicaerp.models;


public class ModelComprobanteGastos {
    private int id_comprobante;
    private String fecha;
    private String concepto;
    private double monto;
    private int id_gasto;
    private int status_comprobante;
    private int tipo_gasto;
    private String categoria;
    private String tipo_comprobante;

    public ModelComprobanteGastos(int id_comprobante, String fecha, String concepto,
                                  double monto, int id_gasto, int status_comprobante,
                                  int tipo_gasto, String categoria, String tipo_comprobante){
        this.id_comprobante = id_comprobante;
        this.fecha = fecha;
        this.concepto = concepto;
        this.monto = monto;
        this.id_gasto = id_gasto;
        this.status_comprobante = status_comprobante;
        this.tipo_gasto = tipo_gasto;
        this.categoria = categoria;
        this.tipo_comprobante = tipo_comprobante;

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

    public double getMonto() {
        return monto;
    }

    public int getId_gasto() {
        return id_gasto;
    }

    public int getStatus_comprobante() {
        return status_comprobante;
    }

    public int getTipo_gasto() {
        return tipo_gasto;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getTipo_comprobante() {
        return tipo_comprobante;
    }
}
