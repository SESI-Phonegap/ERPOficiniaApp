package android.despacho.com.ofinicaerp.activity;

import android.despacho.com.ofinicaerp.fragments.GastoGasolinaFragment;
import android.despacho.com.ofinicaerp.models.ModelGastosGasolina;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.despacho.com.ofinicaerp.R;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class GasolinaDetails extends AppCompatActivity {
    private TextView tv_carro;
    private TextView tv_marca;
    private TextView tv_modelo;
    private TextView tv_fecha;
    private TextView tv_litros;
    private TextView tv_tipoGas;
    private TextView tv_monto;
    private ImageView btn_anterior;
    private ImageView btn_siguiente;
    private ImageView photoCarro;
    private ImageView imgBack;
    private int position;
    private int max_index;
    private List<ModelGastosGasolina> listGasolinaDetails;
    private ModelGastosGasolina selectedGasolinaDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasolina_details);
        init();
    }

    public void init(){
        imgBack = (ImageView) findViewById(R.id.imgBack);
        photoCarro = (ImageView) findViewById(R.id.photoDetails);
        tv_carro = (TextView) findViewById(R.id.details_label_carro);
        tv_marca = (TextView) findViewById(R.id.details_label_marca);
        tv_modelo = (TextView) findViewById(R.id.details_label_modelo);
        tv_fecha = (TextView) findViewById(R.id.details_label_fecha);
        tv_litros = (TextView) findViewById(R.id.details_label_litros);
        tv_tipoGas = (TextView) findViewById(R.id.details_label_tipoGas);
        tv_monto = (TextView) findViewById(R.id.details_label_monto);

        btn_anterior = (ImageView) findViewById(R.id.btn_anterior);
        btn_siguiente = (ImageView) findViewById(R.id.btn_siguiente);

        position = Integer.parseInt(getIntent().getStringExtra("INDEX"));
        max_index = Integer.parseInt(getIntent().getStringExtra("MAX_INDEX"));
        selectedGasolinaDetails = GastoGasolinaFragment.listGasolina.get(position);

        btn_anterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0){
                    position--;
                    loadInfoByIndex(position);
                }
            }
        });

        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < max_index-1){
                    position++;
                    loadInfoByIndex(position);
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        loadInfo();
    }

    public void loadInfo(){
        tv_carro.setText(selectedGasolinaDetails.getCarro());
        tv_marca.setText(selectedGasolinaDetails.getMarca());
        tv_modelo.setText(selectedGasolinaDetails.getModelo());
        tv_fecha.setText(selectedGasolinaDetails.getFecha());
        tv_litros.setText(getString(R.string.details_label_litros,String.valueOf(selectedGasolinaDetails.getCantidad_litros())));
        tv_tipoGas.setText(selectedGasolinaDetails.getTipo_gas());
        tv_monto.setText(getString(R.string.monto,String.valueOf(selectedGasolinaDetails.getCosto())));

            if (selectedGasolinaDetails.getPhoto_base64().equals("")) {
                photoCarro.setImageResource(R.drawable.camaro);
            } else {
                String UrlImage = Constants.URL_BASE + selectedGasolinaDetails.getPhoto_base64();
                Picasso.with(this).load(UrlImage).fit().into(photoCarro);
            }

    }

    public void loadInfoByIndex(int index){
        selectedGasolinaDetails = GastoGasolinaFragment.listGasolina.get(index);
        tv_carro.setText(selectedGasolinaDetails.getCarro());
        tv_marca.setText(selectedGasolinaDetails.getMarca());
        tv_modelo.setText(selectedGasolinaDetails.getModelo());
        tv_fecha.setText(selectedGasolinaDetails.getFecha());
        tv_litros.setText(getString(R.string.details_label_litros,String.valueOf(selectedGasolinaDetails.getCantidad_litros())));
        tv_tipoGas.setText(selectedGasolinaDetails.getTipo_gas());
        tv_monto.setText(getString(R.string.monto,String.valueOf(selectedGasolinaDetails.getCosto())));

        if (selectedGasolinaDetails.getPhoto_base64().equals("")) {
            photoCarro.setImageResource(R.drawable.camaro);
        } else {
            String UrlImage = Constants.URL_BASE + selectedGasolinaDetails.getPhoto_base64();
            Picasso.with(this).load(UrlImage).fit().into(photoCarro);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
