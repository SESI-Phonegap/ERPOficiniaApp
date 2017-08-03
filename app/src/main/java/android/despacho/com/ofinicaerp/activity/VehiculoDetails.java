package android.despacho.com.ofinicaerp.activity;

import android.despacho.com.ofinicaerp.ActivityBase;
import android.despacho.com.ofinicaerp.fragments.VehiculoFragment;
import android.despacho.com.ofinicaerp.models.ModelVehiculo;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.despacho.com.ofinicaerp.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VehiculoDetails extends ActivityBase implements View.OnClickListener {

    private ImageView imgBack;
    private ImageView btnEdit;
    private ImageView btnPrevious;
    private ImageView btnNext;
    private ImageView imgVehiculo;
    private TextView tv_nombre;
    private TextView tv_serie;
    private TextView tv_placas;
    private TextView tv_marca;
    private TextView tv_modelo;
    private TextView tv_color;
    private TextView tv_empleado;
    private int position;
    private int max_index;
    private List<ModelVehiculo> listVehiculos;
    private ModelVehiculo vehiculo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehiculo_details);
        init();
    }

    public void init() {
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        btnEdit = (ImageView) findViewById(R.id.vehiculo_img_btn_edit);
        btnEdit.setOnClickListener(this);
        btnPrevious = (ImageView) findViewById(R.id.btn_vehiculo_previous_details);
        btnPrevious.setOnClickListener(this);
        btnNext = (ImageView) findViewById(R.id.btn_vehiculo_next_details);
        btnNext.setOnClickListener(this);

        imgVehiculo = (ImageView) findViewById(R.id.vehiculo_photo_details);
        tv_nombre = (TextView) findViewById(R.id.vehiculo_nombre_details);
        tv_serie = (TextView) findViewById(R.id.vehiculo_num_serie_details);
        tv_placas = (TextView) findViewById(R.id.vehiculo_placas_details);
        tv_marca = (TextView) findViewById(R.id.vehiculo_marca_details);
        tv_modelo = (TextView) findViewById(R.id.vehiculo_ano_details);
        tv_color = (TextView) findViewById(R.id.vehiculo_color_details);
        tv_empleado = (TextView) findViewById(R.id.vehiculo_empleado_details);

        position = Integer.parseInt(getIntent().getStringExtra("INDEX"));
        max_index = Integer.parseInt(getIntent().getStringExtra("MAX_INDEX"));

        vehiculo = VehiculoFragment.listVehiculos.get(position);

        loadInfo();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imgBack:
                onBackPressed();
                break;

            case R.id.vehiculo_img_btn_edit:
                break;

            case R.id.btn_vehiculo_previous_details:
                if (position > 0) {
                    position--;
                    loadInfoByIndex(position);
                }
                break;

            case R.id.btn_vehiculo_next_details:
                if (position < max_index - 1) {
                    position++;
                    loadInfoByIndex(position);
                }
                break;
        }
    }

    public void loadInfo() {
        if (vehiculo.getPhotoBase64().equals("")) {
            imgVehiculo.setImageResource(R.drawable.camaro);
        } else {
            String UrlImage = Constants.URL_BASE + vehiculo.getPhotoBase64();
            Picasso.with(this).load(UrlImage).fit().into(imgVehiculo);
        }

        tv_nombre.setText(vehiculo.getNombre());
        tv_marca.setText(vehiculo.getMarca());
        tv_modelo.setText(vehiculo.getModelo());
        tv_placas.setText(vehiculo.getPlacas());
        tv_serie.setText(vehiculo.getSerie());
        tv_color.setText(vehiculo.getColor());
        tv_empleado.setText(vehiculo.getNombre_empleado());
    }

    public void loadInfoByIndex(int index) {
        vehiculo = VehiculoFragment.listVehiculos.get(index);

        if (vehiculo.getPhotoBase64().equals("")) {
            imgVehiculo.setImageResource(R.drawable.camaro);
        } else {
            String UrlImage = Constants.URL_BASE + vehiculo.getPhotoBase64();
            Picasso.with(this).load(UrlImage).fit().into(imgVehiculo);
        }

        tv_nombre.setText(vehiculo.getNombre());
        tv_marca.setText(vehiculo.getMarca());
        tv_modelo.setText(vehiculo.getModelo());
        tv_placas.setText(vehiculo.getPlacas());
        tv_serie.setText(vehiculo.getSerie());
        tv_color.setText(vehiculo.getColor());
        tv_empleado.setText(vehiculo.getNombre_empleado());
    }
}
