package edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia;

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

import edu.cecyt9.ipn.poliasistenciaandroid.Configuracion;
import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.profesor.HorarioGrupo;
import edu.cecyt9.ipn.poliasistenciaandroid.profesor.HorarioGrupoAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragementInicioJefeAcademia.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragementInicioJefeAcademia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragementInicioJefeAcademia extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LineChart grafica;
    Button botonEstadisticas, botonHorario, botonConfiguraciones;
    ListView listaHorario;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FragementInicioJefeAcademia() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragementInicioJefeAcademia.
     */
    // TODO: Rename and change types and number of parameters
    public static FragementInicioJefeAcademia newInstance(String param1, String param2) {
        FragementInicioJefeAcademia fragment = new FragementInicioJefeAcademia();
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
        View vista = inflater.inflate(R.layout.fragment_inicio_jefe_academia, container, false);
        grafica = vista.findViewById(R.id.grafica_linechart_asistencia_inicio);
        generarGrafica();
        botonEstadisticas = vista.findViewById(R.id.boton_estadisticas);
        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InicioJefe)getActivity()).reemplazarFragment(R.id.navigation_estadisticas);
            }
        });
        botonHorario = vista.findViewById(R.id.boton_horario);
        botonHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InicioJefe)getActivity()).reemplazarFragment(R.id.navigation_horario);
            }
        });
        botonConfiguraciones = vista.findViewById(R.id.boton_configuracion);
        botonConfiguraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent configuracion = new Intent(getActivity(), Configuracion.class);
                startActivity(configuracion);
            }
        });
        listaHorario = vista.findViewById(R.id.listview_horario_dia);
        HorarioGrupo titulo = new HorarioGrupo("Grupo", "Unidad", "Hora");
        ArrayList<HorarioGrupo> datos = new ArrayList<>();
        datos.add(titulo);
        for (int i = 0; i <10 ; i++) {
            HorarioGrupo grupox = new HorarioGrupo("Grupo "+i, "Unidad "+i, "Hora");
            datos.add(grupox);
            grupox = null;

        }
        HorarioGrupoAdapter adaptador = new HorarioGrupoAdapter(getContext(), R.layout.adapter_view_horario_grupo, datos);
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
        return vista;
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
