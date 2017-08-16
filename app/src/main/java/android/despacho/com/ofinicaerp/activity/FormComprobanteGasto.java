package android.despacho.com.ofinicaerp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.models.ModelComprobanteGastos;
import android.despacho.com.ofinicaerp.models.ModelGastos;
import android.despacho.com.ofinicaerp.models.ModelTipoGasto;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.despacho.com.ofinicaerp.R;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FormComprobanteGasto extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private Spinner spinner_tipo_comprobante;
    private Spinner spinner_categoria;
    private Spinner spinner_id_gasto;
    private RadioButton radioButton_si;
    private RadioButton radioButton_no;
    private EditText et_fecha;
    private EditText et_concepto;
    private EditText et_monto;
    private List<ModelTipoGasto> listTipoGasto;
    private ProgressDialog progressBar;
    private List<ModelGastos> listGastos;
    private String idCategoria;
    private String tipoComprobante;
    private String idGasto;
    private int iGastoOxxo;
    private RadioGroup radioGroup;
    private double montoGastoSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_comprobante_gasto);
        init();
    }

    public void init(){
        montoGastoSelect = 0;
        iGastoOxxo = 0;
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
        Button btn_guardar = (Button) findViewById(R.id.btn_guardar);
        spinner_tipo_comprobante = (Spinner) findViewById(R.id.spinner_tipo_comprobante);
        spinner_categoria = (Spinner) findViewById(R.id.spinner_categoria);
        spinner_id_gasto = (Spinner) findViewById(R.id.spinner_id_gasto);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton_si = (RadioButton) findViewById(R.id.radioButton_si);
        radioButton_no = (RadioButton) findViewById(R.id.radioButton_no);
        radioButton_no.setChecked(true);
        et_fecha = (EditText) findViewById(R.id.et_fecha_compr);
        et_concepto = (EditText) findViewById(R.id.et_concepto_compro);
        et_monto = (EditText) findViewById(R.id.et_monto_compro);
        et_monto.addTextChangedListener(textWatcherMonto);
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);



        et_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogDate(FormComprobanteGasto.this,et_fecha);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        String[] arrayTipoComprobante = {"Ninguna","Nota","Ticket","Factura", "Devolucion"} ;

        spinner_tipo_comprobante.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,arrayTipoComprobante));


        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final String[] idNomTipoGasto = new String[listTipoGasto.size()];
                for (int i = 0; i < listTipoGasto.size(); i++) {
                    idNomTipoGasto[i] = listTipoGasto.get(i).getId_tipoGasto() + "-" + listTipoGasto.get(i).getTipoGasto();

                }
                spinner_categoria.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,idNomTipoGasto));

                String[] idNomGasto = new String[listGastos.size()];

                for (int x = 0; x < listGastos.size(); x++){
                    idNomGasto[x] = listGastos.get(x).getId_gasto() + "-" + listGastos.get(x).getFecha() + "-" + listGastos.get(x).getRuta() + "-\n" + getString(R.string.monto,Utils.parseToString(listGastos.get(x).getMonto()));
                }
                spinner_id_gasto.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,idNomGasto));

                spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String[] parts = parent.getItemAtPosition(position).toString().split("-");
                        idCategoria = parts[1];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                spinner_id_gasto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String[] parts = parent.getItemAtPosition(position).toString().split("-");
                        idGasto = parts[0];
                        montoGastoSelect = Double.parseDouble(parts[5].replaceAll(Constants.PAYMENT_NUMBER_FORMAT_REGEX_POINT, Constants.STRING_EMPTY));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        };
        Handler myHandler = new Handler();
        myHandler.postDelayed(runnable,1500);

        spinner_tipo_comprobante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoComprobante = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = et_fecha.getText().toString();
                String concepto = et_concepto.getText().toString();
                String monto = et_monto.getText().toString().replaceAll(Constants.PAYMENT_NUMBER_FORMAT_REGEX_POINT, Constants.STRING_EMPTY);

                if (fecha.equals("") || concepto.equals("") || monto.equals("") || idGasto.equals("")
                        || idCategoria.equals("") || tipoComprobante.equals("") ){
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    double montoParse = Double.parseDouble(monto);
                    if (montoParse <= 0){
                        Snackbar.make(v,getString(R.string.msg_monto_cero), Snackbar.LENGTH_LONG).show();
                    } else {
                        double updateMontoGasto = montoGastoSelect - montoParse;
                        if (updateMontoGasto <= 0){
                            Snackbar.make(v,getString(R.string.msg_monto_mayor_a_gasto), Snackbar.LENGTH_LONG).show();
                        } else {
                            ModelComprobanteGastos comprobanteGastos = new ModelComprobanteGastos(fecha,concepto,montoParse,
                                    Integer.parseInt(idGasto),iGastoOxxo,idCategoria,tipoComprobante,updateMontoGasto);
                            String strJSON = comprobanteGastos.toJsonAddComprobante();
                            new AddComprobanteTask().execute(Constants.URL_ADD_COMPROBANTE,strJSON);

                        }

                    }
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){

            case R.id.radioButton_si:
                iGastoOxxo = 1;
                break;

            case R.id.radioButton_no:
                iGastoOxxo = 0;
                break;
        }
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

    TextWatcher textWatcherMonto = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            et_monto.removeTextChangedListener(this);
            String cleanString = s.toString().replaceAll(Constants.PAYMENT_NUMBER_FORMAT_REGEX, Constants.STRING_EMPTY);
            double parsed = Utils.convertToDouble(cleanString);
            String formated = Utils.parseToString((parsed / 100));

            et_monto.setText(formated);
            Selection.setSelection(et_monto.getEditableText(),et_monto.getEditableText().length());

            et_monto.addTextChangedListener(textWatcherMonto);
        }
    };

    private class AddComprobanteTask extends AsyncTask<String, Integer, String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jSon = params[1];
            return UtilsDML.addData(Constants.POST_COMPROBANTE,baseUrl,jSon);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            proccessResult(result);
            progressBar.cancel();
        }
    }

    public void proccessResult(String result) {
        if (result.contains("OK")) {
            Toast.makeText(FormComprobanteGasto.this, getString(R.string.msg_success), Toast.LENGTH_LONG).show();
            finish();
        } else {
            Toast.makeText(FormComprobanteGasto.this,getString(R.string.msg_error) + result, Toast.LENGTH_LONG).show();
        }
    }
}
