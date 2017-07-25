package android.despacho.com.ofinicaerp.activity;

import android.content.Intent;
import android.despacho.com.ofinicaerp.R;
import android.despacho.com.ofinicaerp.fragments.HomeFragment;
import android.despacho.com.ofinicaerp.fragments.RutasFragment;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.ADMIN;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_ID_EMPLEADO;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_TIPO_USER;

public class MenuPrincipal extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private FloatingActionButton fab;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        init();


    }

    public void init() {
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
        String idUser = getIntent().getStringExtra(PUTEXTRA_ID_EMPLEADO);

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
                break;

            case R.id._nav_pago_nomina:
                break;

            case R.id._nav_registro_vehiculo:
                break;

            case R.id._nav_registro_mantenimiento:
                break;

            case R.id._nav_gasto_gasolina:
                break;

            case R.id._nav_clientes:
                Toast.makeText(this, "Clientes", Toast.LENGTH_LONG).show();
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
            Snackbar.make(v,"HomeFragment", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    };

    View.OnClickListener onClickRutas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Snackbar.make(v,"RutasFragment", BaseTransientBottomBar.LENGTH_LONG).show();
        }
    };

    View.OnClickListener onClickVehiculo = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public void createNewProductDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MenuPrincipal.this);
        LayoutInflater inflater = MenuPrincipal.this.getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_add_vehiculo, null);

        builder.setView(view);


        CircleImageView photoCar = (CircleImageView) view.findViewById(R.id.vehiculo_photo);
        Button btn_guardar = (Button) view.findViewById(R.id.btn_vehiculo_guardar);
        Button btn_cancelar = (Button) view.findViewById(R.id.btn_vehiculo_cancelar);
        final EditText et_nombre = (EditText) view.findViewById(R.id.vehiculo_nombre);
        final EditText et_modelo = (EditText) view.findViewById(R.id.vehiculo_ano);
        final EditText et_marca = (EditText) view.findViewById(R.id.vehiculo_marca);
        final EditText et_serie = (EditText) view.findViewById(R.id.vehiculo_serie);
        final EditText et_placas = (EditText) view.findViewById(R.id.vehiculo_placas);
        final EditText et_color = (EditText) view.findViewById(R.id.vehiculo_color);
        final Spinner spinnerEmpleado = (Spinner) view.findViewById(R.id.vehiculo_spinner);
        dialog = builder.create();

        photoCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                if (nombre.equals("") && modelo.equals("") && marca.equals("") && serie.equals("") &&
                        placas.equals("") && color.equals("")) {
                    Snackbar.make(v, getResources().getString(R.string.msg_campos_vacios), Snackbar.LENGTH_LONG).show();

                } else {

                }


            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();

    }

}
