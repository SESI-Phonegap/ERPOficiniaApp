package android.despacho.com.ofinicaerp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.fragments.TiendasFragment;
import android.despacho.com.ofinicaerp.models.ModelTienda;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.despacho.com.ofinicaerp.R;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.despacho.com.ofinicaerp.utils.Constants.POST_TIENDA;
import static android.despacho.com.ofinicaerp.utils.Constants.URL_QUERY_TIENDAS;

public class TiendasPorRuta extends AppCompatActivity {
    private ImageView imgBack;
    private RecyclerView recyclerView;
    private TiendasAdapter testAdapter;
    private List<ModelTienda> listTiendas;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiendas_por_ruta);
        init();
    }

    public void init(){
        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);
        listTiendas = new ArrayList<>();
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_tiendas);

        int idRuta = Integer.parseInt(getIntent().getStringExtra("IDRUTA"));
        String strJSON = toJsonTiendas(idRuta);
        new QueryTiendasTask().execute(POST_TIENDA,URL_QUERY_TIENDAS,strJSON);
    }


    public void setUpRecyclerView() {
        testAdapter = new TiendasAdapter(listTiendas, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(testAdapter);
        recyclerView.setHasFixedSize(true);
    }

    public String toJsonTiendas(int idruta) {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("idRuta", idruta);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public class TiendasAdapter extends RecyclerView.Adapter {
        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
        List<ModelTienda> items;
        Context mContext;
        List<ModelTienda> itemsPendingRemoval;
        int lastInsertedIndex; // so we can add some more items for testing purposes
        boolean undoOn; // is undo on, you can turn it on from the toolbar menu

        private Handler handler = new Handler(); // hanlder for running delayed runnables

        private TiendasAdapter(List<ModelTienda> item, Context context) {

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
            final ModelTienda item = items.get(position);
            viewHolder.imgTienda.setImageResource(R.drawable.store);
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

        public void removeAll(){
            items.removeAll(itemsPendingRemoval);
            notifyDataSetChanged();
        }

        public void remove(int position) {
            ModelTienda item = items.get(position);
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item);
            }
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

        public boolean isPendingRemoval(int position) {
            ModelTienda item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }
    }

    private static class TestViewHolder extends RecyclerView.ViewHolder {
        CircleImageView imgTienda;
        TextView et_nombre;
        View mView;

        TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_empleado, parent, false));
            imgTienda = (CircleImageView) itemView.findViewById(R.id.circleImageViewEmpleado);
            et_nombre = (TextView) itemView.findViewById(R.id.row_empleado_nombre);
            mView = itemView;
        }
    }

    private class QueryTiendasTask extends AsyncTask<String, Integer, String> {
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
            UtilsDML.resultQueryTiendas(result,listTiendas);
            setUpRecyclerView();
            progressBar.cancel();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
