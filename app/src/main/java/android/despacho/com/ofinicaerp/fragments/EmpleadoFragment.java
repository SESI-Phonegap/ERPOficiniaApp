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

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EmpleadoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EmpleadoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EmpleadoFragment extends Fragment {



    public EmpleadoFragment() {
        // Required empty public constructor
    }

    public static EmpleadoFragment newInstance() {
        EmpleadoFragment fragment = new EmpleadoFragment();
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
        return inflater.inflate(R.layout.fragment_empleado, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
