package android.despacho.com.ofinicaerp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.despacho.com.ofinicaerp.ActivityBase;
import android.despacho.com.ofinicaerp.fragments.VehiculoFragment;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.despacho.com.ofinicaerp.models.ModelVehiculo;
import android.despacho.com.ofinicaerp.utils.CameraPhoto;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.GalleryPhoto;
import android.despacho.com.ofinicaerp.utils.ImageLoader;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.despacho.com.ofinicaerp.R;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PICK_IMAGE_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PICK_IMAGE_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.TAKE_PICTURE_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.TAKE_PICTURE_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.VEHICULO;

public class FormVehiculo extends ActivityBase implements View.OnClickListener {

    private ImageView imgBack;
    private CircleImageView imgVehiculo;
    private EditText et_nombre;
    private EditText et_marca;
    private EditText et_modelo;
    private EditText et_placas;
    private EditText et_serie;
    private EditText et_color;
    private Spinner spinnerEmpleado;
    private Button btn_guardar;
    private String[] idNomEmpleado;
    private String id_empleado;
    private String photoPathSelected;
    private String imageBase64;
    private CameraPhoto cameraPhoto;
    private GalleryPhoto galleryPhoto;

    private ProgressDialog progressBar;
    private List<ModelEmpleado> listEmpleados;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_vehiculo);
        init();
    }

    public void init() {
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        progressBar = new ProgressDialog(FormVehiculo.this);
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgVehiculo = (CircleImageView) findViewById(R.id.vehiculo_photo);
        et_nombre = (EditText) findViewById(R.id.vehiculo_nombre);
        et_marca = (EditText) findViewById(R.id.vehiculo_marca);
        et_modelo = (EditText) findViewById(R.id.vehiculo_ano);
        et_placas = (EditText) findViewById(R.id.vehiculo_placas);
        et_serie = (EditText) findViewById(R.id.vehiculo_serie);
        et_color = (EditText) findViewById(R.id.vehiculo_color);
        spinnerEmpleado = (Spinner) findViewById(R.id.vehiculo_spinner);
        btn_guardar = (Button) findViewById(R.id.btn_vehiculo_guardar);
        listEmpleados = new ArrayList<>();
        id_empleado = "";

        imgBack.setOnClickListener(this);

        spinnerEmpleado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_empleado = parent.getItemAtPosition(position).toString().substring(0, 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        imgVehiculo.setOnClickListener(this);

        btn_guardar.setOnClickListener(this);

        new QueryEmpleadoTask().execute(URL_QUERY_EMPLEADO);


    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.imgBack:
                onBackPressed();
                break;

            case R.id.vehiculo_photo:
                dialogSelectPhoto();
                break;

            case R.id.btn_vehiculo_guardar:
                String nombre = et_nombre.getText().toString();
                String modelo = et_modelo.getText().toString();
                String marca = et_marca.getText().toString();
                String serie = et_serie.getText().toString();
                String placas = et_placas.getText().toString();
                String color = et_color.getText().toString();
                // String empleado = spinnerEmpleado.
                if (nombre.equals("") || modelo.equals("") || marca.equals("") || serie.equals("") ||
                        placas.equals("") || color.equals("") || id_empleado.equals("") || photoPathSelected.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    ModelVehiculo modelVehiculo = new ModelVehiculo(nombre, modelo, marca, serie, Integer.parseInt(id_empleado), imageBase64, placas, color);
                    String strJson = modelVehiculo.toJsonAddVehiculo();
                    new AddVehiculoTask().execute(URL_ADD_VEHICULO, strJson);
                }
                break;
        }
    }

    public void tomaFoto() {
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(), TAKE_PICTURE_VEHICULO);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(FormVehiculo.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(FormVehiculo.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);

            } else {
                startActivityForResult(galleryPhoto.openGalleryIntent(), PICK_IMAGE_VEHICULO);
            }
        } else {
            startActivityForResult(galleryPhoto.openGalleryIntent(), PICK_IMAGE_VEHICULO);
        }

    }

    public void dialogSelectPhoto() {
        final AlertDialog dialogPhoto;
        final AlertDialog.Builder builder = new AlertDialog.Builder(FormVehiculo.this);
        LayoutInflater inflater = FormVehiculo.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_select_photo, null);
        builder.setView(view);
        dialogPhoto = builder.create();

        ImageView btn_camera = (ImageView) view.findViewById(R.id.btn_camera);
        ImageView btn_gallery = (ImageView) view.findViewById(R.id.btn_gallery);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomaFoto();
                dialogPhoto.dismiss();
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
                dialogPhoto.dismiss();
            }
        });

        dialogPhoto.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogPhoto.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

                if (requestCode == PICK_IMAGE_VEHICULO) {
                    Uri uri = data.getData();
                    galleryPhoto.setPhotoUri(uri);
                    String photoPath = galleryPhoto.getPath();
                    photoPathSelected = photoPath;
                    try {
                        Bitmap bitmap = ImageLoader.
                                init().
                                from(photoPath).
                                requestSize(1024, 800).
                                getBitmap();
                        imgVehiculo.setImageBitmap(bitmap);
                        imageBase64 = Utils.encodeImageBase64(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                } else if (requestCode == TAKE_PICTURE_VEHICULO) {
                    String photoPath = cameraPhoto.getPhotoPath();
                    photoPathSelected = photoPath;
                    try {
                        Bitmap bitmap = ImageLoader.
                                init().
                                from(photoPath).
                                requestSize(1024, 800).
                                getBitmap();
                        imgVehiculo.setImageBitmap(Utils.rotateBitmap(bitmap, 90));
                        imageBase64 = Utils.encodeImageBase64(Utils.rotateBitmap(bitmap, 90));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

        }
    }

    private class QueryEmpleadoTask extends AsyncTask<String, Integer, String> {
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
            UtilsDML.resultQueryEmpleado(result, listEmpleados);
            String[] idNomEmpleado = new String[listEmpleados.size()];
            for (int i = 0; i < listEmpleados.size(); i++) {
                idNomEmpleado[i] = listEmpleados.get(i).getId_empleado() + " " + listEmpleados.get(i).getNombre();
            }
            spinnerEmpleado.setAdapter(new ArrayAdapter<>(getApplication(), R.layout.row_spinner_item, idNomEmpleado));
            // setUpRecyclerView();
            progressBar.cancel();
        }
    }

    private class AddVehiculoTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_VEHICULO, baseUrl, jsonData);

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Utils.proccessResult(FormVehiculo.this,result);
            progressBar.cancel();
            Intent intent = new Intent();
            intent.putExtra(Constants.REFRESH_FRAGMENT_VEHICULO, Constants.REFRESH_FRAGMENT_VEHICULO);
            setResult(RESULT_OK,intent);
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
