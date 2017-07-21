package android.despacho.com.ofinicaerp.models;

public class ModelPagoEmpleado {
    private int id_pago;
    private int id_empleado;
    private String status;
    private String mes;
    private String semana;
    private double cantidad;

    public ModelPagoEmpleado(int id_pago, int id_empleado, String status, String mes, String semana, double cantidad){
        this.id_pago = id_pago;
        this.id_empleado = id_empleado;
        this.status = status;
        this.mes = mes;
        this.semana = semana;
        this.cantidad = cantidad;
    }

    public int getId_pago() {
        return id_pago;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public String getStatus() {
        return status;
    }

    public String getMes() {
        return mes;
    }

    public String getSemana() {
        return semana;
    }

    public double getCantidad() {
        return cantidad;
    }
}
