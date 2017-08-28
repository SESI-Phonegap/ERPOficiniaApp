package android.despacho.com.ofinicaerp.activity

import android.app.ProgressDialog
import android.despacho.com.ofinicaerp.R
import android.despacho.com.ofinicaerp.models.ModelComprobanteGastos
import android.despacho.com.ofinicaerp.models.ModelEmpleado
import android.despacho.com.ofinicaerp.utils.Constants
import android.despacho.com.ofinicaerp.utils.Utils
import android.despacho.com.ofinicaerp.utils.UtilsDML
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.Selection
import android.text.TextWatcher
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_form_nomina.imgBack;
import kotlinx.android.synthetic.main.activity_form_nomina.spinner_empleado;
import kotlinx.android.synthetic.main.activity_form_nomina.spinner_mes;
import kotlinx.android.synthetic.main.activity_form_nomina.spinner_semana;
import kotlinx.android.synthetic.main.activity_form_nomina.btn_guardar;
import kotlinx.android.synthetic.main.activity_form_nomina.et_cantidad;
import kotlinx.android.synthetic.main.activity_form_nomina.et_fecha;
import java.util.*
import kotlin.collections.ArrayList


class FormNomina : AppCompatActivity() {

    private lateinit var progressBar: ProgressDialog
    private lateinit var empleadoList: MutableList<ModelEmpleado>
    private lateinit var idEmpleado:Array<String>

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
            Utils.showDialogDate(application, et_fecha)
        })
        var semanas = resources.getStringArray(R.array.semanas)
        var meses = resources.getStringArray(R.array.meses)

        spinner_semana.adapter = ArrayAdapter(application, R.layout.row_spinner_item, semanas)
        spinner_mes.adapter = ArrayAdapter(application, R.layout.row_spinner_item, meses)

        imgBack.setOnClickListener({
            onBackPressed()
        })

        et_cantidad.addTextChangedListener(textWatcherMonto)

        empleadosTask().execute(Constants.URL_QUERY_EMPLEADO)
    }

    val textWatcherMonto = object : TextWatcher {

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            et_cantidad.removeTextChangedListener(this)
            val cleanString: String = s.toString().replace(Constants.PAYMENT_NUMBER_FORMAT_REGEX, Constants.STRING_EMPTY)
            val parsed = Utils.convertToDouble(cleanString)
            val formated = Utils.parseToString(parsed / 100)

            et_cantidad.setText(formated)
            Selection.setSelection(et_cantidad.editableText,et_cantidad.editableText.length)
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
            return UtilsDML.addData(Constants.POST_EMPLEADO, params[0], params[1])
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            UtilsDML.resultQueryEmpleado(result, empleadoList)
            progressBar.cancel()
            var x:Int = 0

            do {
                var id:String = empleadoList.get(x).id_empleado.toString()
                var empleado:String = empleadoList.get(x).nombre
                idEmpleado[x] = "$id '-' $empleado"
                x++
            } while (x < empleadoList.size)


            spinner_empleado.adapter = ArrayAdapter(application,R.layout.row_spinner_item,idEmpleado)
        }
    }
}
