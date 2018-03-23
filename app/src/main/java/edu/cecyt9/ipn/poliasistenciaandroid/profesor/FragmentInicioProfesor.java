package edu.cecyt9.ipn.poliasistenciaandroid.profesor;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import edu.cecyt9.ipn.poliasistenciaandroid.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInicioProfesor.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentInicioProfesor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInicioProfesor extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    LineChart grafica;

    public FragmentInicioProfesor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInicioProfesor.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInicioProfesor newInstance(String param1, String param2) {
        FragmentInicioProfesor fragment = new FragmentInicioProfesor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vistaInicio = inflater.inflate(R.layout.fragment_inicio_profesor, container, false);
        grafica = vistaInicio.findViewById(R.id.grafica_linechart_asistencia_inicio);
        generarGrafica();
        return vistaInicio;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void generarGrafica(){
        ArrayList<String> meses = new ArrayList<>();
        meses.add("Meses");
        meses.add("Enero");
        meses.add("Febrero");
        meses.add("Marzo");


        ArrayList<Entry> dias = new ArrayList<>();
        dias.add(new Entry(0f, 0f));
        dias.add(new Entry(1f, 0f));
        dias.add(new Entry(2f, 1f));
        dias.add(new Entry(3f, 0f));

        LineDataSet datos = new LineDataSet(dias, "Dias");
        datos.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        datos.setFillColor(ContextCompat.getColor(getContext(), R.color.azul));
        datos.setDrawFilled(true);
        datos.setLineWidth(3f);
        datos.setColor(ContextCompat.getColor(getContext(), R.color.azul));
        datos.setCircleColor(ContextCompat.getColor(getContext(), R.color.azul));
        datos.setCircleRadius(5f);
        LineData todo = new LineData(datos);
        grafica.setData(todo);
        grafica.animateY(1500, Easing.EasingOption.EaseInOutExpo);
        grafica.setTouchEnabled(false);
        grafica.getXAxis().setEnabled(false);
        grafica.getDescription().setText("");
    }
}
