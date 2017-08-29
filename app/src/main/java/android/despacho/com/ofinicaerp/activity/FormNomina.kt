package android.despacho.com.ofinicaerp.activity

import android.app.ProgressDialog
import android.despacho.com.ofinicaerp.R
import android.despacho.com.ofinicaerp.models.ModelEmpleado
import android.despacho.com.ofinicaerp.models.ModelPagoEmpleado
import android.despacho.com.ofinicaerp.utils.Constants
import android.despacho.com.ofinicaerp.utils.Utils
import android.despacho.com.ofinicaerp.utils.UtilsDML
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_form_nomina.imgBack
import kotlinx.android.synthetic.main.activity_form_nomina.spinner_empleado
import kotlinx.android.synthetic.main.activity_form_nomina.spinner_mes
import kotlinx.android.synthetic.main.activity_form_nomina.spinner_semana
import kotlinx.android.synthetic.main.activity_form_nomina.btn_guardar
import kotlinx.android.synthetic.main.activity_form_nomina.et_cantidad
import kotlinx.android.synthetic.main.activity_form_nomina.et_fecha
import kotlin.collections.ArrayList


class FormNomina : AppCompatActivity() {

    private lateinit var progressBar: ProgressDialog
    private lateinit var empleadoList: MutableList<ModelEmpleado>
    private lateinit var idEmpleado: ArrayList<String>
    private var _idSemana: Int = 1
    private var _idMes: Int = 1
    private var _idEmpleado: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_nomina)
        init()
    }

    fun init() {
        empleadoList = ArrayList()
        progressBar = ProgressDialog(this)
        progressBar.setMessage("Cargando...")
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressBar.setCancelable(false)
        progressBar.setCanceledOnTouchOutside(false)
        progressBar.isIndeterminate = true

        et_fecha.setOnClickListener({
            Utils.showDialogDate(this@FormNomina, et_fecha)
        })

        val semanas = resources.getStringArray(R.array.semanas)
        val meses = resources.getStringArray(R.array.meses)

        spinner_semana.adapter = ArrayAdapter(application, R.layout.row_spinner_item, semanas)
        spinner_mes.adapter = ArrayAdapter(application, R.layout.row_spinner_item, meses)
        spinner_semana.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val idSemana = parent!!.getItemAtPosition(position).toString().split("-".toRegex())
                _idSemana = idSemana[0].toInt()
            }

        }

        spinner_mes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val idMes = parent!!.getItemAtPosition(position).toString().split("-".toRegex())
                _idMes = idMes[0].toInt()
            }

        }

        imgBack.setOnClickListener({
            onBackPressed()
        })

        et_cantidad.addTextChangedListener(textWatcherMonto)

        btn_guardar.setOnClickListener({
            if (et_cantidad.text.equals("") || et_fecha.text.equals("")) {
                Toast.makeText(this@FormNomina, getString(R.string.msg_campos_vacios), Toast.LENGTH_LONG).show()
            } else {
                val monto = et_cantidad.text.toString().replace(Constants.PAYMENT_NUMBER_FORMAT_REGEX_POINT.toRegex(), Constants.STRING_EMPTY).toDouble()
                val fecha = et_fecha.text.toString()
                val modelPagoEmpleado = ModelPagoEmpleado(_idEmpleado,monto, fecha,_idMes ,_idSemana)
                val strJSON = modelPagoEmpleado.toJSONAddNomina()
                addNominaTask().execute(Constants.URL_ADD_NOMINA,strJSON)
            }
        })

        empleadosTask().execute(Constants.URL_QUERY_EMPLEADO)
    }

    private val textWatcherMonto = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            et_cantidad.removeTextChangedListener(this)
            val cleanString: String = s.toString().replace(Constants.PAYMENT_NUMBER_FORMAT_REGEX.toRegex(), Constants.STRING_EMPTY)
            val parsed = Utils.convertToDouble(cleanString)
            val formated = Utils.parseToString(parsed / 100)

            et_cantidad.setText(formated)
            Selection.setSelection(et_cantidad.editableText, et_cantidad.editableText.length)
            et_cantidad.addTextChangedListener(this)
        }


    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    inner class empleadosTask : AsyncTask<String, Int, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.show()
        }

        override fun doInBackground(vararg params: String?): String {
            return UtilsDML.queryAllData(params[0])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            UtilsDML.resultQueryEmpleado(result, empleadoList)
            progressBar.cancel()
            var x = 0

            idEmpleado = arrayListOf()
            do {
                val id: String = empleadoList[x].id_empleado.toString()
                val empleado: String = empleadoList[x].nombre
                idEmpleado.add("$id-$empleado")
                x++
            } while (x < empleadoList.size)


            spinner_empleado.adapter = ArrayAdapter(application, R.layout.row_spinner_item, idEmpleado)
            spinner_empleado.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    val idEmp = parent!!.getItemAtPosition(position).toString().split("-".toRegex())
                    _idEmpleado = idEmp[0].toInt()
                }

            }
        }
    }

    inner class addNominaTask : AsyncTask<String, Int, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.show()
        }

        override fun doInBackground(vararg params: String?): String {
            return UtilsDML.addData(Constants.POST_NOMINA, params[0], params[1])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            progressBar.cancel()
            Utils.proccessResult(this@FormNomina, result)
            finish()

        }
    }
}
