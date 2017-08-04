package android.despacho.com.ofinicaerp.utils;


import android.app.Application;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.despacho.com.ofinicaerp.R;
import android.despacho.com.ofinicaerp.models.ModelDespacho_Clientes;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.despacho.com.ofinicaerp.models.ModelGastosGasolina;
import android.despacho.com.ofinicaerp.models.ModelMantenimiento;
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
            return result = "Ocurrio un error al conectarse al servidor, intentelo de nuevo.";
          //  Log.e("JSON", e.toString());
        }



        return result;
    }

    public static boolean loadDataUser(Application context, ModelEmpleado empleado){
        boolean bIsOk = false;
        return bIsOk;
    }

    public static String addData(String data,String baseUrl, String jsonData){
        BufferedReader in = null;
        try{
            //Creamos un objeto Cliente HTTP para manejar la peticion al servidor
            HttpClient httpClient = new DefaultHttpClient();
            //Creamos objeto para armar peticion de tipo HTTP POST
            HttpPost post = new HttpPost(baseUrl);

            //Configuramos los parametos que vaos a enviar con la peticion HTTP POST
            List<NameValuePair> nvp = new ArrayList<NameValuePair>(2);
            nvp.add(new BasicNameValuePair(data, jsonData));
            //post.setHeader("Content-type", "application/json");
            post.setEntity(new UrlEncodedFormEntity(nvp));

            //Se ejecuta el envio de la peticion y se espera la respuesta de la misma.
            HttpResponse response = httpClient.execute(post);
            Log.i("JSON-CLIENTE--",jsonData);
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

    public static String queryAllData(String baseUrl){
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

    public static void resultQueryEmpleado(String result, List<ModelEmpleado> listEmpleados){
        //Se obtiene el resultado de la peticion Asincrona
        Log.w(APP_TAG,"Resultado obtenido " + result);
        //    data.setText(result);
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(result);
        } catch (JSONException e) {
            Log.e("JSON", e.toString());
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listEmpleados.add(new ModelEmpleado(Integer.parseInt(jsonObject.getString("idempleado")),
                        jsonObject.getString("nombre"),
                        jsonObject.getString("puesto"),
                        Double.parseDouble(jsonObject.getString("sueldo")),
                        jsonObject.getString("empresa"),
                        jsonObject.getString("photobase64"),
                        jsonObject.getString("telefono")));

                //adapter.add("Clave : "+clave+" Nombre : "+ nombre);
            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        }

    }

    public static void resultQueryCliente(String result, List<ModelDespacho_Clientes> listClientes){
        //Se obtiene el resultado de la peticion Asincrona
        Log.w(APP_TAG,"Resultado obtenido " + result);
        //    data.setText(result);
        JSONArray jsonArray = null;

        try {
            jsonArray = new JSONArray(result);
        } catch (JSONException e) {
            Log.e("JSON", e.toString());
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                listClientes.add(new ModelDespacho_Clientes(
                        Integer.parseInt(jsonObject.getString("idcliente")),
                        jsonObject.getString("nombre"),
                        jsonObject.getString("rfc"),
                        jsonObject.getString("curp"),
                        jsonObject.getString("passSat"),
                        jsonObject.getString("passFiel"),
                        jsonObject.getString("passCert"),
                        Double.parseDouble(jsonObject.getString("honorarios"))));

                //adapter.add("Clave : "+clave+" Nombre : "+ nombre);
            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }
        }

    }

    public static void resultQueryVehiculo(String result, List<ModelVehiculo> listVehiculo){
        //Se obtiene el resultado de la peticion Asincrona
        Log.w(APP_TAG,"Resultado obtenido " + result);
        //    data.setText(result);
        JSONArray jsonArray = null;
        if (result.contains("[")) {
            try {
                jsonArray = new JSONArray(result);
            } catch (JSONException e) {
                Log.e("JSON", e.toString());
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    listVehiculo.add(new ModelVehiculo(
                            Integer.parseInt(jsonObject.getString("idvehiculo")),
                            jsonObject.getString("nombre"),
                            jsonObject.getString("modelo"),
                            jsonObject.getString("marca"),
                            jsonObject.getString("serie"),
                            Integer.parseInt(jsonObject.getString("idempleado")),
                            jsonObject.getString("photobase64"),
                            jsonObject.getString("placas"),
                            jsonObject.getString("color"),
                            jsonObject.getString("empleado")));

                    //adapter.add("Clave : "+clave+" Nombre : "+ nombre);
                } catch (JSONException e) {
                    Log.e("JSON", e.toString());
                }
            }
        }

    }

    public static String resultQueryGasolina(Application context,String resultTask, List<ModelGastosGasolina> resultGastoGasolina) {
        JSONArray jsonArray = null;
        String result = "";

        Log.d("GASOLINA--", resultTask);
        try {
            jsonArray = new JSONArray(resultTask);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String fecha = jsonObject.getString("fecha");
                        String photobase64 = jsonObject.getString("photobase64");
                        String carro = jsonObject.getString("carro");
                        String marca = jsonObject.getString("marca");
                        String modelo = jsonObject.getString("modelo");
                        String tipo_gas = jsonObject.getString("tipogas");
                        String litros = jsonObject.getString("litros");
                        String costo = jsonObject.getString("costo");

                        ModelGastosGasolina gastosGasolina = new ModelGastosGasolina(
                                fecha,
                                photobase64,
                                carro,
                                marca,
                                modelo,
                                Double.parseDouble(litros),
                                tipo_gas,
                                Double.parseDouble(costo));
                        resultGastoGasolina.add(gastosGasolina);


                    } catch (JSONException e) {
                        Log.e("JSON---", e.toString());
                    }
                }


            } else {

                result = context.getString(R.string.msg_usiarioIncorrecto);
            }
        } catch (JSONException e) {
            return result = "Ocurrio un error al conectarse al servidor, intentelo de nuevo.";
            //  Log.e("JSON", e.toString());
        }



        return result;
    }

    public static String resultQueryMantenimiento(Application context,String resultTask, List<ModelMantenimiento> resultMantenimiento) {
        JSONArray jsonArray = null;
        String result = "";

        Log.d("MANTENIMIENTO--", resultTask);
        try {
            jsonArray = new JSONArray(resultTask);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String idMantenimiento = jsonObject.getString("idMantenimiento");
                        String fecha = jsonObject.getString("fecha");
                        String mantenimiento = jsonObject.getString("mantenimiento");
                        String descripcion = jsonObject.getString("descripcion");
                        String monto = jsonObject.getString("monto");
                        String idVehiculo = jsonObject.getString("idVehiculo");
                        String vehiculo = jsonObject.getString("vehiculo");
                        String photoVehiculo = jsonObject.getString("photoVehiculo");


                        ModelMantenimiento gastosGasolina = new ModelMantenimiento(
                                Integer.parseInt(idMantenimiento),
                                mantenimiento,
                                descripcion,
                                Double.parseDouble(monto),
                                fecha,
                                Integer.parseInt(idVehiculo),
                                vehiculo,
                                photoVehiculo);
                        resultMantenimiento.add(gastosGasolina);


                    } catch (JSONException e) {
                        Log.e("JSON---", e.toString());
                    }
                }


            } else {

                result = context.getString(R.string.msg_usiarioIncorrecto);
            }
        } catch (JSONException e) {
            return result = "Ocurrio un error al conectarse al servidor, intentelo de nuevo.";
            //  Log.e("JSON", e.toString());
        }



        return result;
    }
}
