package android.despacho.com.ofinicaerp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.despacho.com.ofinicaerp.activity.GasolinaDetails;
import android.despacho.com.ofinicaerp.activity.MantenimientoDetails;
import android.despacho.com.ofinicaerp.models.ModelGastosGasolina;
import android.despacho.com.ofinicaerp.models.ModelMantenimiento;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
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
    private MantenimientoAdapter testAdapter;
    private String idVehiculo;
    private ProgressDialog progressBar;
    public static List<ModelMantenimiento> listMantenimiento;
    private double total;
    private String sIndex;

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
        idNomVehiculo[0] = "100000-TODOS";
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
                    total = 0;
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

    public void setUpRecyclerView(){
        testAdapter = new MantenimientoAdapter(listMantenimiento,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(testAdapter);
        recyclerView.setHasFixedSize(true);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class MantenimientoAdapter extends RecyclerView.Adapter {
        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
        List<ModelMantenimiento> items;
        Context mContext;
        List<ModelMantenimiento> itemsPendingRemoval;
        int lastInsertedIndex; // so we can add some more items for testing purposes
        boolean undoOn; // is undo on, you can turn it on from the toolbar menu

        private Handler handler = new Handler(); // hanlder for running delayed runnables

        MantenimientoAdapter(List<ModelMantenimiento> item, Context context) {
            items = item;
            mContext = context;
            itemsPendingRemoval = item;
            // let's generate some items
            lastInsertedIndex = 15;

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TestViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            TestViewHolder viewHolder = (TestViewHolder) holder;
            final ModelMantenimiento item = items.get(position);
            total += item.getCosto();
            tv_total.setText(getString(R.string.total,String.valueOf(total)));
            viewHolder.et_fecha.setText(item.getFecha());
            viewHolder.et_carro.setText(item.getVehiculo());
            viewHolder.et_mantenimiento.setText(item.getMantenimiento());
            viewHolder.et_monto.setText(getString(R.string.monto,String.valueOf(item.getCosto())));
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sIndex = String.valueOf(holder.getLayoutPosition());
                    Intent intent = new Intent(getActivity(),MantenimientoDetails.class);
                    intent.putExtra("INDEX",sIndex);
                    intent.putExtra("MAX_INDEX",String.valueOf(listMantenimiento.size()));
                    startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        private void setUndoOn(boolean undoOn) {
            this.undoOn = undoOn;
        }

        public boolean isUndoOn() {
            return undoOn;
        }

        public void removeAll(){
            items.removeAll(itemsPendingRemoval);
            notifyDataSetChanged();
        }
        public void remove(int position) {
            ModelMantenimiento item = items.get(position);
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item);
            }
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

        public boolean isPendingRemoval(int position) {
            ModelMantenimiento item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }
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
