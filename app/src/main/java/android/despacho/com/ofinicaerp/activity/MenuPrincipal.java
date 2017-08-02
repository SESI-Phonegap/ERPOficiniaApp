package android.despacho.com.ofinicaerp.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.despacho.com.ofinicaerp.ActivityBase;
import android.despacho.com.ofinicaerp.R;
import android.despacho.com.ofinicaerp.fragments.ClientesDespachoFragment;
import android.despacho.com.ofinicaerp.fragments.EmpleadoFragment;
import android.despacho.com.ofinicaerp.fragments.GastoGasolinaFragment;
import android.despacho.com.ofinicaerp.fragments.HomeFragment;
import android.despacho.com.ofinicaerp.fragments.RutasFragment;
import android.despacho.com.ofinicaerp.fragments.VehiculoFragment;
import android.despacho.com.ofinicaerp.models.ModelDespacho_Clientes;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.despacho.com.ofinicaerp.models.ModelGastosGasolina;
import android.despacho.com.ofinicaerp.models.ModelVehiculo;
import android.despacho.com.ofinicaerp.utils.CameraPhoto;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.GalleryPhoto;
import android.despacho.com.ofinicaerp.utils.ImageFilePath;
import android.despacho.com.ofinicaerp.utils.ImageLoader;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.ADMIN;
import static android.despacho.com.ofinicaerp.utils.Constants.EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PICK_IMAGE_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PICK_IMAGE_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_ID_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_TIPO_USER;
import static android.despacho.com.ofinicaerp.utils.Constants.TAKE_PICTURE_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.TAKE_PICTURE_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_CLIENTE;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_GASTO_GASOLINA;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.VEHICULO;

