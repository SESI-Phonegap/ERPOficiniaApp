package android.despacho.com.ofinicaerp.models;

public class ModelUser {

    private int id_user;
    private String password;
    private String tipo;
    private int id_empleado;

   public ModelUser(int id_user, String password){
        this.id_user = id_user;
        this.password = password;
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
}
