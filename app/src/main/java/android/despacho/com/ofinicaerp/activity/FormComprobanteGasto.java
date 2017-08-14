package android.despacho.com.ofinicaerp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.despacho.com.ofinicaerp.R;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;

public class FormComprobanteGasto extends AppCompatActivity {
    private Spinner spinner_tipo_comprobante;
    private Spinner spinner_categoria;
    private Spinner spinner_id_gasto;
    private RadioButton radioButton_si;
    private RadioButton getRadioButton_no;
    private EditText et_fecha;
    private EditText et_concepto;
    private EditText et_monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_comprobante_gasto);
        init();
    }

    public void init(){
        spinner_tipo_comprobante = (Spinner) findViewById(R.id.spinner_tipo_comprobante);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria);
        spinner_id_gasto = (Spinner) findViewById(R.id.spinner_id_gasto);
        radioButton_si = (RadioButton) findViewById(R.id.radioButton_si);
        getRadioButton_no = (RadioButton) findViewById(R.id.radioButton_no);
        et_fecha = (EditText) findViewById(R.id.et_fecha_compr);
        et_concepto = (EditText) findViewById(R.id.et_concepto_compro);
        et_monto = (EditText) findViewById(R.id.et_monto_compro);
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String[] arrayTipoComprobante = {"Ninguna","Nota","Ticket","Factura"} ;

        spinner_tipo_comprobante.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,arrayTipoComprobante));

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
