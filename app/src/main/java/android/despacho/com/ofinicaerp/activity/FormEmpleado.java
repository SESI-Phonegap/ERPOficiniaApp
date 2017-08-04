package android.despacho.com.ofinicaerp.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.despacho.com.ofinicaerp.ActivityBase;
import android.despacho.com.ofinicaerp.fragments.EmpleadoFragment;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.PICK_IMAGE_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PICK_IMAGE_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.TAKE_PICTURE_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.TAKE_PICTURE_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_EMPLEADO;

public class FormEmpleado extends ActivityBase implements View.OnClickListener {

    private ImageView imgBack;
    private CircleImageView photoEmpleado;
    private EditText et_nombre;
    private EditText et_puesto;
    private EditText et_sueldo;
    private EditText et_empresa;
    private EditText et_telefono;
    private Button btn_guardar;
    private String imageBase64;
    private CameraPhoto cameraPhoto;
    private GalleryPhoto galleryPhoto;
    private ProgressDialog progressBar;
    private String photoPathSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_empleado);
        init();
    }

    public void init(){
        progressBar = new ProgressDialog(FormEmpleado.this);
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        imageBase64 = "";
        imgBack = (ImageView) findViewById(R.id.imgBack);
        photoEmpleado = (CircleImageView) findViewById(R.id.empleado_photo);
        et_nombre = (EditText) findViewById(R.id.empleado_nombre);
        et_puesto = (EditText) findViewById(R.id.empleado_puesto);
        et_sueldo = (EditText) findViewById(R.id.empleado_sueldo);
        et_empresa = (EditText) findViewById(R.id.empleado_empresa);
        et_telefono = (EditText) findViewById(R.id.empleado_telefono);
        btn_guardar = (Button) findViewById(R.id.btn_guardar);

        imgBack.setOnClickListener(this);
        photoEmpleado.setOnClickListener(this);
        btn_guardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.imgBack:
                onBackPressed();
                break;

            case R.id.empleado_photo:
                dialogSelectPhoto();
                break;

            case R.id.btn_guardar:
                String nombre = et_nombre.getText().toString();
                String puesto = et_puesto.getText().toString();
                String sueldo = et_sueldo.getText().toString();
                String empresa = et_empresa.getText().toString();
                String telefono = et_telefono.getText().toString();

                if (nombre.equals("") || puesto.equals("") || sueldo.equals("") ||
                        empresa.equals("") || telefono.equals("") || photoPathSelected.equals("")){
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    ModelEmpleado modelEmpleado = new ModelEmpleado(nombre, puesto, Double.parseDouble(sueldo), empresa, imageBase64, telefono);
                    String strJson = modelEmpleado.toJsonAddEmpleado();
                    new AddEmpleadoTask().execute(URL_ADD_EMPLEADO, strJson);
                }
                break;
        }
    }

    public void tomaFoto() {
        try {
            startActivityForResult(cameraPhoto.takePhotoIntent(), TAKE_PICTURE_EMPLEADO);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openGallery() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(FormEmpleado.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(FormEmpleado.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);

            } else {
                startActivityForResult(galleryPhoto.openGalleryIntent(), PICK_IMAGE_EMPLEADO);
            }
        } else {
            startActivityForResult(galleryPhoto.openGalleryIntent(), PICK_IMAGE_EMPLEADO);
        }

    }

    public void dialogSelectPhoto() {
        final AlertDialog dialogPhoto;
        final AlertDialog.Builder builder = new AlertDialog.Builder(FormEmpleado.this);
        LayoutInflater inflater = FormEmpleado.this.getLayoutInflater();
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

            if (requestCode == PICK_IMAGE_EMPLEADO) {
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
                    photoEmpleado.setImageBitmap(bitmap);
                    imageBase64 = Utils.encodeImageBase64(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            } else if (requestCode == TAKE_PICTURE_EMPLEADO) {
                String photoPath = cameraPhoto.getPhotoPath();
                photoPathSelected = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.
                            init().
                            from(photoPath).
                            requestSize(1024, 800).
                            getBitmap();
                    photoEmpleado.setImageBitmap(Utils.rotateBitmap(bitmap, 90));
                    imageBase64 = Utils.encodeImageBase64(Utils.rotateBitmap(bitmap, 90));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private class AddEmpleadoTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_EMPLEADO, baseUrl, jsonData);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Utils.proccessResult(FormEmpleado.this,result);
            changeFragment(EmpleadoFragment.newInstance(),R.id.mainFrame,false,false);
            progressBar.cancel();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }
}
