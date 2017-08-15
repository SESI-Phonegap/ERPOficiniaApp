package android.despacho.com.ofinicaerp.models;

/**
 * Created by sesimx on 14/08/17.
 */

public class ModelTipoGasto {
    private int id_tipoGasto;
    private String tipoGasto;

    public ModelTipoGasto(int id_tipoGasto, String tipoGasto){
        this.id_tipoGasto = id_tipoGasto;
        this.tipoGasto = tipoGasto;
    }

    public int getId_tipoGasto() {
        return id_tipoGasto;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }
}
