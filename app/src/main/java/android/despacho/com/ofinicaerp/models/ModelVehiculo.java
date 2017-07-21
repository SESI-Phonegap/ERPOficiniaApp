package android.despacho.com.ofinicaerp.models;

public class ModelVehiculo {
    private int id_vehiculo;
    private String nombre;
    private String modelo;
    private String marca;
    private String serie;
    private int id_empleado;

    public ModelVehiculo(int id_vehiculo, String nombre, String modelo, String marca,
                         String serie, int id_empleado){
        this.id_vehiculo = id_vehiculo;
        this.nombre = nombre;
        this.modelo = modelo;
        this.marca = marca;
        this.serie = serie;
        this.id_empleado = id_empleado;
    }

    public int getId_vehiculo() {
        return id_vehiculo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getSerie() {
        return serie;
    }

    public int getId_empleado() {
        return id_empleado;
    }
}
