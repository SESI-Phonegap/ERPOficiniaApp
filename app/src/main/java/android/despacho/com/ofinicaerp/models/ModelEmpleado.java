package android.despacho.com.ofinicaerp.models;

public class ModelEmpleado {
    private int id_empleado;
    private String nombre;
    private String puesto;
    private double sueldo;
    private String empresa;

    public ModelEmpleado(int id_empleado, String nombre, String puesto, double sueldo, String empresa){
        this.id_empleado = id_empleado;
        this.nombre = nombre;
        this.puesto = puesto;
        this.sueldo = sueldo;
        this.empresa = empresa;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPuesto() {
        return puesto;
    }

    public double getSueldo() {
        return sueldo;
    }

    public String getEmpresa() {
        return empresa;
    }
}
