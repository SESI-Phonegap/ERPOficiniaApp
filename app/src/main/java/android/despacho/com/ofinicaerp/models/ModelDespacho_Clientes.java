package android.despacho.com.ofinicaerp.models;

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
}
