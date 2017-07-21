package android.despacho.com.ofinicaerp.models;

public class ModelTipoMantenimiento {
    private int id_tipo_mantenimiento;
    private String nombre;
    private String descripcion;

    public ModelTipoMantenimiento(int id_tipo_mantenimiento, String nombre, String descripcion){
        this.id_tipo_mantenimiento = id_tipo_mantenimiento;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getId_tipo_mantenimiento() {
        return id_tipo_mantenimiento;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
