package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.HashMap;

import edu.cecyt9.ipn.poliasistenciaandroid.Configuracion;
import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.Sesion;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;



public class FragmentInicioAlumno extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Sesion sesion;
    String resultado, resultado2;
    LineChart grafica;
    ListView listaHorario;
    TextView txtdiasAsistidos;
    Button botonEstadisticas, botonHorario, botonConfiguraciones;
    graficaGeneralAndroid graficaAsync;
    obtenerHorarioDiaAndroid horarioAsync;
    ArrayList<DatosHorarioAlumno> datos;
    View vistaInicio;
    SwipeRefreshLayout refrescar;
    //HolaSoyCaleb
    public FragmentInicioAlumno() {
        // Required empty public constructor
    }

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
        setRetainInstance(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(vistaInicio != null){
            if((ViewGroup)vistaInicio.getParent()!=null){
                ((ViewGroup)vistaInicio.getParent()).removeView(vistaInicio);
            }
            return vistaInicio;
        }
        vistaInicio = inflater.inflate(R.layout.fragment_inicio_alumno, container, false);
        refrescar = vistaInicio.findViewById(R.id.swipeRefreshLayout);
        refrescar.setColorSchemeResources(R.color.colorPrimary);
        refrescar.setOnRefreshListener(this);
        grafica = vistaInicio.findViewById(R.id.grafica_linechart_asistencia_inicio);
        listaHorario = vistaInicio.findViewById(R.id.listview_horario_dia);
        txtdiasAsistidos = vistaInicio.findViewById(R.id.txt_descripcion_estadistica);
        DatosHorarioAlumno titulo = new DatosHorarioAlumno("Unidad de Aprendizaje", "Hora");
        datos = new ArrayList<>();
        datos.add(titulo);
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

        sesion = new Sesion(getContext());
        graficaAsync = new graficaGeneralAndroid();
        graficaAsync.execute();
        horarioAsync = new obtenerHorarioDiaAndroid();
        horarioAsync.execute();
        return vistaInicio;

    }

    @Override
    public void onPause() {
        super.onPause();
        graficaAsync.cancel(true);
        horarioAsync.cancel(true);
    }

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

    @Override
    public void onRefresh() {
        graficaAsync = new graficaGeneralAndroid();
        graficaAsync.execute();
        horarioAsync = new obtenerHorarioDiaAndroid();
        horarioAsync.execute();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @SuppressLint("StaticFieldLeak")
    public class graficaGeneralAndroid extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/alumno?WSDL";
            String METHOD_NAME = "graficaGeneralAndroid";
            String SOAP_ACTION = "http://servicios/graficaGeneralAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idPer", sesion.getIdPer());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.debug = true;

            try{
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                resultado = response.toString();
                Log.i("Respuesta" ,  resultado);
            }
            catch(Exception error){
                error.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                int mesActual = 0;
                int ciclo = 0;
                String infoMes;
                int diasAsistidos = 0;
                final HashMap<Integer, String> meses = new HashMap<>();
                ArrayList<Entry> dias = new ArrayList<>();

                try{
                    JSONObject info = new JSONObject(resultado);
                    mesActual = Integer.parseInt(info.getString("mes actual"));
                    ciclo = Integer.parseInt(info.getString("Ciclo"));

                    diasAsistidos = Integer.parseInt(info.getString("total asistidos"));
                    String infoDias = "Días asistidos en total: "+diasAsistidos;
                    txtdiasAsistidos.setText(infoDias);

                    meses.put(0, "Meses");
                    dias.add(new Entry(0f, 0f));
                    if(ciclo == 1){
                        int x = 1;
                        for (int i = 8; i <=mesActual; i++) {
                            infoMes = "mes " + i;
                            meses.put(x, nombreMes(i));
                            dias.add(new Entry(x, Integer.parseInt(info.getString(infoMes))));
                            x++;
                        }
                    }
                    else{
                        for (int i = 1; i <=mesActual; i++) {
                            infoMes = "mes " + i;
                            meses.put(i, nombreMes(i));
                            dias.add(new Entry(i, Integer.parseInt(info.getString(infoMes))));
                        }
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

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
                refrescar.setRefreshing(false);
            }
            else{
                Toast.makeText(getContext(), "Error al obtener información, no se puede conectar al servidor", Toast.LENGTH_LONG).show();
                refrescar.setRefreshing(false);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            refrescar.setRefreshing(false);
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class obtenerHorarioDiaAndroid  extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/alumno?WSDL";
            String METHOD_NAME = "obtenerHorarioDiaAndroid";
            String SOAP_ACTION = "http://servicios/obtenerHorarioDiaAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("boleta", sesion.getNum());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.debug = true;

            try{
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                resultado2 = response.toString();
                Log.i("Respuesta" ,  resultado2);
            }
            catch(Exception error){
                error.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                try {
                    JSONArray materias = new JSONArray(resultado2);
                    for (int i = 0; i < materias.length(); i++) {
                        datos.add(new DatosHorarioAlumno(materias.getJSONObject(i).getString("Materia"),
                                materias.getJSONObject(i).getString("Hora")));
                    }
                    HorarioUnidadAdapter unidadAdapter = new HorarioUnidadAdapter(getContext(), R.layout.adapter_view_horario_unidad, datos);
                    listaHorario.setAdapter(unidadAdapter);
                    unidadAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                //Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public String nombreMes(int mes){
        String nombreMes;
        switch (mes) {
            case 1:  nombreMes = "Enero";
                break;
            case 2:  nombreMes = "Febrero";
                break;
            case 3:  nombreMes = "Marzo";
                break;
            case 4:  nombreMes = "Abril";
                break;
            case 5:  nombreMes = "Mayo";
                break;
            case 6:  nombreMes = "Junio";
                break;
            case 7:  nombreMes = "Julio";
                break;
            case 8:  nombreMes = "Agosto";
                break;
            case 9:  nombreMes = "Septiembre";
                break;
            case 10: nombreMes = "Octubre";
                break;
            case 11: nombreMes = "Noviembre";
                break;
            case 12: nombreMes = "Diciembre";
                break;
            default: nombreMes = "Mes invalido";
                break;
        }
        return nombreMes;
    }
}
