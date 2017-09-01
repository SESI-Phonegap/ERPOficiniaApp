package android.despacho.com.ofinicaerp.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.fragment_honorarios.*
import android.despacho.com.ofinicaerp.R
import android.despacho.com.ofinicaerp.activity.FormHonorario
import android.despacho.com.ofinicaerp.models.ModelDespacho_Honorarios
import android.despacho.com.ofinicaerp.utils.Constants
import android.despacho.com.ofinicaerp.utils.Utils
import android.despacho.com.ofinicaerp.utils.UtilsDML
import android.os.AsyncTask
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.*
import java.util.*
import kotlin.collections.ArrayList


class HonorariosFragment : Fragment(), RadioGroup.OnCheckedChangeListener {

    private var _idMes: Int = 1
    private var _ano: String = ""
    private var iStatus: Int = 3
    private lateinit var honorariosList: MutableList<ModelDespacho_Honorarios>
    private lateinit var progressBar: ProgressDialog
    private var testAdapter: HonorarioAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_honorarios, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init(){
        honorariosList = ArrayList()
        progressBar = ProgressDialog(activity)
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
        val arrayAno: ArrayList<String> = arrayListOf(iAnoAtual.toString(), (iMinAno + 4).toString(), (iMinAno + 3).toString(), (iMinAno + 2).toString(), (iMinAno + 1).toString())

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

        radioGroup2.setOnCheckedChangeListener(this)
        radioButton_todos.isChecked = true

        btn_buscar.setOnClickListener({
            if (testAdapter != null) {
                testAdapter!!.removeAll()
                tv_honorarios_total.text = ""
            }

            val modelHonorario = ModelDespacho_Honorarios(_idMes,_ano,iStatus)
            val strJSON = modelHonorario.toJSONQueryHono()
            honorariosTask().execute(Constants.URL_QUERY_HONORARIOS,strJSON)
        })

    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {

        when (checkedId) {

            R.id.radioButton_pagado -> {
                iStatus = 1
                Log.d("STATUS",iStatus.toString());
            }

            R.id.radioButton_noPagado -> {
                iStatus = 2
                Log.d("STATUS",iStatus.toString());
            }

            R.id.radioButton_todos -> {
                iStatus = 3
                Log.d("STATUS",iStatus.toString());
            }
        }
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }


    companion object {
        fun newInstance(): HonorariosFragment {
            val fragment = HonorariosFragment()
            return fragment
        }
    }

    fun setUpRecyclerView() {
        testAdapter = HonorarioAdapter(honorariosList, activity)
        recycler_view_honorarios.layoutManager = LinearLayoutManager(activity)
        recycler_view_honorarios.adapter = testAdapter
        recycler_view_honorarios.setHasFixedSize(true)
    }

    class HonorarioAdapter internal constructor(item: MutableList<ModelDespacho_Honorarios>, context: Context) : RecyclerView.Adapter<TestViewHolder>() {


        internal var items: MutableList<ModelDespacho_Honorarios>
        internal var mContext: Context
        internal var itemsPendingRemoval: MutableList<ModelDespacho_Honorarios>
        internal var lastInsertedIndex: Int = 0 // so we can add some more items for testing purposes
        internal val arrayMes:ArrayList<String> = arrayListOf("","Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre")

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

            viewHolder.tv_nombre.text = item.clienteNombre
            viewHolder.tv_monto.text = mContext.getString(R.string.monto, Utils.parseToString(item.monto))
            viewHolder.tv_ano.text = item.ano
            viewHolder.tv_mes.text = arrayMes[item.id_mes]
            if (item.status == 1) {
                viewHolder.img_status.setImageDrawable(mContext.resources.getDrawable(R.drawable.yes))
            } else {
                viewHolder.img_status.setImageDrawable(mContext.resources.getDrawable(R.drawable.no))
            }

            viewHolder.mView.setOnClickListener({
                val idHonorario = item.id_honorario.toString()
                val idMes = item.id_mes.toString()
                val _ano = item.ano
                val _status = item.status.toString()
                val intent = Intent(mContext.applicationContext,FormHonorario::class.java)
                intent.putExtra("ID_HONO",idHonorario)
                intent.putExtra("ID_MES",idMes)
                intent.putExtra("ANO",_ano)
                intent.putExtra("STATUS",_status)
                intent.putExtra("CLIENT",item.clienteNombre)
                intent.putExtra("MONTO",item.monto.toString())
                mContext.startActivity(intent)

            })

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


    class TestViewHolder internal constructor(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_recyclerview_honorarios, parent, false)) {
        internal var tv_nombre: TextView
        internal val tv_mes: TextView
        internal val tv_ano: TextView
        internal var tv_monto: TextView
        internal var img_status: ImageView
        internal var mView: View

        init {
            tv_nombre = itemView.findViewById(R.id.h_nombre) as TextView
            tv_mes = itemView.findViewById(R.id.h_mes) as TextView
            tv_ano = itemView.findViewById(R.id.h_ano) as TextView
            tv_monto = itemView.findViewById(R.id.h_monto) as TextView
            img_status = itemView.findViewById(R.id.h_status) as ImageView
            mView = itemView
        }
    }

    inner class honorariosTask : AsyncTask<String, Int, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.show()
        }

        override fun doInBackground(vararg params: String?): String {
            return UtilsDML.addData(Constants.POST_HONORARIOS, params[0], params[1])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            Log.d("HONORAR--",result)
            UtilsDML.resultQueryHonorarios(activity.application, result, honorariosList)
            setUpRecyclerView()

            var iTotal = 0.0
            for (x in honorariosList.indices) {
                iTotal = iTotal + honorariosList.get(x).monto
            }
            tv_honorarios_total.text = getString(R.string.monto,Utils.parseToString(iTotal))
            progressBar.cancel()
        }
    }


}// Required empty public constructor
