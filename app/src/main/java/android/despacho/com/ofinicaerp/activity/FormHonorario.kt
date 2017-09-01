package android.despacho.com.ofinicaerp.activity

import android.app.ProgressDialog
import android.despacho.com.ofinicaerp.R
import android.despacho.com.ofinicaerp.models.ModelDespacho_Honorarios
import android.despacho.com.ofinicaerp.utils.Constants
import android.despacho.com.ofinicaerp.utils.Utils
import android.despacho.com.ofinicaerp.utils.UtilsDML
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_form_honorario.btn_guardar
import kotlinx.android.synthetic.main.activity_form_honorario.et_monto
import kotlinx.android.synthetic.main.activity_form_honorario.spinner_ano
import kotlinx.android.synthetic.main.activity_form_honorario.spinner_mes
import kotlinx.android.synthetic.main.activity_form_honorario.radioGroup_status
import kotlinx.android.synthetic.main.activity_form_honorario.radioButton_abono
import kotlinx.android.synthetic.main.activity_form_honorario.radioButton_noPagado
import kotlinx.android.synthetic.main.activity_form_honorario.radioButton_pagado
import kotlinx.android.synthetic.main.activity_form_honorario.tv_cliente
import kotlinx.android.synthetic.main.activity_form_honorario.tv_id_hono
import kotlinx.android.synthetic.main.activity_form_honorario.img_back
import java.util.*

class FormHonorario : AppCompatActivity(), RadioGroup.OnCheckedChangeListener {

    private var iStatus: Int = 1
    private lateinit var progressBar: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_honorario)
        init()
    }

    fun init(){
        progressBar = ProgressDialog(this)
        progressBar.setMessage("Cargando...")
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressBar.setCancelable(false)
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.isIndeterminate = true

        val idHono = intent.getStringExtra("ID_HONO")
        var idMes = intent.getStringExtra("ID_MES")
        var ano = intent.getStringExtra("ANO")
        val status = intent.getStringExtra("STATUS")
        val cliente = intent.getStringExtra("CLIENT")
        val monto = intent.getStringExtra("MONTO")

        val meses = resources.getStringArray(R.array.meses)
        val calendarAno = Calendar.getInstance()
        val anoActual = calendarAno.get(Calendar.YEAR).toString().trim()
        val iAnoAtual = anoActual.toInt()
        val iMinAno = iAnoAtual - 5
        val arrayAno: ArrayList<String> = arrayListOf(iAnoAtual.toString(), (iMinAno + 4).toString(), (iMinAno + 3).toString(), (iMinAno + 2).toString(), (iMinAno + 1).toString())
        spinner_ano.adapter = ArrayAdapter(application,R.layout.row_spinner_item,arrayAno)
        spinner_mes.adapter = ArrayAdapter(application,R.layout.row_spinner_item,meses)
        spinner_mes.setSelection(idMes.toInt()-1)

        spinner_ano.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               ano =  parent!!.getItemAtPosition(position).toString()
            }
        }
        spinner_mes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val _idMes =  parent!!.getItemAtPosition(position).toString().split("-".toRegex())
                idMes = _idMes[0]
            }

        }
        tv_id_hono.text = getString(R.string.numRegistro,idHono)
        tv_cliente.text = cliente

        et_monto.setText(monto)
        radioGroup_status.setOnCheckedChangeListener(this)
        radioButton_pagado.isChecked = true
        img_back.setOnClickListener({
            onBackPressed()
        })

        btn_guardar.setOnClickListener({
            if (et_monto.text.equals("")){
                Toast.makeText(this@FormHonorario, getString(R.string.msg_campos_vacios), Toast.LENGTH_LONG).show()
            } else {
                val _monto = et_monto.text.toString()
                val honorario = ModelDespacho_Honorarios(idHono.toInt(),idMes.toInt(),ano,iStatus,_monto.toDouble())
                val strJSON = honorario.toJSONUpdateHono()
                updateHonorarioTask().execute(Constants.URL_UPDATE_HONORARIOS,strJSON)
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when(checkedId){
            R.id.radioButton_pagado -> { iStatus = 1 }

            R.id.radioButton_abono -> {  iStatus = 4 }

            R.id.radioButton_noPagado -> { iStatus = 2 }
        }
    }

    inner class updateHonorarioTask : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.show()
        }

        override fun doInBackground(vararg params: String?): String {
            return UtilsDML.addData(Constants.POST_HONORARIOS, params[0], params[1])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.cancel()
            Utils.proccessResult(this@FormHonorario, result)
            finish()
        }
    }
}
