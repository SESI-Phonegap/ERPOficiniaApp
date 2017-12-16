package android.despacho.com.ofinicaerp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.despacho.com.ofinicaerp.ActivityBase;
import android.despacho.com.ofinicaerp.R;
import android.despacho.com.ofinicaerp.fragments.CajaFragment;
import android.despacho.com.ofinicaerp.fragments.ClientesDespachoFragment;
import android.despacho.com.ofinicaerp.fragments.ComprobanteFragment;
import android.despacho.com.ofinicaerp.fragments.EmpleadoFragment;
import android.despacho.com.ofinicaerp.fragments.GastoGasolinaFragment;
import android.despacho.com.ofinicaerp.fragments.GastosFragment;
import android.despacho.com.ofinicaerp.fragments.HomeFragment;
import android.despacho.com.ofinicaerp.fragments.HonorariosFragment;
import android.despacho.com.ofinicaerp.fragments.IngresosFragment;
import android.despacho.com.ofinicaerp.fragments.MantenimientoVehiculoFragment;
import android.despacho.com.ofinicaerp.fragments.NominaFragment;
import android.despacho.com.ofinicaerp.fragments.RutasFragment;
import android.despacho.com.ofinicaerp.fragments.TiendasFragment;
import android.despacho.com.ofinicaerp.fragments.VehiculoFragment;
import android.despacho.com.ofinicaerp.models.ModelCaja;
import android.despacho.com.ofinicaerp.models.ModelDespacho_Clientes;
import android.despacho.com.ofinicaerp.models.ModelDespacho_Honorarios;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.despacho.com.ofinicaerp.models.ModelGastos;
import android.despacho.com.ofinicaerp.models.ModelGastosGasolina;
import android.despacho.com.ofinicaerp.models.ModelRutas;
import android.despacho.com.ofinicaerp.models.ModelTienda;
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
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.ADMIN;
import static android.despacho.com.ofinicaerp.utils.Constants.PICK_IMAGE_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PICK_IMAGE_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_ID_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_TIPO_USER;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_CLIENTE;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_GASTO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_GASTO_GASOLINA;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_HONORARIO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_RUTA;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_TIENDA;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_CAJA;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_CLIENTE;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_RUTAS;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_VEHICULO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_UPDATE_CAJA;

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
    public static List<ModelVehiculo> listVehiculos;
    public static List<ModelRutas> listRutas;
    private List<ModelDespacho_Clientes> listClientes;
    private String idVehiculo;
    private String idRuta;
    private String idEmpleado;
    private String idStatus;
    private String idMes;
    private String ano;
    private String idCliente;
    private String photoPathSelected;
    private CameraPhoto cameraPhoto;
    private GalleryPhoto galleryPhoto;
    public static List<ModelCaja> caja;
    public static double montoActual_Gasto;
    private EditText et_monto;
    private NavigationView navigationView;
    private static volatile MenuPrincipal instance = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new QueryCajaTask().execute(URL_QUERY_CAJA);
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
        listRutas = new ArrayList<>();
        listClientes = new ArrayList<>();
        caja = new ArrayList<>();
        montoActual_Gasto = 0;

        new QueryVehiculoTask().execute(URL_QUERY_VEHICULO);
        new QueryRutaTask().execute(URL_QUERY_RUTAS);
        new QueryEmpleadoTask().execute(URL_QUERY_EMPLEADO);
        new QueryCajaTask().execute(URL_QUERY_CAJA);
        new QueryClienteTask().execute(URL_QUERY_CLIENTE);

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

        if (!typeUser.equals(ADMIN)) {
            deshabilitaOpciones();
        }
        changeFragment(HomeFragment.newInstance(), R.id.mainFrame, false, false);
        fab.setOnClickListener(onClickHome);
    }

    public void deshabilitaOpciones(){
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu miMenu = navigationView.getMenu();
        miMenu.findItem(R.id._nav_honorarios).setVisible(false);
        miMenu.findItem(R.id._nav_clientes).setVisible(false);
        miMenu.findItem(R.id._nav_registro_vehiculo).setVisible(false);
        miMenu.findItem(R.id._nav_pago_nomina).setVisible(false);
        miMenu.findItem(R.id._nav_empleados).setVisible(false);
        miMenu.findItem(R.id._nav_ingresos).setVisible(false);
        miMenu.findItem(R.id._nav_caja).setVisible(false);
        miMenu.findItem(R.id._nav_tienda).setVisible(false);
        miMenu.findItem(R.id._nav_ruta).setVisible(false);
    }

    public static synchronized MenuPrincipal getInstance() {
        if (instance == null) {
            synchronized (MenuPrincipal.class) {
                if (instance == null) {
                    instance = new MenuPrincipal();
                }
            }
        }
        return instance;
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

            case R.id._nav_tienda:
                changeFragment(TiendasFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickTiendas);
                break;

            case R.id._nav_caja:
                changeFragment(CajaFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickCaja);
                break;

            case R.id._nav_ingresos:
                changeFragment(IngresosFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickIngresos);
                break;

            case R.id._nav_gastos:
                changeFragment(GastosFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickGastos);
                break;

            case R.id._nav_compGastos:
                changeFragment(ComprobanteFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickComprobante);
                break;

            case R.id._nav_empleados:
                changeFragment(EmpleadoFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickEmpleado);
                break;

            case R.id._nav_pago_nomina:
                Fragment nominaFragment = Fragment.instantiate(MenuPrincipal.this,NominaFragment.class.getName());
                changeFragment(nominaFragment, R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickNomina);
                break;

            case R.id._nav_registro_vehiculo:
                changeFragment(VehiculoFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickVehiculo);
                break;

            case R.id._nav_registro_mantenimiento:
                changeFragment(MantenimientoVehiculoFragment.newInstance(), R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickMantenimiento);
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
                Fragment honorarioFragment = Fragment.instantiate(MenuPrincipal.this,HonorariosFragment.class.getName());
                changeFragment(honorarioFragment, R.id.mainFrame, false, false);
                fab.setOnClickListener(onClickHonorario);
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
            createDialogRuta();
        }
    };

    View.OnClickListener onClickTiendas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogTienda();
        }
    };

    View.OnClickListener onClickCaja = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogUpdateCaja();
        }
    };
    View.OnClickListener onClickIngresos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuPrincipal.this, FormIngreso.class);
            startActivityForResult(intent, 6666);
        }
    };

    View.OnClickListener onClickGastos = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogGasto();
        }
    };

    View.OnClickListener onClickComprobante = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuPrincipal.this,FormComprobanteGasto.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onClickVehiculo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuPrincipal.this, FormVehiculo.class);
            intent.putExtra("FROM","new");
            startActivityForResult(intent, 9999);
        }
    };

    View.OnClickListener onClickMantenimiento = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuPrincipal.this, FormMantenimiento.class);
            startActivityForResult(intent, 8888);
        }
    };

    View.OnClickListener onClickEmpleado = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuPrincipal.this, FormEmpleado.class);
            startActivityForResult(intent, 7777);
        }
    };

    View.OnClickListener onClickNomina = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MenuPrincipal.this, FormNomina.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onClickClientes = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogNewCliente();
        }
    };

    View.OnClickListener onClickHonorario = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogNewHonorario();
        }
    };

    View.OnClickListener onClickGastoGasolina = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            createDialogNewGastoGasolina();
        }
    };

    public void createDialogRuta() {
        if (dialog != null){
            dialog.cancel();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_ruta, null);

        builder.setView(view);

        final EditText et_ruta = (EditText) view.findViewById(R.id.dialog_et_ruta);
        Button btn_guadar = (Button) view.findViewById(R.id.btn_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_cancelar);

        dialog = builder.create();

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_guadar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ruta = et_ruta.getText().toString();
                if (ruta.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    ModelRutas rutas = new ModelRutas(ruta);
                    String strJSON = rutas.toJsonAddRuta();
                    new AddRutaTask().execute(URL_ADD_RUTA, strJSON);

                }
            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
    }

    public void createDialogTienda() {
        if (dialog != null){
            dialog.cancel();
        }
        idRuta = "";
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_tienda, null);

        builder.setView(view);

        final String[] idNomRuta = new String[listRutas.size()];
        for (int i = 0; i < listRutas.size(); i++) {
            idNomRuta[i] = listRutas.get(i).getId_ruta() + "-" + listRutas.get(i).getRuta();
        }

        final EditText et_tienda = (EditText) view.findViewById(R.id.dialog_et_tienda);
        final EditText et_direccion = (EditText) view.findViewById(R.id.dialog_et_direccion);
        Spinner spinner_ruta = (Spinner) view.findViewById(R.id.spinner_dialog_et_tienda);
        Button btn_guardar = (Button) view.findViewById(R.id.btn_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_cancelar);

        spinner_ruta.setAdapter(new ArrayAdapter<>(getApplication(), R.layout.row_spinner_item, idNomRuta));
        spinner_ruta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] parts = parent.getItemAtPosition(position).toString().split("-");

                idRuta = parts[0];
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
                String tienda = et_tienda.getText().toString();
                String direccion = et_direccion.getText().toString();
                if (tienda.equals("") || direccion.equals("") || idRuta.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    ModelTienda modelTienda = new ModelTienda(tienda, direccion, Integer.parseInt(idRuta));
                    String strJSON = modelTienda.toJsonAddTienda();
                    new AddTiendaTask().execute(URL_ADD_TIENDA, strJSON);

                }
            }
        });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();

    }

    public void createDialogNewCliente() {
        if (dialog != null){
            dialog.cancel();
        }
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
        if (dialog != null){
            dialog.cancel();
        }
        montoActual_Gasto = 0;
        idVehiculo = "";
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_gasto_gasolina, null);

        builder.setView(view);

        final String[] idNomVehiculo = new String[listVehiculos.size()];
        for (int i = 0; i < listVehiculos.size(); i++) {
            idNomVehiculo[i] = listVehiculos.get(i).getId_vehiculo() + "-" + listVehiculos.get(i).getNombre();
        }
        Button btn_guardar = (Button) view.findViewById(R.id.btn_gasolina_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_gasolina_cancelar);
        final Spinner spinner_idVehiculo = (Spinner) view.findViewById(R.id.gasolina_spinner_idvehiculo);
        final EditText et_fecha = (EditText) view.findViewById(R.id.gasolina_et_fecha);
        final EditText et_gas = (EditText) view.findViewById(R.id.gasolina_et_tipoGas);
        final EditText et_litros = (EditText) view.findViewById(R.id.gasolina_et_litros);
        et_monto = (EditText) view.findViewById(R.id.gasolina_et_monto);
        et_monto.addTextChangedListener(textWatcherMontoCaja);

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
                String[] parts = parent.getItemAtPosition(position).toString().split("-");

                idVehiculo = parts[0];
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
                String monto = et_monto.getText().toString().replaceAll(Constants.PAYMENT_NUMBER_FORMAT_REGEX_POINT, Constants.STRING_EMPTY);

                // String empleado = spinnerEmpleado.
                if (fecha.equals("") || gas.equals("") || litros.equals("") || monto.equals("") || idNomVehiculo.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    montoActual_Gasto = caja.get(0).getMonto() - Double.parseDouble(monto);
                    if (montoActual_Gasto < 0) {
                        Snackbar.make(v, getResources().getString(R.string.msg_monto_mayor_a_caja), Snackbar.LENGTH_LONG).show();
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

            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
    }

    public void createDialogGasto() {
        if (dialog != null){
            dialog.cancel();
        }
        montoActual_Gasto = 0;
        idRuta = "";
        idEmpleado = "";
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_gasto, null);
        builder.setView(view);

        final String[] idNomRuta = new String[listRutas.size()];
        for (int i = 0; i < listRutas.size(); i++) {
            idNomRuta[i] = listRutas.get(i).getId_ruta() + "-" + listRutas.get(i).getRuta();
        }

        String[] idNomEmpleado = new String[listEmpleados.size()];
        for (int x = 0; x < listEmpleados.size(); x++) {
            idNomEmpleado[x] = listEmpleados.get(x).getId_empleado() + "-" + listEmpleados.get(x).getNombre();
        }

        Button btn_guardar = (Button) view.findViewById(R.id.btn_gasto_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_gasto_cancelar);
        Spinner spinner_idRuta = (Spinner) view.findViewById(R.id.spinner_gasto_idRuta);
        Spinner spinner_idEmpleado = (Spinner) view.findViewById(R.id.spinner_gasto_idEmpleado);
        final EditText et_fecha = (EditText) view.findViewById(R.id.gasto_et_fecha);
        et_monto = (EditText) view.findViewById(R.id.gasto_et_monto);
        et_monto.addTextChangedListener(textWatcherMontoCaja);
        et_fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogDate(MenuPrincipal.this, et_fecha);
            }
        });
        spinner_idRuta.setAdapter(new ArrayAdapter<>(getApplication(), R.layout.row_spinner_item, idNomRuta));
        spinner_idRuta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] parts = parent.getItemAtPosition(position).toString().split("-");

                idRuta = parts[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_idEmpleado.setAdapter(new ArrayAdapter<>(getApplication(), R.layout.row_spinner_item, idNomEmpleado));
        spinner_idEmpleado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] parts = parent.getItemAtPosition(position).toString().split("-");
                idEmpleado = parts[0];
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
                //String monto = et_monto.getText().toString();
                String monto = et_monto.getText().toString().replaceAll(Constants.PAYMENT_NUMBER_FORMAT_REGEX_POINT, Constants.STRING_EMPTY);


                if (fecha.equals("") || monto.equals("") || idRuta.equals("") || idEmpleado.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    montoActual_Gasto = caja.get(0).getMonto() - Double.parseDouble(monto);
                    if (montoActual_Gasto < 0) {
                        Snackbar.make(v, getResources().getString(R.string.msg_monto_mayor_a_caja), Snackbar.LENGTH_LONG).show();
                    } else {
                        ModelGastos gastos = new ModelGastos(Integer.parseInt(idRuta), Double.parseDouble(monto), fecha, Integer.parseInt(idEmpleado));
                        String strJSON = gastos.toJsonAddGasto();
                        new AddGastoTask().execute(URL_ADD_GASTO, strJSON);
                    }
                }
            }

            TextWatcher textWatcherMontoCaja = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    et_monto.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll(Constants.PAYMENT_NUMBER_FORMAT_REGEX, Constants.STRING_EMPTY);
                    double parsed = Utils.convertToDouble(cleanString);
                    String formated = Utils.parseToString((parsed / 100));

                    et_monto.setText(formated);
                    Selection.setSelection(et_monto.getEditableText(),et_monto.getEditableText().length());

                    et_monto.addTextChangedListener(textWatcherMontoCaja);
                }
            };
        });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
    }

    public void createDialogUpdateCaja() {
        if (dialog != null){
            dialog.cancel();
        }
        idRuta = "";
        idEmpleado = "";
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_update_caja, null);
        builder.setView(view);

        Log.d("CAJA--", String.valueOf(caja.get(0).getMonto()));
        Button btn_guardar = (Button) view.findViewById(R.id.btn_caja_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_caja_cancelar);
        et_monto = (EditText) view.findViewById(R.id.caja_et_monto);
        et_monto.addTextChangedListener(textWatcherMontoCaja);

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monto = et_monto.getText().toString().replaceAll(Constants.PAYMENT_NUMBER_FORMAT_REGEX_POINT, Constants.STRING_EMPTY);
                if (monto.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    double montoActual = caja.get(0).getMonto();
                    Log.d("CAJA--", String.valueOf(montoActual));
                    double suma = montoActual + Double.parseDouble(monto);
                    ModelCaja modelCaja = new ModelCaja(suma);
                    String strJSON = modelCaja.toJsonUpdateCaja();
                    new UpdateCajaTask().execute(URL_UPDATE_CAJA, strJSON);
                }
            }
        });



        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
    }

    public void createDialogNewHonorario() {
        if (dialog != null) {
            dialog.cancel();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_honorario, null);
        builder.setView(view);

        idStatus = "";
        idMes = "";
        idCliente = "";
        ano = "";

        Spinner spinner_nombCliente = (Spinner) view.findViewById(R.id.cliente_nombre);
        Spinner spinner_mes = (Spinner) view.findViewById(R.id.h_mes);
        Spinner spinner_ano = (Spinner) view.findViewById(R.id.h_ano);
        Spinner spinner_status = (Spinner) view.findViewById(R.id.h_status);
        et_monto = (EditText) view.findViewById(R.id.h_monto);
        et_monto.addTextChangedListener(textWatcherMontoCaja);
        Button btn_agregar = (Button) view.findViewById(R.id.btn_honorario_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_honorario_cancelar);

        String[] arrayMes = getResources().getStringArray(R.array.meses);
        spinner_mes.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,arrayMes));

        Calendar calendarAno = Calendar.getInstance();
        String anoActual = String.valueOf(calendarAno.get(Calendar.YEAR));
        int iAnoAtual = Integer.parseInt(anoActual);
        int iMinAno = iAnoAtual - 5;
        String[] arrayAno = {String.valueOf(iAnoAtual), String.valueOf(iMinAno + 4), String.valueOf(iMinAno + 3), String.valueOf(iMinAno + 2), String.valueOf(iMinAno + 1)};
        spinner_ano.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,arrayAno));
        String[] cliente = new String[listClientes.size()];
        for (int x = 0; x < listClientes.size(); x++){
            cliente[x] = listClientes.get(x).getId_cliente() + "-" + listClientes.get(x).getNombre();
        }
        spinner_nombCliente.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,cliente));
        String[] status = {"1-Pagado","2-No Pagado","3-Abono"};
        spinner_status.setAdapter(new ArrayAdapter<>(getApplication(),R.layout.row_spinner_item,status));
        spinner_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] parts = parent.getItemAtPosition(position).toString().split("-");
                idStatus = parts[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_mes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] parts = parent.getItemAtPosition(position).toString().split("-");
                idMes = parts[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_nombCliente.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] parts = parent.getItemAtPosition(position).toString().split("-");
                idCliente = parts[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_ano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               ano = parent.getItemAtPosition(position).toString();
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

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String monto = et_monto.getText().toString().replaceAll(Constants.PAYMENT_NUMBER_FORMAT_REGEX_POINT, Constants.STRING_EMPTY);

                if (monto.equals("")){
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();
                } else {
                    ModelDespacho_Honorarios honorarios = new ModelDespacho_Honorarios(Integer.parseInt(idMes),ano,Integer.parseInt(idStatus),Integer.parseInt(idCliente),Double.parseDouble(monto));
                    String strJSON = honorarios.toJSONAddHono();
                    new AddHonorarioTask().execute(URL_ADD_HONORARIO,strJSON);
                }
            }
        });

        dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
    }

    TextWatcher textWatcherMontoCaja = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            et_monto.removeTextChangedListener(textWatcherMontoCaja);
            String cleanString = s.toString().replaceAll(Constants.PAYMENT_NUMBER_FORMAT_REGEX, Constants.STRING_EMPTY);
            double parsed = Utils.convertToDouble(cleanString);
            String formated = Utils.parseToString((parsed / 100));

            et_monto.setText(formated);
            Selection.setSelection(et_monto.getEditableText(),et_monto.getEditableText().length());

            et_monto.addTextChangedListener(textWatcherMontoCaja);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (data != null) {

                final String sData = data.getStringExtra(Constants.REFRESH);
                if (sData.equals(Constants.REFRESH_FRAGMENT_VEHICULO)) {
                    changeFragment(VehiculoFragment.newInstance(), R.id.mainFrame, false, false);
                } else if (sData.equals(Constants.REFRESH_FRAGMENT_EMPLEADO)) {
                    changeFragment(EmpleadoFragment.newInstance(), R.id.mainFrame, false, false);
                } else if (sData.equals(Constants.REFRESH_FRAGMENT_MANTENIMIENTO)) {
                    changeFragment(MantenimientoVehiculoFragment.newInstance(), R.id.mainFrame, false, false);
                } else if (sData.equals(Constants.REFRESH_FRAGMENT_INGRESO)) {
                    changeFragment(IngresosFragment.newInstance(), R.id.mainFrame, false, false);
                } else if (sData.equals(Constants.REFRESH_FRAGMENT_COMPROBANTE)){
                    changeFragment(ComprobanteFragment.newInstance(), R.id.mainFrame, false, false);
                }

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
                    } catch (FileNotFoundException e) {
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
                    } catch (FileNotFoundException e) {
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

    private class AddRutaTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_RUTA, baseUrl, jsonData);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            proccessResult(result);
            changeFragment(RutasFragment.newInstance(), R.id.mainFrame, false, false);
            progressBar.cancel();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    private class AddTiendaTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_TIENDA, baseUrl, jsonData);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            proccessResult(result);
            changeFragment(TiendasFragment.newInstance(), R.id.mainFrame, false, false);
            progressBar.cancel();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    private class AddClienteTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
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
            changeFragment(ClientesDespachoFragment.newInstance(), R.id.mainFrame, false, false);
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
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
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
            new QueryCajaTask().execute(URL_QUERY_CAJA);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

    private class AddGastoTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_GASTO, baseUrl, jsonData);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            proccessResult(result);
            //  changeFragment(TiendasFragment.newInstance(),R.id.mainFrame,false,false);
            progressBar.cancel();
            new QueryCajaTask().execute(URL_QUERY_CAJA);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

        }
    }

    private class AddHonorarioTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_HONORARIOS, baseUrl, jsonData);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            proccessResult(result);
            progressBar.cancel();
        }

    }


    private class UpdateCajaTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            String baseUrl = params[0];
            String jsonData = params[1];
            return UtilsDML.addData(Constants.POST_CAJA, baseUrl, jsonData);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            proccessResult(result);
            //  changeFragment(TiendasFragment.newInstance(),R.id.mainFrame,false,false);
            progressBar.cancel();
            new QueryCajaTask().execute(URL_QUERY_CAJA);
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
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
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

    private class QueryRutaTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
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
            UtilsDML.resultQueryRutas(result, listRutas);
            // setUpRecyclerView();
            progressBar.cancel();
        }

    }

    public class QueryEmpleadoTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
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
            progressBar.cancel();
        }
    }

    private class QueryCajaTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
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
            caja = new ArrayList<>();
            UtilsDML.resultQueryCaja(result, caja);
            // setUpRecyclerView();
            progressBar.cancel();
            montoActual_Gasto = caja.get(0).getMonto();
        }

    }

    private class QueryClienteTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(progressBar.isShowing()) {
                progressBar.cancel();
            } else {
                progressBar.show();
            }
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
            UtilsDML.resultQueryCliente(result,listClientes);
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
