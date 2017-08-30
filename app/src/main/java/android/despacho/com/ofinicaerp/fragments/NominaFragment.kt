package android.despacho.com.ofinicaerp.fragments

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_nomina.spinner_ano
import kotlinx.android.synthetic.main.fragment_nomina.spinner_mes
import kotlinx.android.synthetic.main.fragment_nomina.btn_buscar
import kotlinx.android.synthetic.main.fragment_nomina.recycler_view_nomina
import kotlinx.android.synthetic.main.fragment_nomina.labelTotal_ingreso
import android.despacho.com.ofinicaerp.R
import android.despacho.com.ofinicaerp.models.ModelPagoEmpleado
import android.despacho.com.ofinicaerp.utils.Constants
import android.despacho.com.ofinicaerp.utils.UtilsDML
import android.os.AsyncTask
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.*
import kotlin.collections.ArrayList

class NominaFragment : Fragment() {

    private lateinit var nominaList: MutableList<ModelPagoEmpleado>
    private lateinit var progressBar: ProgressDialog
    private lateinit var testAdapter: NominaAdapter
    private var _idMes: Int = 1
    private var _ano: String = ""

    companion object {

        fun newInstance(): NominaFragment {
            val fragment = NominaFragment()
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_nomina, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        nominaList = ArrayList()
        progressBar = ProgressDialog(activity.application)
        progressBar.setMessage("Cargando...")
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressBar.setCancelable(false)
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.isIndeterminate = true

        val meses = resources.getStringArray(R.array.meses)
        val calendarAno = Calendar.getInstance()
        val anoActual = calendarAno.get(Calendar.YEAR).toString().trim()
        val iAnoAtual = anoActual.toInt()
        val iMinAno = iAnoAtual - 5
        val arrayAno: ArrayList<String> = arrayListOf((iMinAno + 1).toString(), (iMinAno + 2).toString(), (iMinAno + 3).toString(), (iMinAno + 4).toString(), (iAnoAtual).toString())

        spinner_mes.adapter = ArrayAdapter(activity.application, R.layout.row_spinner_item, meses)
        spinner_ano.adapter = ArrayAdapter(activity.application, R.layout.row_spinner_item, arrayAno)

        spinner_mes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val idMes = parent!!.getItemAtPosition(position).toString().split("-".toRegex())
                _idMes = idMes[0].toInt()
            }

        }
        spinner_ano.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                _ano = parent!!.getItemAtPosition(position).toString()
            }

        }

        btn_buscar.setOnClickListener({
            testAdapter.removeAll()
            labelTotal_ingreso.setText("")

            val modelNomina = ModelPagoEmpleado(_idMes,_ano)
            val strJSON = modelNomina.toJSONQueryNomina()


        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    fun setUpRecyclerView() {
        testAdapter = NominaAdapter(nominaList, activity.application, labelTotal_ingreso)
        recycler_view_nomina.layoutManager = LinearLayoutManager(activity.application)
        recycler_view_nomina.adapter = testAdapter
        recycler_view_nomina.setHasFixedSize(true)
    }

    class NominaAdapter internal constructor(item: MutableList<ModelPagoEmpleado>, context: Context, labelTotal: TextView) : RecyclerView.Adapter<TestViewHolder>() {


        internal var items: MutableList<ModelPagoEmpleado>
        internal var mContext: Context
        internal var itemsPendingRemoval: MutableList<ModelPagoEmpleado>
        internal var lastInsertedIndex: Int = 0 // so we can add some more items for testing purposes
        var isUndoOn: Boolean = false
            private set // is undo on, you can turn it on from the toolbar menu
        private val handler = Handler() // hanlder for running delayed runnables
        internal var _labelTotal: TextView = labelTotal

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
            var total = +item.monto

            _labelTotal.setText(mContext.getString(R.string.total, total.toString()))
            var semana = ""
            when (item.semana) {
                1 -> {
                    semana = "Semana 1"
                }
                2 -> {
                    semana = "Semana 2"
                }
                3 -> {
                    semana = "Semana 3"
                }
                4 -> {
                    semana = "Semana 4"
                }
            }
            viewHolder.tv_nombre.text = item.nombreEmpleado
            viewHolder.tv_monto.text = mContext.getString(R.string.monto, item.monto.toString())
            viewHolder.tv_semana.text = semana

        }

        override fun getItemCount(): Int {
            var itemCount: Int
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


    class TestViewHolder internal constructor(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_recyclerview_nomina, parent, false)) {
        internal var tv_nombre: TextView
        internal val tv_semana: TextView
        internal var tv_monto: TextView
        internal var mView: View

        init {
            tv_nombre = itemView.findViewById(R.id.tv_empleado) as TextView
            tv_semana = itemView.findViewById(R.id.tv_semana) as TextView
            tv_monto = itemView.findViewById(R.id.tv_total) as TextView
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
            UtilsDML.resultQueryNomina(activity.application, result, nominaList)
            setUpRecyclerView()
            progressBar.cancel()
        }
    }

}// Required empty public constructor
