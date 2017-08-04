package android.despacho.com.ofinicaerp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.models.ModelMantenimiento;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.despacho.com.ofinicaerp.activity.MenuPrincipal.listVehiculos;
import static android.despacho.com.ofinicaerp.utils.Constants.POST_FECHA;
import static android.despacho.com.ofinicaerp.utils.Constants.POST_MANTENIMIENTO;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_GASTO_GASOLINA;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_MANTENIMIENTO;

public class MantenimientoVehiculoFragment extends Fragment {

    private Spinner spinner_vehiculo;
    private EditText et_fechaIni;
    private EditText et_fechaFin;
    private Button btn_buscar;
    private TextView tv_total;
    private RecyclerView recyclerView;
    private String idVehiculo;
    private ProgressDialog progressBar;
    private List<ModelMantenimiento> listMantenimiento;

    public MantenimientoVehiculoFragment() {
        // Required empty public constructor
    }

    public static MantenimientoVehiculoFragment newInstance() {
        MantenimientoVehiculoFragment fragment = new MantenimientoVehiculoFragment();
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
        return inflater.inflate(R.layout.fragment_mantenimiento_vehiculo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init(){
        listMantenimiento = new ArrayList<>();
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        idVehiculo = "";
        spinner_vehiculo = (Spinner) getActivity().findViewById(R.id.spinner_mantenimiento);
        et_fechaIni = (EditText) getActivity().findViewById(R.id.et_fechaIni);
        et_fechaFin = (EditText) getActivity().findViewById(R.id.et_fechaFin);
        btn_buscar = (Button)getActivity().findViewById(R.id.btn_buscar);
        tv_total = (TextView) getActivity().findViewById(R.id.labelTotal_mantenimiento);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_mantenimiento);
        int sizeListVehiculos = listVehiculos.size() + 1;
        final String[] idNomVehiculo = new String[sizeListVehiculos];
        idNomVehiculo[0] = "10000-TODOS";
       // int x = 0;
        for (int i = 1, x = 0; i < sizeListVehiculos; i++, x++) {
            idNomVehiculo[i] = listVehiculos.get(x).getId_vehiculo() + "-" + listVehiculos.get(x).getNombre();
        }

        spinner_vehiculo.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.row_spinner_item, idNomVehiculo));
        spinner_vehiculo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] parts = parent.getItemAtPosition(position).toString().split("-");

                idVehiculo = parts[0];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        et_fechaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogDate(getContext(),et_fechaIni);
                if (!et_fechaIni.getText().toString().equals("") || !et_fechaFin.getText().toString().equals("")){
                    btn_buscar.setEnabled(true);
                    btn_buscar.setAlpha(1.0f);
                }
            }
        });

        et_fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogDate(getContext(),et_fechaFin);
                if (!et_fechaIni.getText().toString().equals("") || !et_fechaFin.getText().toString().equals("")){
                    btn_buscar.setEnabled(true);
                    btn_buscar.setAlpha(1.0f);
                }
            }
        });

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testAdapter != null){
                    testAdapter.removeAll();
                }

                if (et_fechaIni.getText().toString().equals("") || et_fechaFin.getText().toString().equals("") || idVehiculo.equals("")){
                    Toast.makeText(getActivity(),getString(R.string.msg_fecha),Toast.LENGTH_LONG).show();
                } else {
                    String fInicial = et_fechaIni.getText().toString();
                    String fFinal = et_fechaFin.getText().toString();
                    String strJSON = Utils.toJsonMantenimientoFecha(idVehiculo,fInicial,fFinal);
                    // Log.i("JSSON--",strJSON);
                    new QueryMantenimientoTask().execute(POST_MANTENIMIENTO,URL_QUERY_MANTENIMIENTO,strJSON);

                }
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



    private static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView et_fecha;
        TextView et_carro;
        TextView et_mantenimiento;
        TextView et_monto;
        View mView;

        TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycler_mantenimiento, parent, false));

            et_fecha = (TextView) itemView.findViewById(R.id.row_mantenimiento_fecha);
            et_carro = (TextView) itemView.findViewById(R.id.row_mantenimiento_carro);
            et_mantenimiento = (TextView) itemView.findViewById(R.id.row_mantenimiento_mant);
            et_monto = (TextView) itemView.findViewById(R.id.row_mantenimiento_monto);
            mView = itemView;
        }
    }


    private class QueryMantenimientoTask extends AsyncTask<String, Integer, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return UtilsDML.addData(params[0],params[1],params[2]);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            UtilsDML.resultQueryMantenimiento(getActivity().getApplication(),result,listMantenimiento);
            setUpRecyclerView();
            progressBar.cancel();
        }
    }
}
