package android.despacho.com.ofinicaerp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.models.ModelGastosGasolina;
import android.despacho.com.ofinicaerp.models.ModelVehiculo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class GastoGasolinaFragment extends Fragment {

    private EditText et_fechaIni;
    private EditText et_fechaFin;
    public RecyclerView recyclerView;
    private GasolinaAdapter testAdapter;
    private List<ModelGastosGasolina> listGasolina;
    private ProgressDialog progressBar;

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
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        et_fechaFin = (EditText) getActivity().findViewById(R.id.frag_gasolina_fechaFin);
        et_fechaIni = (EditText) getActivity().findViewById(R.id.frag_gasolina_fechaIni);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.frag_gasolina_recyclerView);
        listGasolina = new ArrayList<>();
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
            itemsPendingRemoval = new ArrayList<>();
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
            final ModelGastosGasolina item = items.get(position);
            viewHolder.et_fecha.setText(item.getFecha());
            viewHolder.et_carro.setText(item.getCarro());
            viewHolder.et_litros.setText(String.valueOf(item.getCantidad_litros()));
            viewHolder.et_monto.setText(String.valueOf(item.getCosto()));

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


}
