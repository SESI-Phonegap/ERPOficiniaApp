package android.despacho.com.ofinicaerp.utils;


import android.app.Application;
import android.app.DatePickerDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.R;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cz.msebera.android.httpclient.extras.Base64;

public class Utils {

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
        bm.compress(Bitmap.CompressFormat.JPEG,80,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.URL_SAFE | Base64.NO_WRAP);
        return encImage;
    }

    public static Bitmap rotateBitmap(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source,0,0,source.getWidth(),source.getHeight(),matrix,true);
    }
}
