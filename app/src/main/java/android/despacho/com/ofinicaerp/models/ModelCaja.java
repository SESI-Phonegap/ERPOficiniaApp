package android.despacho.com.ofinicaerp.models;


import org.json.JSONException;
import org.json.JSONObject;

public class ModelCaja {
    private int id_caja;
    private double monto;

    ModelCaja(int id_caja, double monto){
        this.id_caja = id_caja;
        this.monto = monto;
    }

   public ModelCaja(double monto){
        this.monto = monto;
    }

    public int getId_caja() {
        return id_caja;
    }

    public double getMonto() {
        return monto;
    }

    public String toJsonUpdateCaja() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("monto", getMonto());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
