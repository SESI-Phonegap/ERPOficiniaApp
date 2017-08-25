package android.despacho.com.ofinicaerp.activity

import android.despacho.com.ofinicaerp.R
import android.despacho.com.ofinicaerp.utils.Constants
import android.despacho.com.ofinicaerp.utils.Utils
import android.despacho.com.ofinicaerp.utils.UtilsDML
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_gastos_details.recycler_view_gastos_details

class GastosDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos_details)
        val intent_data = intent.getStringExtra("ID_GASTO")
        var strJSON = Utils.toJsonIdGasto(intent_data)
        gastosDetailsTask().execute(Constants.URL_QUERY_GASTOS_DETAILS_BY_ID,strJSON)
    }

   private class gastosDetailsTask : AsyncTask<String, Int, String>() {
       override fun onPreExecute() {
           super.onPreExecute()
       }

       override fun doInBackground(vararg params: String?): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
         //  return UtilsDML.addData()
        }

       override fun onPostExecute(result: String?) {
           super.onPostExecute(result)
       }
   }
}
