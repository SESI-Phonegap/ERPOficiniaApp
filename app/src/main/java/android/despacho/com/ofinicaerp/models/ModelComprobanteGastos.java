package android.despacho.com.ofinicaerp.models;


import org.json.JSONException;
import org.json.JSONObject;

import static android.despacho.com.ofinicaerp.activity.FormComprobanteGasto.updateCaja;

public class ModelComprobanteGastos {
    private int id_comprobante;
    private String fecha;
    private String concepto;
    private double monto;
    private int id_gasto;
    private int tipo_gasto_oxxo;
    private String categoria;
    private String tipo_comprobante;

    private double montoGasto;

    public ModelComprobanteGastos(int id_comprobante, String fecha, String concepto,
                                  double monto, int id_gasto, int tipo_gasto_oxxo,
                                  String categoria, String tipo_comprobante){
        this.id_comprobante = id_comprobante;
        this.fecha = fecha;
        this.concepto = concepto;
        this.monto = monto;
        this.id_gasto = id_gasto;
        this.tipo_gasto_oxxo = tipo_gasto_oxxo;
        this.categoria = categoria;
        this.tipo_comprobante = tipo_comprobante;

    }

    public ModelComprobanteGastos(String fecha, String concepto,
                                  double monto, int id_gasto, int tipo_gasto_oxxo,
                                  String categoria, String tipo_comprobante, double montoGasto){
        this.fecha = fecha;
        this.concepto = concepto;
        this.monto = monto;
        this.id_gasto = id_gasto;
        this.tipo_gasto_oxxo = tipo_gasto_oxxo;
        this.categoria = categoria;
        this.tipo_comprobante = tipo_comprobante;
        this.montoGasto = montoGasto;
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

    public int getTipo_gasto_oxxo() {
        return tipo_gasto_oxxo;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getTipo_comprobante() {
        return tipo_comprobante;
    }

    public double getMontoGasto() {
        return montoGasto;
    }

    public String toJsonAddComprobante() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("fecha", getFecha());
            jsonObject.put("concepto", getConcepto());
            jsonObject.put("monto", getMonto());
            jsonObject.put("idGasto", getId_gasto());
            jsonObject.put("oxxo",getTipo_gasto_oxxo());
            jsonObject.put("categoria",getCategoria());
            jsonObject.put("tipoComprobante",getTipo_comprobante());
            jsonObject.put("montoGasto",getMontoGasto());
            jsonObject.put("updateCaja",updateCaja);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
