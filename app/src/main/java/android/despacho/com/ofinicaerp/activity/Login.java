package android.despacho.com.ofinicaerp.activity;

import android.content.Intent;
import android.despacho.com.ofinicaerp.R;
import android.despacho.com.ofinicaerp.models.ModelUser;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_ID_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_TIPO_USER;
import static android.despacho.com.ofinicaerp.utils.Constants.URL;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_LOGIN;
import static android.despacho.com.ofinicaerp.utils.UtilsDML.APP_TAG;

public class Login extends AppCompatActivity {

    ModelUser modelUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    public void init(){
        final EditText et_email = (EditText) findViewById(R.id.et_login_email);
        final EditText et_pass = (EditText) findViewById(R.id.et_login_pass);
        Button btn_entrar = (Button) findViewById(R.id.btn_login_entrar);

        btn_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_email.getText().toString().equals("") && !et_pass.getText().toString().equals("")){
                    ModelUser modelUser = new ModelUser(et_email.getText().toString(),et_pass.getText().toString());
                    String strJson = modelUser.toJSONLogin();
                    new LoginTask().execute(URL+URL_LOGIN,strJson);
                //    Intent intent = new Intent(getApplication(),MenuPrincipal.class);
                //    startActivity(intent);
                }else {
                    Toast.makeText(getApplication(),getString(R.string.msg_campos_vacios),Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    private class LoginTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
          return  UtilsDML.login(params[0],params[1]);
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.w(APP_TAG,"Indicador de pregreso " + values[0].toString());
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //Se obtiene el resultado de la peticion Asincrona
            Log.w(APP_TAG,"Resultado obtenido " + result);

            String msg = UtilsDML.loginResultJson(getApplication(),result,modelUser);
           if (msg.equals("")){
               if (modelUser != null){
                   Intent intent = new Intent(getApplication(),MenuPrincipal.class);
                   intent.putExtra(PUTEXTRA_ID_EMPLEADO,modelUser.getId_empleado());
                   intent.putExtra(PUTEXTRA_TIPO_USER,modelUser.getTipo());
                   startActivity(intent);
               }
           } else {
               Toast.makeText(getApplication(),msg,Toast.LENGTH_LONG).show();
           }


        }
    }
}
