package android.despacho.com.ofinicaerp.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.despacho.com.ofinicaerp.activity.MenuPrincipal;
import android.despacho.com.ofinicaerp.models.ModelDespacho_Clientes;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.URL_ADD_CLIENTE;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_CLIENTE;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_UPDATE_CLIENTE;


public class ClientesDespachoFragment extends Fragment {

    public RecyclerView recyclerView;
    private ClienteAdapter testAdapter;
    private List<ModelDespacho_Clientes> listClientes;
    private ProgressDialog progressBar;
    private AlertDialog dialog;


    public ClientesDespachoFragment() {
        // Required empty public constructor
    }

    public static ClientesDespachoFragment newInstance() {
        ClientesDespachoFragment fragment = new ClientesDespachoFragment();
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
        View view = inflater.inflate(R.layout.fragment_clientes_despacho, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init(){

        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        listClientes = new ArrayList<>();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_clientes);
        new QueryClienteTask().execute(URL_QUERY_CLIENTE);
    }

    public void setUpRecyclerView() {
        testAdapter = new ClienteAdapter(listClientes,getActivity());
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

    public class ClienteAdapter extends RecyclerView.Adapter {
        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
        List<ModelDespacho_Clientes> items;
        Context mContext;
        List<ModelDespacho_Clientes> itemsPendingRemoval;
        int lastInsertedIndex; // so we can add some more items for testing purposes
        boolean undoOn; // is undo on, you can turn it on from the toolbar menu

        private Handler handler = new Handler(); // hanlder for running delayed runnables

        private ClienteAdapter(List<ModelDespacho_Clientes> item, Context context) {

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
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TestViewHolder viewHolder = (TestViewHolder) holder;
            final ModelDespacho_Clientes item = items.get(position);
            viewHolder.et_nombre.setText(item.getNombre());
            viewHolder.et_rfc.setText(item.getRfc());
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogUpdateCliente(item.getId_cliente(),item.getNombre(),item.getRfc(),
                            item.getCurp(),String.valueOf(item.getMonto_mensualidad()),item.getPass_sat(),item.getPass_fiel(),
                            item.getPass_certificado());
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



        public void remove(int position) {
            ModelDespacho_Clientes item = items.get(position);
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item);
            }
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

        public void removeAll(){
            items.removeAll(itemsPendingRemoval);
            notifyDataSetChanged();
        }

        public boolean isPendingRemoval(int position) {
            ModelDespacho_Clientes item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView et_nombre;
        TextView et_rfc;
        View mView;

        TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_cliente, parent, false));
            et_nombre = (TextView) itemView.findViewById(R.id.row_cliente_nombre);
            et_rfc = (TextView) itemView.findViewById(R.id.row_cliente_rfc);
            mView = itemView;
        }
    }

    private class QueryClienteTask extends AsyncTask<String, Integer, String> {
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
            UtilsDML.resultQueryCliente(result,listClientes);
            setUpRecyclerView();
            progressBar.cancel();
        }
    }

    public void createDialogUpdateCliente(final int idCliente, String nombre, String rfc, String curp,
                                       String honorario, String passSat, String passFiel, String passCertificado ) {
        if (dialog != null){
            dialog.cancel();
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
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
        TextView title = (TextView) view.findViewById(R.id.textView2);
        title.setText("Actualiza");

        et_nombre.setText(nombre);
        et_rfc.setText(rfc);
        et_curp.setText(curp);
        et_honorario.setText(honorario);
        et_passSat.setText(passSat);
        et_passFiel.setText(passFiel);
        et_passCertificado.setText(passCertificado);

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
                            idCliente,
                            nombre,
                            rfc,
                            curp,
                            et_passSat.getText().toString(),
                            et_passFiel.getText().toString(),
                            et_passCertificado.getText().toString(),
                            Double.parseDouble(honorario));

                    String strJson = cliente.toJsonUpdateCliente();
                    new UpdateClienteTask().execute(URL_UPDATE_CLIENTE, strJson);
                }

            }
        });

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        dialog.show();
    }

    private class UpdateClienteTask extends AsyncTask<String, Integer, String> {

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
            Utils.proccessResult(getActivity(),result);
            dialog.dismiss();
            progressBar.cancel();
            if (testAdapter != null){
                testAdapter.removeAll();
            }
            new QueryClienteTask().execute(URL_QUERY_CLIENTE);
        }


    }
}
