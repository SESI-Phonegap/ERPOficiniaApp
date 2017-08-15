package android.despacho.com.ofinicaerp.activity;

import android.app.ProgressDialog;
import android.despacho.com.ofinicaerp.models.ModelGastos;
import android.despacho.com.ofinicaerp.models.ModelTipoGasto;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.despacho.com.ofinicaerp.R;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class FormComprobanteGasto extends AppCompatActivity {
    private Spinner spinner_tipo_comprobante;
    private Spinner spinner_categoria;
    private Spinner spinner_id_gasto;
    private RadioButton radioButton_si;
    private RadioButton getRadioButton_no;
    private EditText et_fecha;
    private EditText et_concepto;
    private EditText et_monto;
    private List<ModelTipoGasto> listTipoGasto;
    private ProgressDialog progressBar;
    private List<ModelGastos> listGastos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_comprobante_gasto);
        init();
    }

    public void init(){
        listGastos = new ArrayList<>();
        progressBar = new ProgressDialog(FormComprobanteGasto.this);
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        listTipoGasto = new ArrayList<>();
        new QueryTipoGastoTask().execute(Constants.URL_QUERY_TIPO_GASTO);
        new QueryGastosTask().execute(Constants.URL_QUERY_GASTO_NO_COMPROBADOS);
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


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final String[] idNomTipoGasto = new String[listTipoGasto.size()];
                for (int i = 0; i < listTipoGasto.size(); i++) {
                    idNomTipoGasto[i] = listTipoGasto.get(i).getId_tipoGasto() + "-" + listTipoGasto.get(i).getTipoGasto();
                    Log.d("TIPO---",idNomTipoGasto[i]);
                }
                spinner_categoria.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,idNomTipoGasto));

                String[] idNomGasto = new String[listGastos.size()];

                for (int x = 0; x < listGastos.size(); x++){
                    idNomGasto[x] = listGastos.get(x).getId_gasto() + "-" + listGastos.get(x).getMonto();
                    Log.d("GASTO---",idNomGasto[x]);
                }
                spinner_id_gasto.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,idNomGasto));
            }
        };
        Handler myHandler = new Handler();
        myHandler.postDelayed(runnable,800);




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class QueryTipoGastoTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return UtilsDML.queryAllData(params[0]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            UtilsDML.resultQueryTipoGasto(result, listTipoGasto);
            // setUpRecyclerView();
            progressBar.cancel();
        }

    }

    private class QueryGastosTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return UtilsDML.queryAllData(params[0]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            UtilsDML.resultQueryGastosNoComprobados(getApplication(),result,listGastos);
            progressBar.cancel();
        }
    }
}
