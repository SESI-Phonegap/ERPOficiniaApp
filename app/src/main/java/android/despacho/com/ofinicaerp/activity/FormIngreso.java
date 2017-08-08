package android.despacho.com.ofinicaerp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.despacho.com.ofinicaerp.models.ModelIngresos;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.despacho.com.ofinicaerp.R;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import static android.despacho.com.ofinicaerp.activity.MenuPrincipal.listRutas;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_INGRESO;

public class FormIngreso extends AppCompatActivity {
    private EditText et_fecha;
    private EditText et_concepto;
    private EditText et_monto;
    private String idRuta;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_ingreso);
        init();
    }

    public void init(){
        progressBar = new ProgressDialog(FormIngreso.this);
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        idRuta = "";
        ImageView imgBack = (ImageView) findViewById(R.id.imgBack);
        Spinner spinner_ruta = (Spinner) findViewById(R.id.spinner_ingreso_ruta);
        et_fecha = (EditText) findViewById(R.id.et_fecha);
        et_concepto = (EditText) findViewById(R.id.et_ingreso_concepto);
        et_monto = (EditText) findViewById(R.id.et_ingreso_monto);
        Button btn_guardar = (Button) findViewById(R.id.btn_guardar);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        et_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogDate(FormIngreso.this,et_fecha);
            }
        });

        final String[] idNomVehiculo = new String[listRutas.size()];
        for (int i = 0; i < listRutas.size(); i++) {
            idNomVehiculo[i] = listRutas.get(i).getId_ruta() + "-" + listRutas.get(i).getRuta();
        }
        spinner_ruta.setAdapter(new ArrayAdapter<>(FormIngreso.this, R.layout.row_spinner_item, idNomVehiculo));
        spinner_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] parts = parent.getItemAtPosition(position).toString().split("-");

                idRuta = parts[0];
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
                String monto = et_monto.getText().toString();

                if (fecha.equals("") || concepto.equals("") || monto.equals("") || idRuta.equals("")){
                    Toast.makeText(FormIngreso.this,getString(R.string.msg_campos_vacios),Toast.LENGTH_LONG).show();
                } else {
                    ModelIngresos modelIngresos = new ModelIngresos(Integer.parseInt(idRuta),fecha,concepto,Double.parseDouble(monto));
                    String strJSON = modelIngresos.toJsonAddIngreso();
                    new AddIngresoTask().execute(URL_ADD_INGRESO,strJSON);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class AddIngresoTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_INGRESO, baseUrl, jsonData);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Utils.proccessResult(FormIngreso.this,result);
            progressBar.cancel();
            Intent intent = new Intent();
            intent.putExtra(Constants.REFRESH, Constants.REFRESH_FRAGMENT_INGRESO);
            setResult(RESULT_OK,intent);
        }

    }
}
