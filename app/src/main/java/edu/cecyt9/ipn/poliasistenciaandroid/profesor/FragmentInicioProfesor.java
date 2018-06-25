package edu.cecyt9.ipn.poliasistenciaandroid.profesor;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import edu.cecyt9.ipn.poliasistenciaandroid.AsistenciaUnidad;
import edu.cecyt9.ipn.poliasistenciaandroid.Configuracion;
import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.Sesion;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.DatosHorarioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.HorarioUnidadAdapter;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;


public class FragmentInicioProfesor extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    LineChart grafica;
    Button botonEstadisticas, botonHorario, botonConfiguraciones;
    ListView listaHorario;
    String resultado, resultado2;
    Sesion sesion;
    TextView txtdiasAsistidos;
    ArrayList<HorarioGrupo> datos;
    graficaGeneralAndroid graficaAsync;
    obtenerHorarioDiaAndroid horarioAsync;

    public FragmentInicioProfesor() {
        // Required empty public constructor
    }

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
        sesion = new Sesion(getContext());

        View vistaInicio = inflater.inflate(R.layout.fragment_inicio_profesor, container, false);
        grafica = vistaInicio.findViewById(R.id.grafica_linechart_asistencia_inicio);
        txtdiasAsistidos = vistaInicio.findViewById(R.id.txt_descripcion_estadistica);

        listaHorario = vistaInicio.findViewById(R.id.listview_horario_dia);
        HorarioGrupo titulo = new HorarioGrupo("Grupo", "Unidad", "Hora");
        datos = new ArrayList<>();
        datos.add(titulo);
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



        botonEstadisticas = vistaInicio.findViewById(R.id.boton_estadisticas);
        botonEstadisticas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InicioProfesor)getActivity()).reemplazarFragment(R.id.navigation_estadisticas);
            }
        });
        botonHorario = vistaInicio.findViewById(R.id.boton_horario);
        botonHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InicioProfesor)getActivity()).reemplazarFragment(R.id.navigation_horario);
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
        graficaAsync = new graficaGeneralAndroid();
        graficaAsync.execute();
        horarioAsync = new obtenerHorarioDiaAndroid();
        horarioAsync.execute();
        return vistaInicio;
        //HorarioGrupo -> HorarioGrupoAdapter
        //DatosGrupos -> GruposAdapter
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class graficaGeneralAndroid extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
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
                        for (int i = 7; i <=mesActual; i++) {
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
            }
            else{
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public class obtenerHorarioDiaAndroid  extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
            String METHOD_NAME = "obtenerHorarioDiaProfesorAndroid";
            String SOAP_ACTION = "http://servicios/obtenerHorarioDiaProfesorAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("numero", sesion.getNum());

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
                final ArrayList<String> ids = new ArrayList<>();
                final ArrayList<String> grupos = new ArrayList<>();
                try {
                    JSONArray materias = new JSONArray(resultado2);
                    for (int i = 0; i < materias.length(); i++) {
                        datos.add(new HorarioGrupo(materias.getJSONObject(i).getString("grupo"),
                                materias.getJSONObject(i).getString("unidad"),
                                materias.getJSONObject(i).getString("hora")));
                        ids.add(i, materias.getJSONObject(i).getString("id"));
                        grupos.add(i, materias.getJSONObject(i).getString("grupo"));
                    }
                    HorarioGrupoAdapter adaptador = new HorarioGrupoAdapter(getContext(), R.layout.adapter_view_horario_grupo, datos);
                    listaHorario.setAdapter(adaptador);
                    listaHorario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String seleccionado = listaHorario.getItemAtPosition(i).toString();
                            switch (seleccionado){
                                case "":

                                    break;
                                default:
                                    Intent graficas = new Intent(getActivity(), AsistenciaUnidad.class);
                                    graficas.putExtra("id", ids.get(i-1));
                                    graficas.putExtra("grupo", grupos.get(i-1));
                                    startActivity(graficas);
                                    break;
                            }
                        }
                    });
                    adaptador.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
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
