package android.despacho.com.ofinicaerp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.activity.MenuPrincipal;
import android.despacho.com.ofinicaerp.models.ModelComprobanteGastos;
import android.despacho.com.ofinicaerp.models.ModelGastos;
import android.despacho.com.ofinicaerp.models.ModelTipoGasto;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ComprobanteFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{

    private ProgressDialog progressBar;
    private EditText et_fechaFin;
    private EditText et_fechaIni;
    private RadioGroup radioGroup;
    private RadioButton radioButton_si, radioButton_no, radioButton_todos;
    private Spinner spinner_tipo_comprobante, spinner_categoria;
    private RecyclerView recyclerView;
    private List<ModelComprobanteGastos>comprobanteGastosList;
    private TextView tv_total;
    private double total;
    private String sIndex;
    private ComprobanteAdapter testAdapter;
    private List<ModelTipoGasto> listTipoGasto;
    private String idCategoria;
    private Button btn_buscar;
    private String tipoComprobante;
    private int iGastoOxxo;


    public ComprobanteFragment() {
        // Required empty public constructor
    }

    public static ComprobanteFragment newInstance() {
        ComprobanteFragment fragment = new ComprobanteFragment();
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
        return inflater.inflate(R.layout.fragment_comprobante, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init(){
        listTipoGasto = new ArrayList<>();
        comprobanteGastosList = new ArrayList<>();
        et_fechaFin = (EditText) getActivity().findViewById(R.id.et_fechaFin);
        et_fechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogDate(getActivity(),et_fechaFin);
                enabledButton();
            }
        });
        et_fechaIni = (EditText) getActivity().findViewById(R.id.et_fechaIni);
        et_fechaIni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showDialogDate(getActivity(),et_fechaIni);
                enabledButton();
            }
        });
        radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
        radioButton_todos = (RadioButton) getActivity().findViewById(R.id.radioButton_todos);
        radioButton_si = (RadioButton) getActivity().findViewById(R.id.radioButton_si);
        radioButton_no = (RadioButton) getActivity().findViewById(R.id.radioButton_no);
        spinner_tipo_comprobante = (Spinner) getActivity().findViewById(R.id.spinner_tipo_comprobante);
        spinner_categoria = (Spinner) getActivity().findViewById(R.id.spinner_categoria);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_comprobantes);
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        tv_total = (TextView) getActivity().findViewById(R.id.labelTotal_comprobante);
        btn_buscar = (Button) getActivity().findViewById(R.id.btn_buscar);
        new QueryTipoGastoTask().execute(Constants.URL_QUERY_TIPO_GASTO);

        radioButton_todos.setChecked(true);
        iGastoOxxo = 3;
        String[] arrayTipoComprobante = getResources().getStringArray(R.array.array_query_tipo_comprobante);

        spinner_tipo_comprobante.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.row_spinner_item,arrayTipoComprobante));
        spinner_tipo_comprobante.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tipoComprobante = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                int size = listTipoGasto.size() + 1;
                final String[] idNomTipoGasto = new String[size];
                idNomTipoGasto[0] = "100000-TODAS";
                for (int i = 1, x  = 0; i < size; i++, x++) {
                    idNomTipoGasto[i] = listTipoGasto.get(x).getId_tipoGasto() + "-" + listTipoGasto.get(x).getTipoGasto();
                }
                spinner_categoria.setAdapter(new ArrayAdapter<>(getActivity(),R.layout.row_spinner_item,idNomTipoGasto));

                spinner_categoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String[] parts = parent.getItemAtPosition(position).toString().split("-");
                        idCategoria = parts[1];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        };
        Handler myHandler = new Handler();
        myHandler.postDelayed(runnable,1500);

        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (testAdapter != null){
                    testAdapter.removeAll();
                    total = 0;
                    tv_total.setText(getString(R.string.total,String.valueOf(total)));
                }
                String fechaIni = et_fechaIni.getText().toString();
                String fechaFin = et_fechaFin.getText().toString();
                if (idCategoria.equals("") || tipoComprobante.equals("") || fechaIni.equals("") || fechaFin.equals("")){
                    Toast.makeText(getActivity(),getString(R.string.msg_campos_vacios),Toast.LENGTH_LONG).show();
                } else {
                    String strJSON = Utils.toJsonComprobante(idCategoria,tipoComprobante,iGastoOxxo,fechaIni,fechaFin);
                    new QueryComprobanteTask().execute(Constants.POST_COMPROBANTE,Constants.URL_QUERY_COMPROBANTES_GASTOS,strJSON);
                }

            }
        });

    }

    public void setUpRecyclerView(){
        testAdapter = new ComprobanteAdapter(comprobanteGastosList,getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(testAdapter);
        recyclerView.setHasFixedSize(true);
    }

    public void enabledButton(){
        if (!et_fechaIni.getText().toString().equals("") || !et_fechaFin.getText().toString().equals("")){
            btn_buscar.setEnabled(true);
            btn_buscar.setAlpha(1.0f);
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
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId){

            case R.id.radioButton_si:
                iGastoOxxo = 1;
                Log.d("OXXO--",""+iGastoOxxo);
                break;

            case R.id.radioButton_no:
                iGastoOxxo = 2;
                Log.d("OXXO--",""+iGastoOxxo);
                break;

            case R.id.radioButton_todos:
                iGastoOxxo = 3;
                Log.d("OXXO--",""+iGastoOxxo);
                break;
        }
    }

    public class ComprobanteAdapter extends RecyclerView.Adapter {
        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
        List<ModelComprobanteGastos> items;
        Context mContext;
        List<ModelComprobanteGastos> itemsPendingRemoval;
        int lastInsertedIndex; // so we can add some more items for testing purposes
        boolean undoOn; // is undo on, you can turn it on from the toolbar menu

        private Handler handler = new Handler(); // hanlder for running delayed runnables

        ComprobanteAdapter(List<ModelComprobanteGastos> item, Context context) {
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
            final ModelComprobanteGastos item = items.get(position);
            viewHolder.et_fecha.setText(item.getFecha());
            viewHolder.et_concepto.setText(item.getConcepto());
            viewHolder.et_monto.setText(getString(R.string.monto,Utils.parseToString(item.getMonto())));
            viewHolder.et_categoria.setText(item.getCategoria());
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sIndex = String.valueOf(holder.getLayoutPosition());
                   /* Intent intent = new Intent(getActivity(),IngresosDetails.class);
                    intent.putExtra("INDEX",sIndex);
                    intent.putExtra("MAX_INDEX",String.valueOf(listIngresos.size()));
                    startActivity(intent);*/
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
            ModelComprobanteGastos item = items.get(position);
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item);
            }
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

        public boolean isPendingRemoval(int position) {
            ModelComprobanteGastos item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView et_fecha;
        TextView et_concepto;
        TextView et_monto;
        TextView et_categoria;
        View mView;

        TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recyclerview_comprobante, parent, false));
            et_fecha = (TextView) itemView.findViewById(R.id.row_comprobante_fecha);
            et_concepto = (TextView) itemView.findViewById(R.id.row_comprobante_concepto);
            et_monto = (TextView) itemView.findViewById(R.id.row_comprobante_monto);
            et_categoria = (TextView) itemView.findViewById(R.id.row_comprobante_categoria);
            mView = itemView;
        }
    }

    private class QueryTipoGastoTask extends AsyncTask<String, Integer, String> {
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
            UtilsDML.resultQueryTipoGasto(result, listTipoGasto);
            // setUpRecyclerView();
            progressBar.cancel();
        }

    }

    private class QueryComprobanteTask extends AsyncTask<String, Integer, String> {
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
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            UtilsDML.resultQueryComprobantes(getActivity().getApplication(),result,comprobanteGastosList);
            setUpRecyclerView();
            total = 0.0;
            for(int y = 0; y < comprobanteGastosList.size(); y++){
                total += comprobanteGastosList.get(y).getMonto();
            }
            tv_total.setText(getString(R.string.monto,Utils.parseToString(total)));
            progressBar.cancel();
        }
    }

}
