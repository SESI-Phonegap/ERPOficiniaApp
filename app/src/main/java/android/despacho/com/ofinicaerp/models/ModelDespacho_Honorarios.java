package android.despacho.com.ofinicaerp.models;


public class ModelDespacho_Honorarios {
    private int id_honorario;
    private int id_mes;
    private int ano;
    private int status;
    private int id_cliente;

    public ModelDespacho_Honorarios(int id_honorario, int id_mes, int ano,
                                    int status, int id_cliente){
        this.id_honorario = id_honorario;
        this.id_mes = id_mes;
        this.ano = ano;
        this.status = status;
        this.id_cliente = id_cliente;
    }

    public int getId_honorario() {
        return id_honorario;
    }

    public int getId_mes() {
        return id_mes;
    }

    public int getAno() {
        return ano;
    }

    public int getStatus() {
        return status;
    }

    public int getId_cliente() {
        return id_cliente;
    }
}
