package android.despacho.com.ofinicaerp.utils;


import android.app.Application;
import android.despacho.com.ofinicaerp.R;
import android.despacho.com.ofinicaerp.models.ModelUser;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class UtilsDML {

    public static String APP_TAG = "ERP--";

    public static String login(String baseUrl, String jsonData){
        BufferedReader in = null;


        try {
            //Creamos un objeto Cliente HTTP para manejar la peticion al servidor
            HttpClient httpClient = new DefaultHttpClient();
            //Creamos objeto para armar peticion de tipo HTTP POST
            HttpPost post = new HttpPost(baseUrl);

            //Configuramos los parametos que vaos a enviar con la peticion HTTP POST
            //    List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
            //    nvp.add(new BasicNameValuePair("article", jsonData));
            //post.setHeader("Content-type", "application/json");
            //      post.setEntity(new UrlEncodedFormEntity(nvp));


            //Se ejecuta el envio de la peticion y se espera la respuesta de la misma.
            HttpResponse response = httpClient.execute(post);
            Log.w(APP_TAG, response.getStatusLine().toString());


            //Obtengo el contenido de la respuesta en formato InputStream Buffer y la paso a formato String
            in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");
            String line = "";
            String NL = System.getProperty("line.separator");
            while ((line = in.readLine()) != null) {
                sb.append(line + NL);
            }
            in.close();
            return sb.toString();

        } catch (Exception e) {
            return "Exception happened: " + e.getMessage();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String loginResultJson(Application context, String resultTask,ModelUser resultUser){
        JSONArray jsonArray = null;
        String result = "";

        try {
            jsonArray = new JSONArray(resultTask);
        } catch (JSONException e) {
            Log.e("JSON", e.toString());
        }
        if (jsonArray.length() > 0) {

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    resultUser = new ModelUser(
                            jsonObject.getString("tipousuario"),
                            Integer.parseInt(jsonObject.getString("idempleado")));

                    //adapter.add("Clave : "+clave+" Nombre : "+ nombre);
                } catch (JSONException e) {
                    Log.e("JSON", e.toString());
                }
            }


        } else {
            result = context.getString(R.string.msg_usiarioIncorrecto);
        }

        return result;
    }
}
