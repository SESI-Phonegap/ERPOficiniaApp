package android.despacho.com.ofinicaerp.utils;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.despacho.com.ofinicaerp.R;
import android.despacho.com.ofinicaerp.models.ModelCaja;
import android.despacho.com.ofinicaerp.models.ModelComprobanteGastos;
import android.despacho.com.ofinicaerp.models.ModelDespacho_Clientes;
import android.despacho.com.ofinicaerp.models.ModelDespacho_Honorarios;
import android.despacho.com.ofinicaerp.models.ModelEmpleado;
import android.despacho.com.ofinicaerp.models.ModelGastos;
import android.despacho.com.ofinicaerp.models.ModelGastosGasolina;
import android.despacho.com.ofinicaerp.models.ModelIngresos;
import android.despacho.com.ofinicaerp.models.ModelMantenimiento;
import android.despacho.com.ofinicaerp.models.ModelPagoEmpleado;
import android.despacho.com.ofinicaerp.models.ModelRutas;
import android.despacho.com.ofinicaerp.models.ModelTienda;
import android.despacho.com.ofinicaerp.models.ModelTipoGasto;
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
            Log.d("POST--",post.toString());
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

    public static void resultQueryRutas(String result, List<ModelRutas> listRuta){
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
                    listRuta.add(new ModelRutas(
                            Integer.parseInt(jsonObject.getString("idRuta")),
                            jsonObject.getString("ruta")));

                    //adapter.add("Clave : "+clave+" Nombre : "+ nombre);
                } catch (JSONException e) {
                    Log.e("JSON", e.toString());
                }
            }
        }

    }

    public static void resultQueryCaja(String result, List<ModelCaja> caja){
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
                    caja.add(new ModelCaja(Double.parseDouble(jsonObject.getString("monto"))));

                } catch (JSONException e) {
                    Log.e("JSON", e.toString());
                }
            }
        }

    }

    public static void resultQueryTiendas(String result, List<ModelTienda> listTiendas){
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
                    listTiendas.add(new ModelTienda(
                            Integer.parseInt(jsonObject.getString("idTienda")),
                            jsonObject.getString("tienda"),
                            jsonObject.getString("direccion"),
                            Integer.parseInt(jsonObject.getString("idRuta")),
                            jsonObject.getString("ruta")));

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

    public static String resultQueryIngreso(Application context,String resultTask, List<ModelIngresos> resultIngreso) {
        JSONArray jsonArray = null;
        String result = "";

        Log.d("INGRESO--", resultTask);
        try {
            jsonArray = new JSONArray(resultTask);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String idIngreso = jsonObject.getString("idIngreso");
                        String fecha = jsonObject.getString("fecha");
                        String concepto = jsonObject.getString("concepto");
                        String monto = jsonObject.getString("monto");
                        String idRuta = jsonObject.getString("idRuta");
                        String ruta = jsonObject.getString("ruta");


                        ModelIngresos gastosGasolina = new ModelIngresos(
                                Integer.parseInt(idIngreso),
                                Integer.parseInt(idRuta),
                                fecha,
                                concepto,
                                Double.parseDouble(monto),
                                ruta);
                        resultIngreso.add(gastosGasolina);


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

    public static String resultQueryGastos(Context context, String resultTask, List<ModelGastos> resultGasto) {
        JSONArray jsonArray = null;
        String result = "";

        Log.d("INGRESO--", resultTask);
        try {
            jsonArray = new JSONArray(resultTask);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String idGasto = jsonObject.getString("idGasto");
                        String fecha = jsonObject.getString("fecha");
                        String monto = jsonObject.getString("monto");
                        String idRuta = jsonObject.getString("idRuta");
                        String ruta = jsonObject.getString("ruta");
                        String empleado = jsonObject.getString("empleado");

                        ModelGastos gastos = new ModelGastos(
                                Integer.parseInt(idGasto),
                                Integer.parseInt(idRuta),
                                Double.parseDouble(monto),
                                fecha,
                                Integer.parseInt(idRuta),
                                ruta,
                                empleado);
                        resultGasto.add(gastos);

                    } catch (JSONException e) {
                        Log.e("JSON---", e.toString());
                    }
                }

            } else {

                //result = context.getString(R.string.msg_usiarioIncorrecto);
            }
        } catch (JSONException e) {
            return result = "Ocurrio un error al conectarse al servidor, intentelo de nuevo.";
            //  Log.e("JSON", e.toString());
        }

        return result;
    }

    public static String resultQueryComprobantes(Application context,String resultTask, List<ModelComprobanteGastos> resultComprobantes) {
        JSONArray jsonArray = null;
        String result = "";

        Log.d("INGRESO--", resultTask);
        try {
            jsonArray = new JSONArray(resultTask);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String idComprobante = jsonObject.getString("idComprobante");
                        String fecha = jsonObject.getString("fecha");
                        String concepto = jsonObject.getString("concepto");
                        String monto = jsonObject.getString("monto");
                        String oxxo = jsonObject.getString("oxxo");
                        String categoria = jsonObject.getString("categoria");
                        String tipoComprobante = jsonObject.getString("tipoComprobante");

                        ModelComprobanteGastos gastos = new ModelComprobanteGastos(
                                Integer.parseInt(idComprobante),
                                fecha,
                                concepto,
                                Double.parseDouble(monto),
                                Integer.parseInt(oxxo),
                                categoria,
                                tipoComprobante);
                        resultComprobantes.add(gastos);

                    } catch (JSONException e) {
                        Log.e("JSON---", e.toString());
                    }
                }

            } else {

                result = context.getString(R.string.msg_error);
            }
        } catch (JSONException e) {
            return result = "Ocurrio un error al conectarse al servidor, intentelo de nuevo.";
            //  Log.e("JSON", e.toString());
        }

        return result;
    }

    public static String resultQueryGastosNoComprobados(Application context,String resultTask, List<ModelGastos> resultGasto) {
        JSONArray jsonArray = null;
        String result = "";

        Log.d("INGRESO--", resultTask);
        try {
            jsonArray = new JSONArray(resultTask);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String idGasto = jsonObject.getString("idGasto");
                        String monto = jsonObject.getString("monto");
                        String fecha = jsonObject.getString("fecha");
                        String ruta = jsonObject.getString("ruta");

                        ModelGastos gastos = new ModelGastos(
                                Integer.parseInt(idGasto),
                                Double.parseDouble(monto),
                                fecha,
                                ruta);
                        resultGasto.add(gastos);


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

    public static void resultQueryTipoGasto(String result, List<ModelTipoGasto> listTipoGasto){
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
                    listTipoGasto.add(new ModelTipoGasto(
                            Integer.parseInt(jsonObject.getString("idTipoGasto")),
                            jsonObject.getString("tipoGasto")));

                    //adapter.add("Clave : "+clave+" Nombre : "+ nombre);
                } catch (JSONException e) {
                    Log.e("JSON", e.toString());
                }
            }
        }

    }

    public static String resultQueryNomina(Application context,String resultTask, List<ModelPagoEmpleado> resultNomina) {
        JSONArray jsonArray = null;
        String result = "";

        Log.d("Nomina--", resultTask);
        try {
            jsonArray = new JSONArray(resultTask);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String idNomina = jsonObject.getString("idNomina");
                        String idEmpleado = jsonObject.getString("idEmpleado");
                        String monto = jsonObject.getString("monto");
                        String mes = jsonObject.getString("mes");
                        String semana = jsonObject.getString("semana");
                        String nombreEmpleado = jsonObject.getString("nombreEmpleado");
                        String ano = jsonObject.getString("ano");
                        ModelPagoEmpleado gastos = new ModelPagoEmpleado(
                                Integer.parseInt(idNomina),
                                Integer.parseInt(idEmpleado),
                                Double.parseDouble(monto),
                                Integer.parseInt(mes),
                                Integer.parseInt(semana),
                                ano,
                                nombreEmpleado);
                        resultNomina.add(gastos);

                    } catch (JSONException e) {
                        Log.e("JSON---", e.toString());
                    }
                }

            } else {

                result = context.getString(R.string.msg_error);
            }
        } catch (JSONException e) {
            return result = "Ocurrio un error al conectarse al servidor, intentelo de nuevo.";
            //  Log.e("JSON", e.toString());
        }

        return result;
    }

    public static String resultQueryHonorarios(Application context,String resultTask, List<ModelDespacho_Honorarios> resultHono) {
        JSONArray jsonArray = null;
        String result = "";

        Log.d("HONO--", resultTask);
        try {
            jsonArray = new JSONArray(resultTask);
            if (jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {

                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String idHonorario = jsonObject.getString("idHonorario");
                        String empleadoNomb = jsonObject.getString("empleadoNomb");
                        String monto = jsonObject.getString("monto");
                        String mes = jsonObject.getString("idMes");
                        String ano = jsonObject.getString("ano");
                        String status = jsonObject.getString("status");
                        ModelDespacho_Honorarios honorarios = new ModelDespacho_Honorarios(
                                Integer.parseInt(idHonorario),
                                Integer.parseInt(mes),
                                ano,
                                Integer.parseInt(status),
                                empleadoNomb,
                                Double.parseDouble(monto));
                        resultHono.add(honorarios);

                    } catch (JSONException e) {
                        Log.e("JSON---", e.toString());
                    }
                }

            } else {

                result = context.getString(R.string.msg_error);
            }
        } catch (JSONException e) {
            return result = "Ocurrio un error al conectarse al servidor, intentelo de nuevo.";
            //  Log.e("JSON", e.toString());
        }

        return result;
    }
}
