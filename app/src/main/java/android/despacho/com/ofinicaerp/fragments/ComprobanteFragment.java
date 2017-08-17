package android.despacho.com.ofinicaerp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.activity.MenuPrincipal;
import android.despacho.com.ofinicaerp.models.ModelTipoGasto;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;

import java.util.ArrayList;
import java.util.List;

public class ComprobanteFragment extends Fragment {



    public ComprobanteFragment() {
        // Required empty public constructor
    }

    public static ComprobanteFragment newInstance() {
        ComprobanteFragment fragment = new ComprobanteFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comprobante, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init(){

        /*SELECT comprobante_gasto.id_comprobante_gasto,comprobante_gasto.fecha,comprobante_gasto.concepto,comprobante_gasto.monto,
                comprobante_gasto.tipo_gasto_oxxo,comprobante_gasto.categoria,comprobante_gasto.tipo_comprobante
        FROM comprobante_gasto
        WHERE comprobante_gasto.categoria = 'Telefono' AND comprobante_gasto.tipo_gasto_oxxo = 0
        ORDER BY comprobante_gasto.fecha ASC*/
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
