package android.despacho.com.ofinicaerp.utils;


import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.despacho.com.ofinicaerp.R;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.despacho.com.ofinicaerp.models.ModelUser;
import android.despacho.com.ofinicaerp.models.ModelVehiculo;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

import static android.despacho.com.ofinicaerp.utils.Constants.ERROR_1001;

public class UtilsDML {

    public static String APP_TAG = "ERP--";

    public static String login(String baseUrl, String jsonData) {
        BufferedReader in = null;


        try {
            //Creamos un objeto Cliente HTTP para manejar la peticion al servidor
            HttpClient httpClient = new DefaultHttpClient();
            //Creamos objeto para armar peticion de tipo HTTP POST
            HttpPost post = new HttpPost(baseUrl);

            //Configuramos los parametos que vaos a enviar con la peticion HTTP POST
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
            nvp.add(new BasicNameValuePair("login", jsonData));
           // post.setHeader("Content-type", "application/json");
            post.setEntity(new UrlEncodedFormEntity(nvp));


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

    public static String loginResultJson(Application context, String resultTask, List<ModelUser> resultUser) {
        JSONArray jsonArray = null;
        String result = "";

        try {
            jsonArray = new JSONArray(resultTask);
                if (jsonArray.length() > 0) {

                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String tipo = jsonObject.getString("tipo");
                                int id = Integer.parseInt(jsonObject.getString("idempleado"));
                                ModelUser user = new ModelUser(tipo,id);
                                resultUser.add(user);


                        } catch (JSONException e) {
                            Log.e("JSON---", e.toString());
                        }
                    }


                } else {

                    result = context.getString(R.string.msg_usiarioIncorrecto);
                }
        } catch (JSONException e) {
            Log.e("JSON", e.toString());
        }



        return result;
    }

    public static boolean loadDataUser(Application context, ModelEmpleado empleado){
        boolean bIsOk = false;
        return bIsOk;
    }

    public static String addVehiculo(String baseUrl, String jsonData){
        BufferedReader in = null;
        try{
            //Creamos un objeto Cliente HTTP para manejar la peticion al servidor
            HttpClient httpClient = new DefaultHttpClient();
            //Creamos objeto para armar peticion de tipo HTTP POST
            HttpPost post = new HttpPost(baseUrl);

            //Configuramos los parametos que vaos a enviar con la peticion HTTP POST
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
            nvp.add(new BasicNameValuePair("vehiculo", jsonData));
            //post.setHeader("Content-type", "application/json");
            post.setEntity(new UrlEncodedFormEntity(nvp));

            //Se ejecuta el envio de la peticion y se espera la respuesta de la misma.
            HttpResponse response = httpClient.execute(post);
            Log.w("RESULT--", response.getStatusLine().toString());

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
        }catch (Exception e){
            return "Exception happened: " + e.getMessage();
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static String addEmpleado(String baseUrl, String jsonData){
        BufferedReader in = null;
        try{
            //Creamos un objeto Cliente HTTP para manejar la peticion al servidor
            HttpClient httpClient = new DefaultHttpClient();
            //Creamos objeto para armar peticion de tipo HTTP POST
            HttpPost post = new HttpPost(baseUrl);

            //Configuramos los parametos que vaos a enviar con la peticion HTTP POST
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
            nvp.add(new BasicNameValuePair("empleado", jsonData));
            //post.setHeader("Content-type", "application/json");
            post.setEntity(new UrlEncodedFormEntity(nvp));

            //Se ejecuta el envio de la peticion y se espera la respuesta de la misma.
            HttpResponse response = httpClient.execute(post);
            Log.w("RESULT--", response.getStatusLine().toString());

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
        }catch (Exception e){
            return "Exception happened: " + e.getMessage();
        }finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
