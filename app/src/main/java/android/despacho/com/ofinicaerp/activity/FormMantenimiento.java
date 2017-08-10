package android.despacho.com.ofinicaerp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.despacho.com.ofinicaerp.models.ModelMantenimiento;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
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

import static android.despacho.com.ofinicaerp.activity.MenuPrincipal.caja;
import static android.despacho.com.ofinicaerp.activity.MenuPrincipal.listVehiculos;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_MANTENIMIENTO;

public class FormMantenimiento extends AppCompatActivity {

    private Spinner spinner_vehiculo;
    private EditText et_fecha;
    private EditText et_mantenimiento;
    private EditText et_descripcion;
    private EditText et_moto;
    private Button btn_guardar;
    private ImageView imgBack;
    private String idVehiculo;
    private ProgressDialog progressBar;
    public static double montoActual_Gasto_Mantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_mantenimiento);
        init();
    }

    public void init(){
        montoActual_Gasto_Mantenimiento = 0;
        progressBar = new ProgressDialog(FormMantenimiento.this);
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        idVehiculo = "";
        spinner_vehiculo = (Spinner) findViewById(R.id.spinner_mantenimiento);
        et_fecha = (EditText) findViewById(R.id.et_mantenimiento_fecha);
        et_mantenimiento = (EditText) findViewById(R.id.et_mantenimiento);
        et_descripcion = (EditText) findViewById(R.id.et_mantenimiento_descripcion);
        et_moto = (EditText) findViewById(R.id.et_mantenimiento_monto);
        btn_guardar = (Button) findViewById(R.id.btn_guardar);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        et_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogDate(FormMantenimiento.this,et_fecha);
            }
        });

        final String[] idNomVehiculo = new String[listVehiculos.size()];
        for (int i = 0; i < listVehiculos.size(); i++) {
            idNomVehiculo[i] = listVehiculos.get(i).getId_vehiculo() + "-" + listVehiculos.get(i).getNombre();
        }
        spinner_vehiculo.setAdapter(new ArrayAdapter<>(FormMantenimiento.this, R.layout.row_spinner_item, idNomVehiculo));
        spinner_vehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] parts = parent.getItemAtPosition(position).toString().split("-");

                idVehiculo = parts[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mantenimiento = et_mantenimiento.getText().toString();
                String descripcion = et_descripcion.getText().toString();
                String fecha = et_fecha.getText().toString();
                String monto = et_moto.getText().toString();
                montoActual_Gasto_Mantenimiento = caja.get(0).getMonto() - Double.parseDouble(monto);

                if (idVehiculo.equals("") || mantenimiento.equals("") || descripcion.equals("")
                        || fecha.equals("") || monto.equals("")){
                    Toast.makeText(FormMantenimiento.this,getString(R.string.msg_campos_vacios),Toast.LENGTH_LONG).show();
                } else {
                    if (montoActual_Gasto_Mantenimiento < 0){
                        Snackbar.make(v, getResources().getString(R.string.msg_monto_mayor_a_caja), Snackbar.LENGTH_LONG).show();
                    } else {
                        ModelMantenimiento modelMantenimiento = new ModelMantenimiento(
                                mantenimiento,
                                descripcion,
                                Double.parseDouble(monto),
                                fecha,
                                Integer.parseInt(idVehiculo));

                        String strJSON = modelMantenimiento.toJsonAddMantenimiento();
                        new AddMantenimientoTask().execute(URL_ADD_MANTENIMIENTO, strJSON);
                    }
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class AddMantenimientoTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_MANTENIMIENTO, baseUrl, jsonData);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Utils.proccessResult(FormMantenimiento.this,result);
            progressBar.cancel();
            Intent intent = new Intent();
            intent.putExtra(Constants.REFRESH, Constants.REFRESH_FRAGMENT_MANTENIMIENTO);
            setResult(RESULT_OK,intent);
        }

    }


}
