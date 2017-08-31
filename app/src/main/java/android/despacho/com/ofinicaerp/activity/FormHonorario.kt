package android.despacho.com.ofinicaerp.activity

import android.despacho.com.ofinicaerp.R
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_form_honorario.btn_guardar
import kotlinx.android.synthetic.main.activity_form_honorario.et_monto
import kotlinx.android.synthetic.main.activity_form_honorario.spinner_ano
import kotlinx.android.synthetic.main.activity_form_honorario.spinner_mes
import kotlinx.android.synthetic.main.activity_form_honorario.spinner_status
import kotlinx.android.synthetic.main.activity_form_honorario.tv_cliente
import kotlinx.android.synthetic.main.activity_form_honorario.tv_id_hono
import java.util.*

class FormHonorario : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_honorario)
        init()
    }

    fun init(){
        val idHono = intent.getStringExtra("ID_HONO")
        val idMes = intent.getStringExtra("ID_MES")
        val ano = intent.getStringExtra("ANO")
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


        tv_id_hono.text = getString(R.string.numRegistro,idHono)
        tv_cliente.text = cliente
        spinner_mes.setSelection(idMes.toInt())






    }
}
