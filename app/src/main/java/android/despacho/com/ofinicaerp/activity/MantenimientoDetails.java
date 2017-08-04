package android.despacho.com.ofinicaerp.activity;

import android.despacho.com.ofinicaerp.fragments.MantenimientoVehiculoFragment;
import android.despacho.com.ofinicaerp.models.ModelMantenimiento;
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

public class MantenimientoDetails extends AppCompatActivity implements View.OnClickListener{

    private ImageView imgBack;
    private ImageView img_btn_edit;
    private ImageView img_btn_previous;
    private ImageView img_btn_next;
    private ImageView imgVehiculo;
    private TextView tv_vehiculo;
    private TextView tv_fecha;
    private TextView tv_mante_descrip;
    private TextView tv_monto;
    private int position;
    private int max_index;
    private List<ModelMantenimiento> listMantenimiento;
    private ModelMantenimiento mantenimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento_details);
        init();
    }

    public void init(){
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        img_btn_edit = (ImageView) findViewById(R.id.vehiculo_img_btn_edit);
        img_btn_edit.setOnClickListener(this);
        img_btn_previous = (ImageView) findViewById(R.id.btn_vehiculo_previous_details);
        img_btn_previous.setOnClickListener(this);
        img_btn_next = (ImageView) findViewById(R.id.btn_vehiculo_next_details);
        img_btn_next.setOnClickListener(this);
        imgVehiculo = (ImageView) findViewById(R.id.vehiculo_photo_details);
        tv_vehiculo = (TextView) findViewById(R.id.vehiculo_nombre_mantenimiento);
        tv_fecha = (TextView) findViewById(R.id.fecha_mantenimiento);
        tv_mante_descrip = (TextView) findViewById(R.id.mantenimiento_descrip);
        tv_monto = (TextView) findViewById(R.id.matenimiento_monto);

        position = Integer.parseInt(getIntent().getStringExtra("INDEX"));
        max_index = Integer.parseInt(getIntent().getStringExtra("MAX_INDEX"));

        mantenimiento = MantenimientoVehiculoFragment.listMantenimiento.get(position);

        loadInfo();
    }

    public void loadInfo(){
        tv_mante_descrip.setText("");
        if (mantenimiento.getImgVehiculo().equals("")) {
            imgVehiculo.setImageResource(R.drawable.camaro);
        } else {
            String UrlImage = Constants.URL_BASE + mantenimiento.getImgVehiculo();
            Picasso.with(this).load(UrlImage).fit().into(imgVehiculo);
        }

        tv_vehiculo.setText(mantenimiento.getVehiculo());
        tv_fecha.setText(mantenimiento.getFecha());
        tv_mante_descrip.append(mantenimiento.getMantenimiento());
        tv_mante_descrip.append("\n");
        tv_mante_descrip.append(mantenimiento.getDescripcion());
        tv_monto.setText(String.valueOf(mantenimiento.getCosto()));

    }

    public void loadInfoByIndex(int index){
        tv_mante_descrip.setText("");
        mantenimiento = MantenimientoVehiculoFragment.listMantenimiento.get(index);

        if (mantenimiento.getImgVehiculo().equals("")) {
            imgVehiculo.setImageResource(R.drawable.camaro);
        } else {
            String UrlImage = Constants.URL_BASE + mantenimiento.getImgVehiculo();
            Picasso.with(this).load(UrlImage).fit().into(imgVehiculo);
        }

        tv_vehiculo.setText(mantenimiento.getVehiculo());
        tv_fecha.setText(mantenimiento.getFecha());
        tv_mante_descrip.append(mantenimiento.getMantenimiento());
        tv_mante_descrip.append("\n");
        tv_mante_descrip.append(mantenimiento.getDescripcion());
        tv_monto.setText(String.valueOf(mantenimiento.getCosto()));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
