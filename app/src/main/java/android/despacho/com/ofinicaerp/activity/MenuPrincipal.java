package android.despacho.com.ofinicaerp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.despacho.com.ofinicaerp.R;
import android.despacho.com.ofinicaerp.fragments.ClientesDespachoFragment;
import android.despacho.com.ofinicaerp.fragments.EmpleadoFragment;
import android.despacho.com.ofinicaerp.fragments.HomeFragment;
import android.despacho.com.ofinicaerp.fragments.RutasFragment;
import android.despacho.com.ofinicaerp.fragments.VehiculoFragment;
import android.despacho.com.ofinicaerp.models.ModelDespacho_Clientes;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.despacho.com.ofinicaerp.models.ModelVehiculo;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.ADMIN;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_ID_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_TIPO_USER;
import static android.despacho.com.ofinicaerp.utils.Constants.TAKE_PICTURE_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.TAKE_PICTURE_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_CLIENTE;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_EMPLEADO;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FloatingActionButton fab;
    private AlertDialog dialog;
    private String imageBase64;
    private CircleImageView photoCar;
    private CircleImageView photoEmpleado;
    private String id_empleado;
    private ProgressDialog progressBar;
    private List<ModelEmpleado> listEmpleados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        init();


    }

    public void init() {
        imageBase64 = "";
        progressBar = new ProgressDialog(MenuPrincipal.this);
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        listEmpleados = new ArrayList<>();
        new QueryEmpleadoTask().execute(URL_QUERY_EMPLEADO);
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
        int idUser = getIntent().getIntExtra(PUTEXTRA_ID_EMPLEADO,0);

        Log.i("USER-MENUP--",typeUser + " - " + idUser);
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

    public void changeFragment(Fragment fragment, int resource, boolean isRoot, boolean backStack) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (isRoot) {
            transaction.add(resource, fragment);
        } else {
            transaction.replace(resource, fragment);
        }

        if (backStack) {
            transaction.addToBackStack(null);
        }
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.enter_from_left);
        //transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        transaction.commit();
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
            createDialogNewVehiculo();
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

    public void createDialogNewVehiculo() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_vehiculo, null);


        String[] idNomEmpleado = new String[listEmpleados.size()];
        for (int i = 0; i < listEmpleados.size() ; i++){
            idNomEmpleado[i] = listEmpleados.get(i).getId_empleado() + " " + listEmpleados.get(i).getNombre();
        }
        builder.setView(view);


        photoCar = (CircleImageView) view.findViewById(R.id.vehiculo_photo);
        Button btn_guardar = (Button) view.findViewById(R.id.btn_vehiculo_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_vehiculo_cancelar);
        final EditText et_nombre = (EditText) view.findViewById(R.id.vehiculo_nombre);
        final EditText et_modelo = (EditText) view.findViewById(R.id.vehiculo_ano);
        final EditText et_marca = (EditText) view.findViewById(R.id.vehiculo_marca);
        final EditText et_serie = (EditText) view.findViewById(R.id.vehiculo_serie);
        final EditText et_placas = (EditText) view.findViewById(R.id.vehiculo_placas);
        final EditText et_color = (EditText) view.findViewById(R.id.vehiculo_color);
        final Spinner spinnerEmpleado = (Spinner) view.findViewById(R.id.vehiculo_spinner);
        id_empleado = "";
        imageBase64 = "";
        dialog = builder.create();
        spinnerEmpleado.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,idNomEmpleado));
        spinnerEmpleado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_empleado = parent.getItemAtPosition(position).toString().substring(0,1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        photoCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomaFoto(TAKE_PICTURE_VEHICULO);
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
                String modelo = et_modelo.getText().toString();
                String marca = et_marca.getText().toString();
                String serie = et_serie.getText().toString();
                String placas = et_placas.getText().toString();
                String color = et_color.getText().toString();
                // String empleado = spinnerEmpleado.
                if (nombre.equals("") || modelo.equals("") || marca.equals("") || serie.equals("") ||
                        placas.equals("") || color.equals("") || id_empleado.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {

                    ModelVehiculo modelVehiculo = new ModelVehiculo(nombre, modelo, marca, serie, Integer.parseInt(id_empleado), imageBase64,placas,color);
                    String strJson = modelVehiculo.toJsonAddVehiculo();
                    new AddVehiculoTask().execute(URL_ADD_VEHICULO, strJson);
                }

            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();

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
                tomaFoto(TAKE_PICTURE_EMPLEADO);
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
                if (nombre.equals("") && puesto.equals("") && sueldo.equals("") && empresa.equals("") &&
                        telefono.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    ModelEmpleado modelEmpleado = new ModelEmpleado(nombre,puesto,Double.parseDouble(sueldo),empresa,imageBase64,telefono);
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
                if (nombre.equals("") && rfc.equals("") && curp.equals("") && honorario.equals("")) {
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
                    new AddClienteTask().execute(URL_ADD_CLIENTE,strJson);
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
        Intent intentCamera;
        switch (action) {
            case TAKE_PICTURE_VEHICULO:
                intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, TAKE_PICTURE_VEHICULO);
                break;

            case TAKE_PICTURE_EMPLEADO:
                intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, TAKE_PICTURE_EMPLEADO);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Bitmap bMap = (Bitmap) data.getExtras().get("data");
            bMap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] byteImage = baos.toByteArray();
            imageBase64 = Base64.encodeToString(byteImage, Base64.DEFAULT);

            if (requestCode == TAKE_PICTURE_VEHICULO) {
                photoCar.setImageBitmap(bMap);
            } else if (requestCode == TAKE_PICTURE_EMPLEADO) {
                photoEmpleado.setImageBitmap(bMap);
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
            return UtilsDML.addVehiculo(baseUrl, jsonData);

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
            return UtilsDML.addEmpleado(baseUrl,jsonData);
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
            return UtilsDML.addCliente(baseUrl,jsonData);
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
            UtilsDML.resultQueryEmpleado(result,listEmpleados);
           // setUpRecyclerView();
            progressBar.cancel();
        }
    }

    public void proccessResult(String result){
        if (result.contains("OK")) {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_success), Toast.LENGTH_LONG).show();
            dialog.dismiss();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.msg_error) + result, Toast.LENGTH_LONG).show();
        }
    }
}
