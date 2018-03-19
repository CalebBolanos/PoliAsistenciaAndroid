package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.WebViewNotificaciones;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentEstadisticasAlumno.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentEstadisticasAlumno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentEstadisticasAlumno extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ListView listaMeses;
    LineChart graficaGeneral;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragmentEstadisticasAlumno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentEstadisticasAlumno.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentEstadisticasAlumno newInstance(String param1, String param2) {
        FragmentEstadisticasAlumno fragment = new FragmentEstadisticasAlumno();
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
        // Inflate the layout for this fragment

        View vistaEstadisticas = inflater.inflate(R.layout.fragment_estadisticas_alumno, container, false);
        listaMeses = vistaEstadisticas.findViewById(R.id.listview_meses);
        graficaGeneral = vistaEstadisticas.findViewById(R.id.grafica_linechart_asistencia_individual);
        generarGrafica();
        ArrayList<String> arrayMeses = new ArrayList<>();
        for(int i = 1; i<=12; i++){
            arrayMeses.add("Mes" + i);
        }

        ArrayAdapter adaptador = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayMeses);
        listaMeses.setAdapter(adaptador);
        setListViewHeightBasedOnChildren(listaMeses);
        listaMeses.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccionado = listaMeses.getItemAtPosition(i).toString();
                switch (seleccionado){
                    case "":

                        break;
                    default:
                        Intent graficas = new Intent(getActivity(), AsistenciaPorMesAlumno.class);
                        startActivity(graficas);
                        break;
                }

            }
        });
        return vistaEstadisticas;
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

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
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
        graficaGeneral.setData(todo);
        graficaGeneral.animateY(1500, Easing.EasingOption.EaseInOutExpo);
        graficaGeneral.setTouchEnabled(false);
        graficaGeneral.getXAxis().setEnabled(false);
    }
}
