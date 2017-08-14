package android.despacho.com.ofinicaerp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.despacho.com.ofinicaerp.R;
import android.widget.TextView;

import static android.despacho.com.ofinicaerp.activity.MenuPrincipal.montoActual_Gasto;

public class CajaFragment extends Fragment {
    private TextView tv_caja;

    public CajaFragment() {
        // Required empty public constructor
    }

    public static CajaFragment newInstance() {
        CajaFragment fragment = new CajaFragment();
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
        return inflater.inflate(R.layout.fragment_caja, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    public void init(){
        tv_caja = (TextView) getActivity().findViewById(R.id.tv_caja);
        tv_caja.setText(getString(R.string.pesoscaja,String.valueOf(montoActual_Gasto)));
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
