package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;

import edu.cecyt9.ipn.poliasistenciaandroid.AsistenciaPorMes;
import edu.cecyt9.ipn.poliasistenciaandroid.Configuracion;
import edu.cecyt9.ipn.poliasistenciaandroid.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentInicioAlumno.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentInicioAlumno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentInicioAlumno extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    LineChart grafica;
    ListView listaHorario;
    Button botonEstadisticas, botonHorario, botonConfiguraciones;
//HolaSoyCaleb
    public FragmentInicioAlumno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentInicioAlumno.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentInicioAlumno newInstance(String param1, String param2) {
        FragmentInicioAlumno fragment = new FragmentInicioAlumno();
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vistaInicio = inflater.inflate(R.layout.fragment_inicio_alumno, container, false);
        grafica = vistaInicio.findViewById(R.id.grafica_linechart_asistencia_inicio);
        listaHorario = vistaInicio.findViewById(R.id.listview_horario_dia);
        DatosHorarioAlumno titulo = new DatosHorarioAlumno("Unidad de Aprendizaje", "Hora");
        ArrayList<DatosHorarioAlumno> datos = new ArrayList<>();
        datos.add(titulo);
        for (int i = 0; i <9 ; i++) {
            DatosHorarioAlumno unidadx = new DatosHorarioAlumno("Unidad"+i, "00:00");
            datos.add(unidadx);
            unidadx = null;
        }
        HorarioUnidadAdapter adaptador = new HorarioUnidadAdapter(getContext(), R.layout.adapter_view_horario_unidad, datos);
        listaHorario.setAdapter(adaptador);
        listaHorario.setFocusable(false);
        listaHorario.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            }
        });
        botonEstadisticas = vistaInicio.findViewById(R.id.boton_estadisticas);
        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InicioAlumno)getActivity()).reemplazarFragment(R.id.navigation_estadisticas);
            }
        });
        botonHorario = vistaInicio.findViewById(R.id.boton_horario);
        botonHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InicioAlumno)getActivity()).reemplazarFragment(R.id.navigation_horario);
            }
        });
        botonConfiguraciones = vistaInicio.findViewById(R.id.boton_configuracion);
        botonConfiguraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent configuracion = new Intent(getActivity(), Configuracion.class);
                startActivity(configuracion);
            }
        });
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
        final HashMap<Integer, String> meses = new HashMap<>();
        meses.put(0, "Meses");
        meses.put(1, "Enero");
        meses.put(2, "Febrero");
        meses.put(3, "Marzo");


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

        XAxis valoresx = grafica.getXAxis();
        valoresx.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return meses.get((int)value);
            }
        });
        valoresx.setLabelCount(meses.size(),true);

        grafica.setData(todo);
        grafica.animateY(1500, Easing.EasingOption.EaseInOutExpo);
        grafica.setTouchEnabled(false);
        grafica.getDescription().setText("");
    }
}
