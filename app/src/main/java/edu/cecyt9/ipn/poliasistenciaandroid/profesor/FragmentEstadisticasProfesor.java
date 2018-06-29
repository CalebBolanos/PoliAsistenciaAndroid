package edu.cecyt9.ipn.poliasistenciaandroid.profesor;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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
import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.AsistenciaPorMes;
import edu.cecyt9.ipn.poliasistenciaandroid.Sesion;
import edu.cecyt9.ipn.poliasistenciaandroid.Unidad;
import edu.cecyt9.ipn.poliasistenciaandroid.UnidadesAdapter;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;


public class FragmentEstadisticasProfesor extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView listaGrupo;
    LineChart graficaGeneral;
    Button botonProfesor;
    Sesion sesion;
    String resultado, resultado2;
    graficaGeneralAndroid graficaAsync;
    unidadesAndroid unidadesAsync;
    View vistaEstadisticas;
    SwipeRefreshLayout refrescar;

    public FragmentEstadisticasProfesor() {
        // Required empty public constructor
    }

    public static FragmentEstadisticasProfesor newInstance(String param1, String param2) {
        FragmentEstadisticasProfesor fragment = new FragmentEstadisticasProfesor();
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
        if(vistaEstadisticas != null){
            if((ViewGroup)vistaEstadisticas.getParent()!=null){
                ((ViewGroup)vistaEstadisticas.getParent()).removeView(vistaEstadisticas);
            }
            return vistaEstadisticas;
        }
        vistaEstadisticas = inflater.inflate(R.layout.fragment_estadisticas_profesor, container, false);
        refrescar = vistaEstadisticas.findViewById(R.id.swipeRefreshLayout);
        refrescar.setColorSchemeResources(R.color.colorPrimary);
        refrescar.setOnRefreshListener(this);
        sesion = new Sesion(getContext());
        listaGrupo = vistaEstadisticas.findViewById(R.id.listview_grupos);
        graficaGeneral = vistaEstadisticas.findViewById(R.id.grafica_linechart_asistencia_individual);
        botonProfesor = vistaEstadisticas.findViewById(R.id.boton_profesor);

        /*
        ArrayList<String> arrayGrupos = new ArrayList<>();
        for(int i = 1; i<=50; i++){
            arrayGrupos.add("Grupo " + i);
        }*/


        botonProfesor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent asistencia = new Intent(getActivity(), AsistenciaIndividualProfesor.class);
                startActivity(asistencia);
            }
        });
        graficaAsync = new graficaGeneralAndroid();
        graficaAsync.execute();
        unidadesAsync = new unidadesAndroid();
        unidadesAsync.execute();
        return vistaEstadisticas;
    }

    @Override
    public void onPause() {
        super.onPause();
        graficaAsync.cancel(true);
        unidadesAsync.cancel(true);
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
        unidadesAsync = new unidadesAndroid();
        unidadesAsync.execute();
    }

    public interface OnFragmentInteractionListener {
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

    @SuppressLint("StaticFieldLeak")
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
                final HashMap<Integer, String> meses = new HashMap<>();
                ArrayList<Entry> dias = new ArrayList<>();

                try{
                    JSONObject info = new JSONObject(resultado);
                    mesActual = Integer.parseInt(info.getString("mes actual"));
                    ciclo = Integer.parseInt(info.getString("Ciclo"));

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

                XAxis valoresx = graficaGeneral.getXAxis();
                valoresx.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return meses.get((int)value);
                    }
                });
                valoresx.setLabelCount(meses.size(),true);

                graficaGeneral.setData(todo);
                graficaGeneral.animateY(1500, Easing.EasingOption.EaseInOutExpo);
                graficaGeneral.setTouchEnabled(false);
                graficaGeneral.getDescription().setText("");
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
    public class unidadesAndroid extends  AsyncTask<String, String, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {
            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
            String METHOD_NAME = "unidadesAndroid";
            String SOAP_ACTION = "http://servicios/unidadesAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idPer", sesion.getIdPer());//sesion.getIdPer()

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
                    JSONArray datos = new JSONArray(resultado2);
                    ArrayList<Unidad> unidades = new ArrayList<Unidad>();
                    for(int i = 0; i<datos.length(); i++){
                        unidades.add(new Unidad(datos.getJSONObject(i).getString("grupo"),
                                datos.getJSONObject(i).getString("unidad"),
                                datos.getJSONObject(i).getString("id")));
                    }

                    final UnidadesAdapter unidadesAdapter = new UnidadesAdapter(getContext(), unidades);
                    listaGrupo.setAdapter(unidadesAdapter);
                    setListViewHeightBasedOnChildren(listaGrupo);
                    listaGrupo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            String id = unidadesAdapter.obtenerId(i);
                            switch (id){
                                case "":

                                    break;
                                default:
                                    Toast.makeText(getContext(), ""+id, Toast.LENGTH_SHORT).show();
                                    Intent graficas = new Intent(getActivity(), AsistenciaUnidad.class);
                                    graficas.putExtra("id", id);
                                    graficas.putExtra("grupo", unidadesAdapter.obtenerGrupo(i));
                                    startActivity(graficas);
                                    break;
                            }

                        }
                    });

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            else{

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
