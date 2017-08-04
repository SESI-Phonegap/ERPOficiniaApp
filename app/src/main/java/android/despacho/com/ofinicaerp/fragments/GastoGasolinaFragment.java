package android.despacho.com.ofinicaerp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.despacho.com.ofinicaerp.activity.GasolinaDetails;
import android.despacho.com.ofinicaerp.activity.MenuPrincipal;
import android.despacho.com.ofinicaerp.models.ModelGastosGasolina;
import android.despacho.com.ofinicaerp.models.ModelVehiculo;
import android.despacho.com.ofinicaerp.utils.Utils;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.POST_FECHA;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_GASTO_GASOLINA;


public class GastoGasolinaFragment extends Fragment {

    private EditText et_fechaIni;
    private EditText et_fechaFin;
    public RecyclerView recyclerView;
    private GasolinaAdapter testAdapter;
    public static List<ModelGastosGasolina> listGasolina;
    private ProgressDialog progressBar;
    private Button btn_buscar;
    private TextView tv_total;
    private double total;
    private String sIndex;
    public GastoGasolinaFragment() {
        // Required empty public constructor
    }

    public static GastoGasolinaFragment newInstance() {
        GastoGasolinaFragment fragment = new GastoGasolinaFragment();;
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
        return inflater.inflate(R.layout.fragment_gasto_gasolina, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init(){
        sIndex = "0";
        tv_total = (TextView) getActivity().findViewById(R.id.labelTotal);
        total = 0.0;
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        btn_buscar = (Button) getActivity().findViewById(R.id.btn_buscar);
        et_fechaFin = (EditText) getActivity().findViewById(R.id.frag_gasolina_fechaFin);
        et_fechaIni = (EditText) getActivity().findViewById(R.id.frag_gasolina_fechaIni);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.frag_gasolina_recyclerView);
        listGasolina = new ArrayList<>();

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

                if (et_fechaIni.getText().toString().equals("") || et_fechaFin.getText().toString().equals("")){
                    Toast.makeText(getActivity(),getString(R.string.msg_fecha),Toast.LENGTH_LONG).show();
                } else {
                    String fInicial = et_fechaIni.getText().toString();
                    String fFinal = et_fechaFin.getText().toString();
                    String strJSON = Utils.toJsonRangoFecha(fInicial,fFinal);
                   // Log.i("JSSON--",strJSON);
                    new QueryGatoGasolinaTask().execute(POST_FECHA,URL_QUERY_GASTO_GASOLINA,strJSON);

                }
            }
        });
    }

    public void setUpRecyclerView() {
        testAdapter = new GasolinaAdapter(listGasolina, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(testAdapter);
        recyclerView.setHasFixedSize(true);
        //  ((TestAdapter) recyclerView.getAdapter()).setUndoOn(true);
        //  Utils.setUpItemTouchHelperLlamar(getActivity(),recyclerView,CONSULTORAS);
        //  Utils.setUpAnimationDecoratorHelperLlamar(getActivity(),recyclerView);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public class GasolinaAdapter extends RecyclerView.Adapter {
        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
        List<ModelGastosGasolina> items;
        Context mContext;
        List<ModelGastosGasolina> itemsPendingRemoval;
        int lastInsertedIndex; // so we can add some more items for testing purposes
        boolean undoOn; // is undo on, you can turn it on from the toolbar menu

        private Handler handler = new Handler(); // hanlder for running delayed runnables

        GasolinaAdapter(List<ModelGastosGasolina> item, Context context) {
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
            final ModelGastosGasolina item = items.get(position);
            total += item.getCosto();
            tv_total.setText(getString(R.string.total,String.valueOf(total)));
            viewHolder.et_fecha.setText(item.getFecha());
            viewHolder.et_carro.setText(item.getCarro());
            viewHolder.et_litros.setText(String.valueOf(item.getCantidad_litros()));
            viewHolder.et_monto.setText(getString(R.string.monto,String.valueOf(item.getCosto())));
            viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sIndex = String.valueOf(holder.getLayoutPosition());
                    Intent intent = new Intent(getActivity(),GasolinaDetails.class);
                    intent.putExtra("INDEX",sIndex);
                    intent.putExtra("MAX_INDEX",String.valueOf(listGasolina.size()));
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
            ModelGastosGasolina item = items.get(position);
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item);
            }
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

        public boolean isPendingRemoval(int position) {
            ModelGastosGasolina item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView et_fecha;
        TextView et_carro;
        TextView et_litros;
        TextView et_monto;
        View mView;

        TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_gasto_gasolina_recyclerview, parent, false));

            et_fecha = (TextView) itemView.findViewById(R.id.row_gasolina_fecha);
            et_carro = (TextView) itemView.findViewById(R.id.row_gasolina_carro);
            et_litros = (TextView) itemView.findViewById(R.id.row_gasolina_litros);
            et_monto = (TextView) itemView.findViewById(R.id.row_gasolina_monto);
            mView = itemView;
        }
    }

    private class QueryGatoGasolinaTask extends AsyncTask<String, Integer, String> {
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
            UtilsDML.resultQueryGasolina(getActivity().getApplication(),result,listGasolina);
            setUpRecyclerView();
            progressBar.cancel();
        }
    }
}
