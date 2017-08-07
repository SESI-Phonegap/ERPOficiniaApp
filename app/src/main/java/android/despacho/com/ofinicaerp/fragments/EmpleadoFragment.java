package android.despacho.com.ofinicaerp.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_EMPLEADO;


public class EmpleadoFragment extends Fragment {
    public RecyclerView recyclerView;
    private EmpleadoAdapter testAdapter;
    private List<ModelEmpleado> listEmpleados;
    private ProgressDialog progressBar;

    public EmpleadoFragment() {
        // Required empty public constructor
    }

    public static EmpleadoFragment newInstance() {
        EmpleadoFragment fragment = new EmpleadoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empleado, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init() {
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        listEmpleados = new ArrayList<>();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_empleado);
        //UtilsDML.consultaEmpleado(getActivity().getApplication(), listEmpleados);
        new QueryEmpleadoTask().execute(URL_QUERY_EMPLEADO);
    }

    public void setUpRecyclerView() {
        testAdapter = new EmpleadoAdapter(listEmpleados, getActivity());
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

    public class EmpleadoAdapter extends RecyclerView.Adapter {
        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
        List<ModelEmpleado> items;
        Context mContext;
        List<ModelEmpleado> itemsPendingRemoval;
        int lastInsertedIndex; // so we can add some more items for testing purposes
        boolean undoOn; // is undo on, you can turn it on from the toolbar menu

        private Handler handler = new Handler(); // hanlder for running delayed runnables

        private EmpleadoAdapter(List<ModelEmpleado> item, Context context) {

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
            final ModelEmpleado item = items.get(position);
            viewHolder.et_nombre.setText(item.getNombre());

                if (item.getPhotoBase64().equals("")) {
                    viewHolder.photoEmpleado.setImageResource(R.drawable.ni_image);
                } else {
                    String UrlImage = Constants.URL_BASE + item.getPhotoBase64();
                    Picasso.with(getContext()).load(UrlImage).fit().into(viewHolder.photoEmpleado);
                }

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

        public void llamar(int position) {
            final String itemTel = listEmpleados.get(position).getTelefono();

            try {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //  explicarUsoPermiso();
                    // Pedir permiso para realizar llamada
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);

                } else {

                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + itemTel)));

                }

                Runnable pendingRemovalRunnable = new Runnable() {
                    @Override
                    public void run() {
                        setUpRecyclerView();
                    }
                };
                handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void removeAll(){
            items.removeAll(itemsPendingRemoval);
            notifyDataSetChanged();
        }

        public void remove(int position) {
            ModelEmpleado item = items.get(position);
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item);
            }
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

        public boolean isPendingRemoval(int position) {
            ModelEmpleado item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {
        CircleImageView photoEmpleado;
        TextView et_nombre;
        View mView;

        TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_empleado, parent, false));
            photoEmpleado = (CircleImageView) itemView.findViewById(R.id.circleImageViewEmpleado);
            et_nombre = (TextView) itemView.findViewById(R.id.row_empleado_nombre);
            mView = itemView;
        }
    }

    public class QueryEmpleadoTask extends AsyncTask<String, Integer, String> {
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
            UtilsDML.resultQueryEmpleado(result,listEmpleados);
            setUpRecyclerView();
            progressBar.cancel();
        }
    }
}
