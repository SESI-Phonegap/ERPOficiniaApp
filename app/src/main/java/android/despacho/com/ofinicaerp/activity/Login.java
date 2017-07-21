package android.despacho.com.ofinicaerp.activity;

import android.content.Intent;
import android.despacho.com.ofinicaerp.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

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
                    Intent intent = new Intent(getApplication(),MenuPrincipal.class);
                }else {
                    Toast.makeText(getApplication(),getString(R.string.msg_campos_vacios),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
