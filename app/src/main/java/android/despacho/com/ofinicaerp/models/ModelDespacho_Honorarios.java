package android.despacho.com.ofinicaerp.models;


import org.json.JSONException;
import org.json.JSONObject;

public class ModelDespacho_Honorarios {
    private int id_honorario;
    private int id_mes;
    private String ano;
    private int status;
    private int id_cliente;
    private double monto;

    private String clienteNombre;

    public ModelDespacho_Honorarios(int id_honorario, int id_mes, String ano,
                                    int status, int id_cliente, String clienteNombre, double monto){
        this.id_honorario = id_honorario;
        this.id_mes = id_mes;
        this.ano = ano;
        this.status = status;
        this.id_cliente = id_cliente;
        this.clienteNombre = clienteNombre;
        this.monto = monto;
    }

    public ModelDespacho_Honorarios(int id_mes, String ano,
                                    int status, int id_cliente, double monto){
        this.id_mes = id_mes;
        this.ano = ano;
        this.status = status;
        this.id_cliente = id_cliente;
        this.monto = monto;
    }

    public ModelDespacho_Honorarios(int id_honorario, int id_mes, String ano,
                                    int status, String clienteNombre, double monto){
        this.id_honorario = id_honorario;
        this.id_mes = id_mes;
        this.ano = ano;
        this.status = status;
        this.clienteNombre = clienteNombre;
        this.monto = monto;
    }

    public ModelDespacho_Honorarios(int id_mes, String ano,
                                    int status){
        this.id_mes = id_mes;
        this.ano = ano;
        this.status = status;
    }


    public int getId_honorario() {
        return id_honorario;
    }

    public int getId_mes() {
        return id_mes;
    }

    public String getAno() {
        return ano;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public int getStatus() {
        return status;
    }

    public double getMonto() {
        return monto;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public String toJSONQueryHono(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("mes", getId_mes());
            jsonObject.put("ano", getAno());
            jsonObject.put("status", getStatus());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public String toJSONAddHono(){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("mes", getId_mes());
            jsonObject.put("ano", getAno());
            jsonObject.put("status", getStatus());
            jsonObject.put("idCliente", getId_cliente());
            jsonObject.put("monto", getMonto());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
