package android.despacho.com.ofinicaerp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelDespacho_Clientes {
    private int id_cliente;
    private String nombre;
    private String rfc;
    private String curp;
    private String pass_sat;
    private String pass_fiel;
    private String pass_certificado;
    private double monto_mensualidad;

    public ModelDespacho_Clientes(int id_cliente,String nombre, String rfc,
                                  String curp, String pass_sat, String pass_fiel,
                                  String pass_certificado, double monto_mensualidad){
        this.id_cliente = id_cliente;
        this.nombre = nombre;
        this.rfc = rfc;
        this.curp = curp;
        this.pass_sat = pass_sat;
        this.pass_fiel = pass_fiel;
        this.pass_certificado = pass_certificado;
        this.monto_mensualidad = monto_mensualidad;
    }

    public ModelDespacho_Clientes(String nombre, String rfc,
                                  String curp, String pass_sat, String pass_fiel,
                                  String pass_certificado, double monto_mensualidad){

        this.nombre = nombre;
        this.rfc = rfc;
        this.curp = curp;
        this.pass_sat = pass_sat;
        this.pass_fiel = pass_fiel;
        this.pass_certificado = pass_certificado;
        this.monto_mensualidad = monto_mensualidad;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRfc() {
        return rfc;
    }

    public String getCurp() {
        return curp;
    }

    public String getPass_sat() {
        return pass_sat;
    }

    public String getPass_fiel() {
        return pass_fiel;
    }

    public String getPass_certificado() {
        return pass_certificado;
    }

    public double getMonto_mensualidad() {
        return monto_mensualidad;
    }

    public String toJsonAddCliente() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("nombre", getNombre());
            jsonObject.put("rfc", getRfc());
            jsonObject.put("curp", getCurp());
            jsonObject.put("honorario", getMonto_mensualidad());
            jsonObject.put("passSat", getPass_sat());
            jsonObject.put("passFiel", getPass_fiel());
            jsonObject.put("passCert", getPass_certificado());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String toJsonUpdateCliente() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("idCliente", getId_cliente());
            jsonObject.put("nombre", getNombre());
            jsonObject.put("rfc", getRfc());
            jsonObject.put("curp", getCurp());
            jsonObject.put("honorario", getMonto_mensualidad());
            jsonObject.put("passSat", getPass_sat());
            jsonObject.put("passFiel", getPass_fiel());
            jsonObject.put("passCert", getPass_certificado());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