public class MenuPrincipal extends ActivityBase
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;
    private AlertDialog dialog;
    private String imageBase64;
    private CircleImageView photoCar;
    private CircleImageView photoEmpleado;
    private String id_empleado;
    private ProgressDialog progressBar;
    private List<ModelEmpleado> listEmpleados;
    private List<ModelVehiculo> listVehiculos;
    private String idVehiculo;
    private EditText et_fecha;
    private String photoPathSelected;
    private CameraPhoto cameraPhoto;
    private GalleryPhoto galleryPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        init();


    }

    public void init() {
        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());
        imageBase64 = "";
        photoPathSelected = "";
        progressBar = new ProgressDialog(MenuPrincipal.this);
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        listEmpleados = new ArrayList<>();
        listVehiculos = new ArrayList<>();

        new QueryVehiculoTask().execute(URL_QUERY_VEHICULO);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        String typeUser = getIntent().getStringExtra(PUTEXTRA_TIPO_USER);
        int idUser = getIntent().getIntExtra(PUTEXTRA_ID_EMPLEADO, 0);

        Log.i("USER-MENUP--", typeUser + " - " + idUser);
        Menu menu = navigationView.getMenu();
        MenuItem mClientes = menu.findItem(R.id._nav_clientes);
        MenuItem mHonorarios = menu.findItem(R.id._nav_honorarios);

        if (typeUser.equals(ADMIN)) {

        } else {
            mClientes.setEnabled(false);
            mHonorarios.setEnabled(false);
        }


        changeFragment(HomeFragment.newInstance(), R.id.mainFrame, false, false);
        fab.setOnClickListener(onClickHome);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id._nav_home:
                changeFragment(HomeFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickHome);
                break;

            case R.id._nav_ruta:
                changeFragment(RutasFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickRutas);
                break;

            case R.id._nav_empleados:
                changeFragment(EmpleadoFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickEmpleado);
                break;

            case R.id._nav_pago_nomina:
                break;

            case R.id._nav_registro_vehiculo:
                changeFragment(VehiculoFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickVehiculo);
                break;

            case R.id._nav_registro_mantenimiento:
                break;

            case R.id._nav_gasto_gasolina:
                changeFragment(GastoGasolinaFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickGastoGasolina);
                break;

            case R.id._nav_clientes:
                changeFragment(ClientesDespachoFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickClientes);
                break;

            case R.id._nav_honorarios:
                Toast.makeText(this, "Honorarios", Toast.LENGTH_LONG).show();
                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // Eventos para el FloatingActionButton

    View.OnClickListener onClickHome = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(v, "HomeFragment", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    };

    View.OnClickListener onClickRutas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(v, "RutasFragment", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    };

    View.OnClickListener onClickVehiculo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuPrincipal.this,FormVehiculo.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onClickEmpleado = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogNewEmpleado();
        }
    };

    View.OnClickListener onClickClientes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogNewCliente();
        }
    };

    View.OnClickListener onClickGastoGasolina = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogNewGastoGasolina();
        }
    };

    public void dialogSelectPhoto(final String from) {
        final AlertDialog dialogPhoto;
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_select_photo, null);
        builder.setView(view);
        dialogPhoto = builder.create();

        ImageView btn_camera = (ImageView) view.findViewById(R.id.btn_camera);
        ImageView btn_gallery = (ImageView) view.findViewById(R.id.btn_gallery);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (from.equals(VEHICULO)) {
                    tomaFoto(TAKE_PICTURE_VEHICULO);
                } else if (from.equals(EMPLEADO)) {
                    tomaFoto(TAKE_PICTURE_EMPLEADO);
                }
                dialogPhoto.dismiss();
            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (from.equals(VEHICULO)) {
                    openGallery(PICK_IMAGE_VEHICULO);
                } else if (from.equals(EMPLEADO)) {
                    openGallery(PICK_IMAGE_EMPLEADO);
                }
                dialogPhoto.dismiss();
            }
        });

        dialogPhoto.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogPhoto.show();
    }


    public void createDialogNewEmpleado() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_empleado, null);

        builder.setView(view);


        photoEmpleado = (CircleImageView) view.findViewById(R.id.empleado_photo);
        Button btn_guardar = (Button) view.findViewById(R.id.btn_empleado_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_empleado_cancelar);
        final EditText et_nombre = (EditText) view.findViewById(R.id.empleado_nombre);
        final EditText et_puesto = (EditText) view.findViewById(R.id.empleado_puesto);
        final EditText et_sueldo = (EditText) view.findViewById(R.id.empleado_sueldo);
        final EditText et_empresa = (EditText) view.findViewById(R.id.empleado_empresa);
        final EditText et_telefono = (EditText) view.findViewById(R.id.empleado_telefono);

        imageBase64 = "";
        dialog = builder.create();

        photoEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSelectPhoto(EMPLEADO);
            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = et_nombre.getText().toString();
                String puesto = et_puesto.getText().toString();
                String sueldo = et_sueldo.getText().toString();
                String empresa = et_empresa.getText().toString();
                String telefono = et_telefono.getText().toString();

                // String empleado = spinnerEmpleado.
                if (nombre.equals("") || puesto.equals("") || sueldo.equals("") || empresa.equals("") ||
                        telefono.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    ModelEmpleado modelEmpleado = new ModelEmpleado(nombre, puesto, Double.parseDouble(sueldo), empresa, imageBase64, telefono);
                    String strJson = modelEmpleado.toJsonAddEmpleado();
                    new AddEmpleadoTask().execute(URL_ADD_EMPLEADO, strJson);
                }

            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
    }

    public void createDialogNewCliente() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_cliente, null);

        builder.setView(view);

        Button btn_guardar = (Button) view.findViewById(R.id.btn_cliente_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_cliente_cancelar);
        final EditText et_nombre = (EditText) view.findViewById(R.id.cliente_nombre);
        final EditText et_rfc = (EditText) view.findViewById(R.id.cliente_rfc);
        final EditText et_curp = (EditText) view.findViewById(R.id.cliente_curp);
        final EditText et_honorario = (EditText) view.findViewById(R.id.cliente_honorario);
        final EditText et_passSat = (EditText) view.findViewById(R.id.cliente_passSat);
        final EditText et_passFiel = (EditText) view.findViewById(R.id.cliente_passFiel);
        final EditText et_passCertificado = (EditText) view.findViewById(R.id.cliente_passCertificado);

        imageBase64 = "";
        dialog = builder.create();

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = et_nombre.getText().toString();
                String rfc = et_rfc.getText().toString();
                String curp = et_curp.getText().toString();
                String honorario = et_honorario.getText().toString();

                // String empleado = spinnerEmpleado.
                if (nombre.equals("") || rfc.equals("") || curp.equals("") || honorario.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    ModelDespacho_Clientes cliente = new ModelDespacho_Clientes(
                            nombre,
                            rfc,
                            curp,
                            et_passSat.getText().toString(),
                            et_passFiel.getText().toString(),
                            et_passCertificado.getText().toString(),
                            Double.parseDouble(honorario));

                    String strJson = cliente.toJsonAddCliente();
                    new AddClienteTask().execute(URL_ADD_CLIENTE, strJson);
                }

            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
    }

    public void createDialogNewGastoGasolina() {

        idVehiculo = "";
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_gasto_gasolina, null);

        builder.setView(view);

        final String[] idNomVehiculo = new String[listVehiculos.size()];
        for (int i = 0; i < listVehiculos.size(); i++) {
            idNomVehiculo[i] = listVehiculos.get(i).getId_vehiculo() + " " + listVehiculos.get(i).getNombre();
        }
        Button btn_guardar = (Button) view.findViewById(R.id.btn_gasolina_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_gasolina_cancelar);
        final Spinner spinner_idVehiculo = (Spinner) view.findViewById(R.id.gasolina_spinner_idvehiculo);
        et_fecha = (EditText) view.findViewById(R.id.gasolina_et_fecha);
        final EditText et_gas = (EditText) view.findViewById(R.id.gasolina_et_tipoGas);
        final EditText et_litros = (EditText) view.findViewById(R.id.gasolina_et_litros);
        final EditText et_monto = (EditText) view.findViewById(R.id.gasolina_et_monto);

        dialog = builder.create();

        et_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogDate(MenuPrincipal.this, et_fecha);
            }
        });
        spinner_idVehiculo.setAdapter(new ArrayAdapter<>(getApplication(), R.layout.row_spinner_item, idNomVehiculo));
        spinner_idVehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idVehiculo = parent.getItemAtPosition(position).toString().substring(0, 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = et_fecha.getText().toString();
                String gas = et_gas.getText().toString();
                String litros = et_litros.getText().toString();
                String monto = et_monto.getText().toString();

                // String empleado = spinnerEmpleado.
                if (fecha.equals("") || gas.equals("") || litros.equals("") || monto.equals("") || idNomVehiculo.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    ModelGastosGasolina gastoGas = new ModelGastosGasolina(
                            Integer.parseInt(idVehiculo),
                            fecha,
                            gas,
                            Double.parseDouble(litros),
                            Double.parseDouble(monto));

                    String strJson = gastoGas.toJsonAddGasolina();
                    new AddGastoGasolinaTask().execute(URL_ADD_GASTO_GASOLINA, strJson);
                }

            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
    }

    public void tomaFoto(int action) {

        try {

            switch (action) {
                case TAKE_PICTURE_VEHICULO:
                    startActivityForResult(cameraPhoto.takePhotoIntent(), TAKE_PICTURE_VEHICULO);
                    break;

                case TAKE_PICTURE_EMPLEADO:
                    startActivityForResult(cameraPhoto.takePhotoIntent(), TAKE_PICTURE_EMPLEADO);
                    break;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void openGallery(int action) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(MenuPrincipal.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(MenuPrincipal.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 999);

            } else {
                switchGallery(action);
            }
        } else {
            switchGallery(action);
        }

    }

    public void switchGallery(int action) {
      //  Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        switch (action) {
            case PICK_IMAGE_EMPLEADO:
                startActivityForResult(galleryPhoto.openGalleryIntent(), PICK_IMAGE_EMPLEADO);
                break;

            case PICK_IMAGE_VEHICULO:
                startActivityForResult(galleryPhoto.openGalleryIntent(), PICK_IMAGE_VEHICULO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (data != null) {

                    if (requestCode == PICK_IMAGE_VEHICULO) {
                        Uri uri = data.getData();
                        galleryPhoto.setPhotoUri(uri);
                        String photoPath = galleryPhoto.getPath();
                        try {
                            Bitmap bitmap = ImageLoader.
                                    init().
                                    from(photoPath).
                                    requestSize(1024, 800).
                                    getBitmap();
                            photoCar.setImageBitmap(bitmap);
                   //         imageBase64 = Utils.encodeImageBase64(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }

                    } else if (requestCode == PICK_IMAGE_EMPLEADO) {
                        Uri uri = data.getData();
                        galleryPhoto.setPhotoUri(uri);
                        String photoPath = galleryPhoto.getPath();
                        try {
                            Bitmap bitmap = ImageLoader.
                                    init().
                                    from(photoPath).
                                    requestSize(1024, 800).
                                    getBitmap();
                            photoEmpleado.setImageBitmap(bitmap);
                    //        imageBase64 = Utils.encodeImageBase64(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }

                    } /*else if (requestCode == TAKE_PICTURE_EMPLEADO) {
                        String photoPath = cameraPhoto.getPhotoPath();
                        try {
                            Bitmap bitmap = ImageLoader.
                                    init().
                                    from(photoPath).
                                    requestSize(1024, 800).
                                    getBitmap();
                            photoEmpleado.setImageBitmap(rotateBitmap(bitmap, 90));
                   //         imageBase64 = Utils.encodeImageBase64(rotateBitmap(bitmap, 90));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else if (requestCode == TAKE_PICTURE_VEHICULO) {
                        String photoPath = cameraPhoto.getPhotoPath();
                        try {
                            Bitmap bitmap = ImageLoader.
                                    init().
                                    from(photoPath).
                                    requestSize(1024, 800).
                                    getBitmap();
                            photoCar.setImageBitmap(rotateBitmap(bitmap, 90));
                    //        imageBase64 = Utils.encodeImageBase64(rotateBitmap(bitmap, 90));
                        } catch (FileNotFoundException e){
                            e.printStackTrace();
                        }
                    }*/



            }
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
            proccessResult(result);
            progressBar.cancel();
        }

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
            proccessResult(result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.cancel();
        }
    }

    private class AddClienteTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_CLIENTE, baseUrl, jsonData);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            proccessResult(result);
            progressBar.cancel();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    private class AddGastoGasolinaTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_GASOLINA, baseUrl, jsonData);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            proccessResult(result);
            progressBar.cancel();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }


    private class QueryVehiculoTask extends AsyncTask<String, Integer, String> {
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
            UtilsDML.resultQueryVehiculo(result, listVehiculos);
            // setUpRecyclerView();
            progressBar.cancel();
        }

    }

    public void proccessResult(String result) {
        if (result.contains("OK")) {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_success), Toast.LENGTH_LONG).show();
            dialog.dismiss();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_error) + result, Toast.LENGTH_LONG).show();
        }
    }

}
