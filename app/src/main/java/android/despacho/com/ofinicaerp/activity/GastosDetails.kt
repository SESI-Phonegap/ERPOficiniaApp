package android.despacho.com.ofinicaerp.activity

import android.app.Application
import android.app.ProgressDialog
import android.content.Context
import android.despacho.com.ofinicaerp.R
import android.despacho.com.ofinicaerp.models.ModelComprobanteGastos
import android.despacho.com.ofinicaerp.utils.Constants
import android.despacho.com.ofinicaerp.utils.Utils
import android.despacho.com.ofinicaerp.utils.UtilsDML
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_gastos_details.recycler_view_gastos_details
import kotlinx.android.synthetic.main.activity_gastos_details.imgBack

class GastosDetails : AppCompatActivity() {

    private lateinit var comprobantesList: MutableList<ModelComprobanteGastos>
    private lateinit var progressBar: ProgressDialog
    private lateinit var testAdapter: GastoDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos_details)
        comprobantesList = ArrayList()
        progressBar = ProgressDialog(this)
        progressBar.setMessage("Cargando...")
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressBar.setCancelable(false)
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.isIndeterminate = true

        imgBack.setOnClickListener({
            onBackPressed()
        })

        val intent_data:Int = intent.getIntExtra("ID_GASTO",0)
        val strJSON = Utils.toJsonIdGasto(intent_data.toString())
        gastosDetailsTask().execute(Constants.URL_QUERY_GASTOS_DETAILS_BY_ID, strJSON)
    }

    fun setUpRecyclerView() {
        testAdapter = GastoDetailsAdapter(comprobantesList, application)
        recycler_view_gastos_details.layoutManager = LinearLayoutManager(application)
        recycler_view_gastos_details.adapter = testAdapter
        recycler_view_gastos_details.setHasFixedSize(true)
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    class GastoDetailsAdapter internal constructor(item: MutableList<ModelComprobanteGastos>, context: Context) : RecyclerView.Adapter<GastosDetails.TestViewHolder>() {


        internal var items: MutableList<ModelComprobanteGastos>
        internal var mContext: Context
        internal var itemsPendingRemoval: MutableList<ModelComprobanteGastos>
        internal var lastInsertedIndex: Int = 0 // so we can add some more items for testing purposes
        var isUndoOn: Boolean = false
            private set // is undo on, you can turn it on from the toolbar menu
        private val handler = Handler() // hanlder for running delayed runnables

        init {
            items = item
            mContext = context
            itemsPendingRemoval = item
            // let's generate some items
            lastInsertedIndex = 15

        }

       override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder? {
            return TestViewHolder(parent)
        }


        override fun onBindViewHolder(holder: TestViewHolder?, position: Int) {
            val viewHolder = holder as TestViewHolder
            val item = items[position]

            viewHolder.tv_fecha.text = item.fecha
            viewHolder.tv_monto.text = mContext.getString(R.string.monto, item.monto.toString())
            viewHolder.tv_tipoGasto.text = item.categoria
            viewHolder.tv_concepto.text = item.concepto
            viewHolder.tv_tipoComprobante.text = item.tipo_comprobante
        }

        override fun getItemCount(): Int {
            var itemCount:Int
            itemCount = items.size
            return itemCount

        }


        fun removeAll() {

            items.removeAll(itemsPendingRemoval)
            notifyDataSetChanged()
        }

        fun remove(position: Int) {
            val item = items[position]
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item)
            }
            if (items.contains(item)) {
                items.removeAt(position)
                notifyItemRemoved(position)
            }
        }

        fun isPendingRemoval(position: Int): Boolean {
            val item = items[position]
            return itemsPendingRemoval.contains(item)
        }

        companion object {
            private val PENDING_REMOVAL_TIMEOUT = 3000 // 3sec
        }
    }

    class TestViewHolder internal constructor(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_cardview_recyclerview_comprobante_gastos, parent, false)) {
        internal var tv_fecha: TextView
        internal val tv_tipoComprobante: TextView
        internal var tv_monto: TextView
        internal var tv_concepto: TextView
        internal var tv_tipoGasto: TextView
        internal var mView: View

        init {
            tv_fecha = itemView.findViewById(R.id.recycler_card_fecha) as TextView
            tv_monto = itemView.findViewById(R.id.recycler_card_monto) as TextView
            tv_tipoComprobante = itemView.findViewById(R.id.recycler_card_tipoComprobante) as TextView
            tv_concepto = itemView.findViewById(R.id.recycler_card_concepto) as TextView
            tv_tipoGasto = itemView.findViewById(R.id.recycler_card_tipoGasto) as TextView
            mView = itemView
        }
    }

    inner class gastosDetailsTask : AsyncTask<String, Int, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.show()
        }

        override fun doInBackground(vararg params: String?): String {
            return UtilsDML.addData(Constants.POST_COMPROBANTE, params[0], params[1])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            UtilsDML.resultQueryComprobantes(application, result, comprobantesList)
            setUpRecyclerView()
            progressBar.cancel()
        }
    }
}
