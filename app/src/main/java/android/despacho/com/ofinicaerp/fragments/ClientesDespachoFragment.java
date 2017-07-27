package android.despacho.com.ofinicaerp.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.despacho.com.ofinicaerp.models.ModelDespacho_Clientes;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ClientesDespachoFragment extends Fragment {

    public RecyclerView recyclerView;
    private ClienteAdapter testAdapter;
    private List<ModelDespacho_Clientes> listClientes;


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
        listClientes = new ArrayList<>();
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_clientes);
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
            final ModelDespacho_Clientes item = items.get(position);
            viewHolder.et_nombre.setText(item.getNombre());

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

        public boolean isPendingRemoval(int position) {
            ModelDespacho_Clientes item = items.get(position);
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


}
