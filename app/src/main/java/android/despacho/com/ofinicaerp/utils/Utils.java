package android.despacho.com.ofinicaerp.utils;


import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.R;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.extras.Base64;

public class Utils {

    public static String toJsonIdGasto(String idGasto){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("idGasto", idGasto);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
    public static String toJsonRangoFecha(String fechaInicial, String fechaFinal){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("fechaIni", fechaInicial);
            jsonObject.put("fechaFin", fechaFinal);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String toJsonMantenimientoFecha(String idVehiculo,String fechaInicial, String fechaFinal){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("idVehiculo", idVehiculo);
            jsonObject.put("fechaIni", fechaInicial);
            jsonObject.put("fechaFin", fechaFinal);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String toJsonIngresoFecha(String idRuta,String fechaInicial, String fechaFinal){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("idRuta", idRuta);
            jsonObject.put("fechaIni", fechaInicial);
            jsonObject.put("fechaFin", fechaFinal);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static String toJsonComprobante(String categoria, String tipoComprobante, int oxxo , String fechaInicial, String fechaFinal){
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("categoria", categoria);
            jsonObject.put("tipoComprobante", tipoComprobante);
            jsonObject.put("oxxo", oxxo);
            jsonObject.put("fechaIni", fechaInicial);
            jsonObject.put("fechaFin", fechaFinal);

            return jsonObject.toString();
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public static void showDialogDate(Context context, final EditText editText) {
        final SimpleDateFormat dateFormatter;
        dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                editText.setText(dateFormatter.format(newDate.getTime()));
            }
        },year,month,day);
        datePickerDialog.show();
    }

    public static String encodeImageBase64(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.URL_SAFE | Base64.NO_WRAP);
        return encImage;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
    }

    public static void proccessResult(Context context, String result) {
        if (result.contains("OK")) {
            Toast.makeText(context, context.getString(R.string.msg_success), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, context.getString(R.string.msg_error) + result, Toast.LENGTH_LONG).show();
        }
    }

    public static double convertToDouble(String value) {
        if(value == null || value.trim().isEmpty()) return 0.0;
        try {
            DecimalFormat format = new DecimalFormat(Constants.PAYMENT_NUMBER_FORMAT, new DecimalFormatSymbols(Locale.US));
            return format.parse(value).doubleValue();
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0.0;
        }
        //     return 0.0;
    }

    public static String parseToString(double value) {
        try {
            DecimalFormat format = new DecimalFormat(Constants.PAYMENT_NUMBER_FORMAT, new DecimalFormatSymbols(Locale.US));
            return format.format(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return Constants.STRING_EMPTY;
    }


}
