package android.despacho.com.ofinicaerp.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.activity.MenuPrincipal;
import android.despacho.com.ofinicaerp.models.ModelGastos;
import android.despacho.com.ofinicaerp.models.ModelGastosGasolina;
import android.despacho.com.ofinicaerp.models.ModelIngresos;
import android.despacho.com.ofinicaerp.models.ModelMantenimiento;
import android.despacho.com.ofinicaerp.models.ModelPagoEmpleado;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.despacho.com.ofinicaerp.utils.Constants.ADMIN;
import static android.despacho.com.ofinicaerp.utils.Constants.POST_FECHA;
import static android.despacho.com.ofinicaerp.utils.Constants.POST_GASTO;
import static android.despacho.com.ofinicaerp.utils.Constants.POST_INGRESO;
import static android.despacho.com.ofinicaerp.utils.Constants.POST_MANTENIMIENTO;
import static android.despacho.com.ofinicaerp.utils.Constants.PUTEXTRA_TIPO_USER;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_GASTOS;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_GASTO_GASOLINA;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_INGRESO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_MANTENIMIENTO;


public class HomeFragment extends Fragment {

    private ProgressDialog progressBar;
    private List<ModelGastos> listGastos;
    private List<ModelGastosGasolina> listGasolina;
    private List<ModelMantenimiento> listMantenimiento;
    private List<ModelPagoEmpleado> nominaList;
    private List<ModelIngresos> listIngresos;

    private TextView tv_ingresos;
    private TextView tv_gastosTotales;
    private TextView tv_otrosGastos;
    private TextView tv_mantenimiento;
    private TextView tv_gasolina;
    private TextView tv_nomina;
    private TextView tv_mes;
    private CardView cardViewTotales;
    private CardView cardViewGastos;

    private double iOtrosGastos;
    private double iMantenimiento;
    private double iGasolina;
    private double iNomina;
    private double iIngresos;
    private double iGatosTotales;

