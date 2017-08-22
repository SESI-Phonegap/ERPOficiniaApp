package android.despacho.com.ofinicaerp.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.despacho.com.ofinicaerp.activity.MenuPrincipal;
import android.despacho.com.ofinicaerp.models.ModelComprobanteGastos;
import android.despacho.com.ofinicaerp.models.ModelTipoGasto;
import android.despacho.com.ofinicaerp.utils.Constants;
import android.despacho.com.ofinicaerp.utils.UtilsDML;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class ComprobanteFragment extends Fragment {

    private ProgressDialog progressBar;
    private EditText et_fechaFin;
    private EditText et_fechaIni;
    private RadioGroup radioGroup;
    private RadioButton radioButton_si, radioButton_no, radioButton_todos;
    private Spinner spinner_tipo_comprobante, spinner_categoria;
    private RecyclerView recyclerView;
    private List<ModelComprobanteGastos>comprobanteGastosList;

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
        et_fechaFin = (EditText) getActivity().findViewById(R.id.et_fechaFin);
        et_fechaIni = (EditText) getActivity().findViewById(R.id.et_fechaIni);
        radioGroup = (RadioGroup) getActivity().findViewById(R.id.radioGroup);
        radioButton_todos = (RadioButton) getActivity().findViewById(R.id.radioButton_todos);
        radioButton_si = (RadioButton) getActivity().findViewById(R.id.radioButton_si);
        radioButton_no = (RadioButton) getActivity().findViewById(R.id.radioButton_no);
        spinner_tipo_comprobante = (Spinner) getActivity().findViewById(R.id.spinner_tipo_comprobante);
        spinner_categoria = (Spinner) getActivity().findViewById(R.id.spinner_categoria);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recycler_view_comprobantes);
        progressBar = new ProgressDialog(getActivity());
        progressBar.setMessage("Cargando...");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.setIndeterminate(true);

        /*SELECT comprobante_gasto.id_comprobante_gasto,comprobante_gasto.fecha,comprobante_gasto.concepto,comprobante_gasto.monto,
                comprobante_gasto.tipo_gasto_oxxo,comprobante_gasto.categoria,comprobante_gasto.tipo_comprobante
        FROM comprobante_gasto
        WHERE comprobante_gasto.categoria = 'Telefono'
        AND comprobante_gasto.tipo_gasto_oxxo = 0
        AND comprobante_gasto.fecha
	    BETWEEN '$sFechaIni' AND '$sFechaFin'
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
