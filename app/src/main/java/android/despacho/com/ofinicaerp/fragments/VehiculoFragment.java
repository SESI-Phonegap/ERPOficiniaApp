package android.despacho.com.ofinicaerp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.despacho.com.ofinicaerp.activity.VehiculoDetails;
import android.despacho.com.ofinicaerp.models.ModelVehiculo;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class VehiculoFragment extends Fragment {
    public RecyclerView recyclerView;
    private VehiculoAdapter testAdapter;
    public static List<ModelVehiculo> listVehiculos;
    private ProgressDialog progressBar;
    private String sIndex;

    public VehiculoFragment() {
        // Required empty public constructor
    }


    public static VehiculoFragment newInstance() {
        VehiculoFragment fragment = new VehiculoFragment();
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
        return inflater.inflate(R.layout.fragment_vehiculo, container, false);
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
        listVehiculos = new ArrayList<>();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_vehiculo);
        new QueryVehiculoTask().execute(Constants.URL_QUERY_VEHICULO);

    }

    public void setUpRecyclerView() {

        testAdapter = new VehiculoAdapter(listVehiculos, getActivity());
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


    public class VehiculoAdapter extends RecyclerView.Adapter {
        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
        List<ModelVehiculo> items;
        Context mContext;
        List<ModelVehiculo> itemsPendingRemoval;
        int lastInsertedIndex; // so we can add some more items for testing purposes
        boolean undoOn; // is undo on, you can turn it on from the toolbar menu

        private Handler handler = new Handler(); // hanlder for running delayed runnables

        VehiculoAdapter(List<ModelVehiculo> item, Context context) {
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
            final ModelVehiculo item = items.get(position);
            viewHolder.et_nombre.setText(item.getNombre());
            viewHolder.et_modelo.setText(item.getModelo());
            viewHolder.et_color.setText(item.getColor());
            viewHolder.et_placa.setText(item.getPlacas());

                if (item.getPhotoBase64().equals("")) {
                    viewHolder.photoVehiculo.setImageResource(R.drawable.ni_image);
                } else {
                    String UrlImage = Constants.URL_BASE + item.getPhotoBase64();
                    Log.d("IMAGE--",UrlImage);
                    Picasso.with(getContext()).load(UrlImage).fit().into(viewHolder.photoVehiculo);
                }

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sIndex = String.valueOf(holder.getLayoutPosition());
                        Intent intent = new Intent(getActivity(),VehiculoDetails.class);
                        intent.putExtra("INDEX",sIndex);
                        intent.putExtra("MAX_INDEX",String.valueOf(listVehiculos.size()));
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
            ModelVehiculo item = items.get(position);
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item);
            }
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

        public boolean isPendingRemoval(int position) {
            ModelVehiculo item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {
        CircleImageView photoVehiculo;
        TextView et_nombre;
        TextView et_placa;
        TextView et_color;
        TextView et_modelo;
        View mView;

        TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_vehiculo, parent, false));
            photoVehiculo = (CircleImageView) itemView.findViewById(R.id.list_avatar_car);
            et_nombre = (TextView) itemView.findViewById(R.id.row_name_car);
            et_placa = (TextView) itemView.findViewById(R.id.row_placas);
            et_color = (TextView) itemView.findViewById(R.id.row_color);
            et_modelo = (TextView) itemView.findViewById(R.id.row_model_car);
            mView = itemView;
        }
    }

    private class QueryVehiculoTask extends AsyncTask<String, Integer, String> {
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
            UtilsDML.resultQueryVehiculo(result,listVehiculos);
            setUpRecyclerView();
            progressBar.cancel();
        }
    }

}