    private static String ALL = "100000";

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        //   Bundle args = new Bundle();
        //   args.putString(ARG_PARAM1, param1);
        //   args.putString(ARG_PARAM2, param2);
        //   fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init() {
        iGasolina = 0;
        iGatosTotales = 0;
        iMantenimiento = 0;
        iNomina = 0;
        iOtrosGastos = 0;
        iIngresos = 0;
        cardViewGastos = (CardView) getActivity().findViewById(R.id.cardView3);
        cardViewTotales = (CardView) getActivity().findViewById(R.id.cardView2);
        tv_mes = (TextView) getActivity().findViewById(R.id.tv_mes);
        tv_ingresos = (TextView) getActivity().findViewById(R.id.tv_ingresos_totales);
        tv_gastosTotales = (TextView) getActivity().findViewById(R.id.tv_gastos_totales);
        tv_otrosGastos = (TextView) getActivity().findViewById(R.id.tv_gasto);
        tv_gasolina = (TextView) getActivity().findViewById(R.id.tv_gasolina);
        tv_mantenimiento = (TextView) getActivity().findViewById(R.id.tv_mantenimiento);
        tv_nomina = (TextView) getActivity().findViewById(R.id.tv_nomina);
        listIngresos = new ArrayList<>();
        nominaList = new ArrayList<>();
        listMantenimiento = new ArrayList<>();
        listGasolina = new ArrayList<>();
        listGastos = new ArrayList<>();
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);


        String typeUser = getActivity().getIntent().getStringExtra(PUTEXTRA_TIPO_USER);
        if (!typeUser.equals(ADMIN)) {
            cardViewTotales.setVisibility(View.INVISIBLE);
            cardViewGastos.setVisibility(View.INVISIBLE);
        }
        Calendar calendar = Calendar.getInstance();
        String anoActual = String.valueOf(calendar.get(Calendar.YEAR));
        String mesActual = String.valueOf(calendar.get(Calendar.MONTH) + 1);
        String diaActual = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
        if (Integer.parseInt(mesActual) <= 9) {
            mesActual = "0" + mesActual;
        }
        if (Integer.parseInt(diaActual) <= 9) {
            diaActual = "0" + diaActual;
        }

        switch (calendar.get(Calendar.MONTH) + 1){
            case 1:
                tv_mes.setText("ENERO");
                break;
            case 2:
                tv_mes.setText("FEBRERO");
                break;
            case 3:
                tv_mes.setText("MARZO");
                break;
            case 4:
                tv_mes.setText("ABRIL");
                break;
            case 5:
                tv_mes.setText("MAYO");
                break;
            case 6:
                tv_mes.setText("JUNIO");
                break;
            case 7:
                tv_mes.setText("JULIO");
                break;
            case 8:
                tv_mes.setText("AGOSTO");
                break;
            case 9:
                tv_mes.setText("SEPTIEMBRE");
                break;
            case 10:
                tv_mes.setText("OCTUBRE");
                break;
            case 11:
                tv_mes.setText("NOVIEMBRE");
                break;
            case 12:
                tv_mes.setText("DICIEMBRE");
                break;
        }

        String fechaInicial = anoActual + "-" + mesActual + "-" + "01";
        String fechaActual = anoActual + "-" + mesActual + "-" + diaActual;

        Activity activity = getActivity();
        if (null != activity) {
            String strJSON = Utils.toJsonIngresoFecha(ALL, fechaInicial, fechaActual);
            new QueryGastosTask().execute(POST_GASTO, URL_QUERY_GASTOS, strJSON);

            String strJSONGasolina = Utils.toJsonRangoFecha(fechaInicial, fechaActual);
            new QueryGatoGasolinaTask().execute(POST_FECHA, URL_QUERY_GASTO_GASOLINA, strJSONGasolina);

            String strJSONManteni = Utils.toJsonMantenimientoFecha(ALL, fechaInicial, fechaActual);
            new QueryMantenimientoTask().execute(POST_MANTENIMIENTO, URL_QUERY_MANTENIMIENTO, strJSONManteni);

            ModelPagoEmpleado modelNomina = new ModelPagoEmpleado(calendar.get(Calendar.MONTH) + 1, anoActual);
            String strJSONNomina = modelNomina.toJSONQueryNomina();
            new nominaTask().execute(Constants.URL_QUERY_NOMINA, strJSONNomina);

            String strJSONIngreso = Utils.toJsonIngresoFecha(ALL, fechaInicial, fechaActual);
            new QueryIngresoTask().execute(POST_INGRESO, URL_QUERY_INGRESO, strJSONIngreso);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        progressBar.cancel();
    }

    private class QueryGastosTask extends AsyncTask<String, Integer, String> {
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
            return UtilsDML.addData(params[0], params[1], params[2]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("OTROS GASTOS--",result);
            UtilsDML.resultQueryGastos(getContext(), result, listGastos);
            for (int x = 0 ; x < listGastos.size() ; x++ ){
                iOtrosGastos += listGastos.get(x).getMonto();
            }
            iGatosTotales += iOtrosGastos;
            tv_gastosTotales.setText(MenuPrincipal.getInstance().getString(R.string.pesoscaja, Utils.parseToString(iGatosTotales)));
            tv_otrosGastos.setText(MenuPrincipal.getInstance().getString(R.string.pesoscaja, Utils.parseToString(iOtrosGastos)));
            progressBar.cancel();
        }
    }

    private class QueryGatoGasolinaTask extends AsyncTask<String, Integer, String> {
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
            return UtilsDML.addData(params[0], params[1], params[2]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            UtilsDML.resultQueryGasolina(getActivity().getApplication(), result, listGasolina);
            for (int x = 0 ; x < listGasolina.size() ; x++ ){
                iGasolina += listGasolina.get(x).getCosto();
            }
            iGatosTotales += iGasolina;
            tv_gastosTotales.setText(getString(R.string.pesoscaja, Utils.parseToString(iGatosTotales)));
            tv_gasolina.setText(getString(R.string.pesoscaja, Utils.parseToString(iGasolina)));
            progressBar.cancel();
        }
    }

    private class QueryMantenimientoTask extends AsyncTask<String, Integer, String> {
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
            return UtilsDML.addData(params[0], params[1], params[2]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            UtilsDML.resultQueryMantenimiento(getActivity().getApplication(), result, listMantenimiento);
            for (int x = 0 ; x < listMantenimiento.size() ; x++ ){
                iMantenimiento += listMantenimiento.get(x).getCosto();
            }
            iGatosTotales += iMantenimiento;
            tv_gastosTotales.setText(getString(R.string.pesoscaja, Utils.parseToString(iGatosTotales)));
            tv_mantenimiento.setText(getString(R.string.pesoscaja, Utils.parseToString(iMantenimiento)));
            progressBar.cancel();
        }
    }

    private class nominaTask extends AsyncTask<String, Integer, String> {
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
            return UtilsDML.addData(Constants.POST_NOMINA, params[0], params[1]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("NOMINA--",result);
            UtilsDML.resultQueryNomina(getActivity().getApplication(), result, nominaList);
            for (int x = 0 ; x < nominaList.size() ; x++ ){
                iNomina += nominaList.get(x).getMonto();
            }
            iGatosTotales += iNomina;
            tv_gastosTotales.setText(getString(R.string.pesoscaja, Utils.parseToString(iGatosTotales)));
            tv_nomina.setText(getString(R.string.pesoscaja, Utils.parseToString(iNomina)));
            progressBar.cancel();
        }
    }

    private class QueryIngresoTask extends AsyncTask<String, Integer, String> {
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
            return UtilsDML.addData(params[0], params[1], params[2]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("ING--",result);
            UtilsDML.resultQueryIngreso(getActivity().getApplication(), result, listIngresos);
            for (int x = 0 ; x < listIngresos.size() ; x++ ){
                iIngresos += listIngresos.get(x).getCantidad();
            }
            tv_ingresos.setText(getString(R.string.pesoscaja, Utils.parseToString(iIngresos)));
            progressBar.cancel();
        }
    }
}
