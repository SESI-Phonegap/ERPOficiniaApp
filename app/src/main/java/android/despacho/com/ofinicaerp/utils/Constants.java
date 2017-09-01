package android.despacho.com.ofinicaerp.utils;

public class Constants {

    //URLs -----------------------------------------------
    public static String URL_BASE = "http://despachogonzalez.com.mx/erpoficina/";
    public static String URL_LOGIN = "http://despachogonzalez.com.mx/erpoficina/userlogin.php";
    public static String URL_ADD_VEHICULO = "http://despachogonzalez.com.mx/erpoficina/newvehiculo.php";
    public static String URL_ADD_EMPLEADO = "http://despachogonzalez.com.mx/erpoficina/newempleado.php";
    public static String URL_ADD_CLIENTE = "http://despachogonzalez.com.mx/erpoficina/newcliente.php";
    public static String URL_ADD_GASTO_GASOLINA = "http://despachogonzalez.com.mx/erpoficina/addgasolina.php";
    public static String URL_ADD_MANTENIMIENTO = "http://despachogonzalez.com.mx/erpoficina/newmantenimiento.php";
    public static String URL_ADD_RUTA = "http://despachogonzalez.com.mx/erpoficina/newruta.php";
    public static String URL_ADD_TIENDA = "http://despachogonzalez.com.mx/erpoficina/newtienda.php";
    public static String URL_ADD_INGRESO = "http://despachogonzalez.com.mx/erpoficina/newingreso.php";
    public static String URL_ADD_GASTO = "http://despachogonzalez.com.mx/erpoficina/newgasto.php";
    public static String URL_UPDATE_CAJA = "http://despachogonzalez.com.mx/erpoficina/updatecaja.php";
    public static String URL_ADD_COMPROBANTE = "http://despachogonzalez.com.mx/erpoficina/newcomprobante.php";
    public static String URL_ADD_NOMINA = "http://despachogonzalez.com.mx/erpoficina/newnomina.php";
    public static String URL_ADD_HONORARIO = "http://despachogonzalez.com.mx/erpoficina/newhonorario.php";


    public static String URL_QUERY_EMPLEADO = "http://despachogonzalez.com.mx/erpoficina/queryempleado.php";
    public static String URL_QUERY_CLIENTE = "http://despachogonzalez.com.mx/erpoficina/querycliente.php";
    public static String URL_QUERY_VEHICULO = "http://despachogonzalez.com.mx/erpoficina/queryvehiculo.php";
    public static String URL_QUERY_RUTAS = "http://despachogonzalez.com.mx/erpoficina/queryrutas.php";
    public static String URL_QUERY_TIENDAS = "http://despachogonzalez.com.mx/erpoficina/query_tiendas.php";
    public static String URL_QUERY_GASTO_GASOLINA = "http://despachogonzalez.com.mx/erpoficina/query_gastoGasolina.php";
    public static String URL_QUERY_MANTENIMIENTO = "http://despachogonzalez.com.mx/erpoficina/query_mantenimiento.php";
    public static String URL_QUERY_INGRESO = "http://despachogonzalez.com.mx/erpoficina/query_ingresos.php";
    public static String URL_QUERY_GASTOS = "http://despachogonzalez.com.mx/erpoficina/query_gasto.php";
    public static String URL_QUERY_CAJA = "http://despachogonzalez.com.mx/erpoficina/query_caja.php";
    public static String URL_QUERY_TIPO_GASTO = "http://despachogonzalez.com.mx/erpoficina/query_tipogasto.php";
    public static String URL_QUERY_GASTO_NO_COMPROBADOS = "http://despachogonzalez.com.mx/erpoficina/query_gasto_no_comp.php";
    public static String URL_QUERY_COMPROBANTES_GASTOS = "http://despachogonzalez.com.mx/erpoficina/query_comprobantes.php";
    public static String URL_QUERY_GASTOS_DETAILS_BY_ID = "http://despachogonzalez.com.mx/erpoficina/query_gastos_details.php";
    public static String URL_QUERY_NOMINA = "http://despachogonzalez.com.mx/erpoficina/query_nomina.php";
    public static String URL_QUERY_HONORARIOS = "http://despachogonzalez.com.mx/erpoficina/query_honorarios.php";

    public static String URL_UPDATE_HONORARIOS = "http://despachogonzalez.com.mx/erpoficina/update_honorarios.php";

    public static String POST_GASOLINA = "gasolina";
    public static String POST_VEHICULO = "vehiculo";
    public static String POST_EMPLEADO = "empleado";
    public static String POST_RUTA = "ruta";
    public static String POST_TIENDA = "tienda";
    public static String POST_CLIENTE = "cliente";
    public static String POST_FECHA = "fecha";
    public static String POST_MANTENIMIENTO = "mantenimiento";
    public static String POST_INGRESO = "ingreso";
    public static String POST_GASTO = "gasto";
    public static String POST_CAJA = "caja";
    public static String POST_COMPROBANTE = "comprobante";
    public static String POST_NOMINA = "nomina";
    public static String POST_HONORARIOS = "honorarios";


    public static String PUTEXTRA_ID_EMPLEADO = "id_empleado";
    public static String PUTEXTRA_TIPO_USER = "tipo_user";
    public static String ERROR_1001 = "1001";
    public static String ADMIN = "ADMIN";

    public static final int TAKE_PICTURE_VEHICULO = 9001;
    public static final int TAKE_PICTURE_EMPLEADO = 9002;
    public static final int PICK_IMAGE_VEHICULO = 8001;
    public static final int PICK_IMAGE_EMPLEADO = 8002;

    public static final String VEHICULO = "vehiculo";
    public static final String EMPLEADO = "empleado";

    public static final String REFRESH = "refresh";
    public static final String REFRESH_FRAGMENT_VEHICULO = "refresh_fragment_vehiculo";
    public static final String REFRESH_FRAGMENT_EMPLEADO = "refresh_fragment_empleado";
    public static final String REFRESH_FRAGMENT_MANTENIMIENTO = "refresh_fragment_mantenimiento";
    public static final String REFRESH_FRAGMENT_INGRESO = "refresh_fragment_ingreso";
    public static final String REFRESH_FRAGMENT_COMPROBANTE = "refresh_fragment_comprobante";

    public static final String PAYMENT_NUMBER_FORMAT_REGEX = "[$,.]";
    public static final String PAYMENT_NUMBER_FORMAT_REGEX_POINT = "[$,]";
    public static final String STRING_EMPTY = "";
    public static final String PAYMENT_NUMBER_FORMAT = "#,###,##0.00";
}
