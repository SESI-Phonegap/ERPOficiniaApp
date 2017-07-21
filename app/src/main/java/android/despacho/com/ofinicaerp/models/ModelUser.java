package android.despacho.com.ofinicaerp.models;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelUser {

    private int id_user;
    private String email;
    private String password;
    private String tipo;
    private int id_empleado;

    public ModelUser(int id_user, String email, String password) {
        this.id_user = id_user;
        this.email = email;
        this.password = password;
    }

    public ModelUser(String email, String password){
        this.email = email;
        this.password = password;
    }

    public ModelUser(String tipo, int id_empleado){
        this.tipo= tipo;
        this.id_empleado = id_empleado;
    }

    public int getId_user() {
        return id_user;
    }

    public String getPassword() {
        return password;
    }

    public String getTipo() {
        return tipo;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public String getEmail() {
        return email;
    }

    public String toJSONLogin() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("user", getEmail());
            jsonObject.put("pass", getPassword());

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
