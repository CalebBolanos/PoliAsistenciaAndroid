package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
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
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

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

import edu.cecyt9.ipn.poliasistenciaandroid.profesor.AsistenciaIndividualProfesor;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;

public class AsistenciaUnidad extends AppCompatActivity {

    ListView listaMeses;
    LineChart graficaUnidad;
    TextView nombreUnidad, grupo, semestre, turno, especialidad, inscritos;
    String resultado, resultado2, idString, grupoString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_unidad);

        Intent intent = getIntent();
        idString = intent.getStringExtra("id");
        grupoString = intent.getStringExtra("grupo");


        nombreUnidad = findViewById(R.id.txt_nombre_unidad);
        grupo = findViewById(R.id.txt_grupo);
        semestre = findViewById(R.id.txt_semestre);
        turno = findViewById(R.id.txt_turno);
        especialidad = findViewById(R.id.txt_especialidad);
        inscritos = findViewById(R.id.txt_inscritos);

        Toolbar toolbar = findViewById(R.id.toolbar_asistencia_unidad);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(grupoString);
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listaMeses = findViewById(R.id.listview_meses_unidad);
        graficaUnidad = findViewById(R.id.linechart_asistencia_unidad);

        new asistenciaUnidadMesAndroid().execute();
        new informacionUnidadAndroid().execute();

    }

    public void generarGrafica(){
        final HashMap<Integer, String> meses = new HashMap<>();
        meses.put(0, "Meses");
        meses.put(1, "Enero");
        meses.put(2, "Febrero");
        meses.put(3, "Marzo");

        //Asistido

        ArrayList<Entry> diasAsistidos = new ArrayList<>();
        diasAsistidos.add(new Entry(0f, 0f));
        diasAsistidos.add(new Entry(1f, 0f));
        diasAsistidos.add(new Entry(2f, 1f));
        diasAsistidos.add(new Entry(3f, 0f));

        LineDataSet datosAsistido = new LineDataSet(diasAsistidos, "Dias Asistidos");
        datosAsistido.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        datosAsistido.setFillColor(ContextCompat.getColor(this, R.color.azul));
        datosAsistido.setDrawFilled(true);
        datosAsistido.setLineWidth(3f);
        datosAsistido.setColor(ContextCompat.getColor(this, R.color.azul));
        datosAsistido.setCircleColor(ContextCompat.getColor(this, R.color.azul));
        datosAsistido.setCircleRadius(5f);

        //faltado

        ArrayList<Entry> diasFaltados = new ArrayList<>();
        diasFaltados.add(new Entry(0f, 0f));
        diasFaltados.add(new Entry(1f, 1f));
        diasFaltados.add(new Entry(2f, 0f));
        diasFaltados.add(new Entry(3f, 1f));

        LineDataSet datosFalta = new LineDataSet(diasFaltados, "Dias Faltados");
        datosFalta.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        datosFalta.setFillColor(ContextCompat.getColor(this, R.color.rojoGrafica));
        datosFalta.setDrawFilled(true);
        datosFalta.setLineWidth(3f);
        datosFalta.setColor(ContextCompat.getColor(this, R.color.rojoGrafica));
        datosFalta.setCircleColor(ContextCompat.getColor(this, R.color.rojoGrafica));
        datosFalta.setCircleRadius(5f);

        ArrayList<ILineDataSet> datasets = new ArrayList<>();
        datasets.add(datosFalta);
        datasets.add(datosAsistido);
        XAxis valoresx = graficaUnidad.getXAxis();
        valoresx.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return meses.get((int)value);
            }
        });
        valoresx.setLabelCount(meses.size(),true);
        LineData todo = new LineData(datasets);
        graficaUnidad.setData(todo);
        graficaUnidad.animateY(1500, Easing.EasingOption.EaseInOutExpo);
        graficaUnidad.setTouchEnabled(false);
        graficaUnidad.getDescription().setText("");
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

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void abrirAsistenciaUnidadDia(View view) {
        Intent AsistenciaUnidad = new Intent(AsistenciaUnidad.this, AsistenciaUnidadDia.class);
        AsistenciaUnidad.putExtra("id", idString);
        startActivity(AsistenciaUnidad);
    }


    public class informacionUnidadAndroid extends  AsyncTask<String, String, Boolean>{

        @Override
        protected Boolean doInBackground(String... params) {

            String valor = "";
            JSONObject datos = new JSONObject();
            try {
                datos.put("grupo", grupoString);
                datos.put("id", idString);
                valor = datos.toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
            String METHOD_NAME = "informacionUnidadAndroid";
            String SOAP_ACTION = "http://servicios/informacionUnidadAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("datos", valor);//sesion.getIdPer()

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
                String unidadString = "", semestreString ="", turnoString = "", especialidadString = "", inscritosString = "";
                try {
                    JSONObject datos = new JSONObject(resultado);
                    unidadString = datos.getString("unidad");
                    semestreString = datos.getString("semestre");
                    turnoString = datos.getString("turno");
                    especialidadString = datos.getString("especialidad");
                    inscritosString = datos.getString("alumnos");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                nombreUnidad.setText(unidadString);
                grupo.setText(grupoString);
                semestre.setText(semestreString);
                turno.setText(turnoString);
                especialidad.setText(especialidadString);
                inscritos.setText(inscritosString);

            }
            else{

            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    public class asistenciaUnidadMesAndroid extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
            String METHOD_NAME = "asistenciaUnidadMesAndroid";
            String SOAP_ACTION = "http://servicios/asistenciaUnidadMesAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idUnidad", idString);

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
                int mesActual = 0;
                int ciclo = 0;
                String infoMesAsistido, infoMesFaltado;
                ArrayList<String> arrayMeses = new ArrayList<>();
                final HashMap<Integer, String> meses = new HashMap<>();
                ArrayList<Entry> diasAsistidos = new ArrayList<>();
                ArrayList<Entry> diasFaltados = new ArrayList<>();

                try{
                    JSONObject info = new JSONObject(resultado2);
                    mesActual = Integer.parseInt(info.getString("mes actual"));
                    ciclo = Integer.parseInt(info.getString("Ciclo"));

                    meses.put(0, "Meses");
                    diasAsistidos.add(new Entry(0f, 0f));
                    diasFaltados.add(new Entry(0f, 0f));
                    if(ciclo == 1){
                        int x = 1;
                        for (int i = 7; i <=mesActual; i++) {
                            infoMesAsistido = "mesAsistido " + i;
                            infoMesFaltado = "mesFaltado " + i;
                            arrayMeses.add(nombreMes(i));
                            meses.put(x, nombreMes(i));
                            diasAsistidos.add(new Entry(x, Float.parseFloat(info.getString(infoMesAsistido))));
                            diasFaltados.add(new Entry(x, Float.parseFloat(info.getString(infoMesFaltado))));
                            x++;
                        }
                    }
                    else{
                        for (int i = 1; i <=mesActual; i++) {
                            infoMesAsistido = "mesAsistido " + i;
                            infoMesFaltado = "mesFaltado " + i;
                            meses.put(i, nombreMes(i));
                            arrayMeses.add(nombreMes(i));
                            diasAsistidos.add(new Entry(i, Float.parseFloat(info.getString(infoMesAsistido))));
                            diasFaltados.add(new Entry(i, Float.parseFloat(info.getString(infoMesFaltado))));
                        }
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                    return;
                }

                ArrayAdapter adaptador = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrayMeses);
                listaMeses.setAdapter(adaptador);
                listaMeses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        String seleccionado = listaMeses.getItemAtPosition(i).toString();
                        switch (seleccionado){
                            case "":

                                break;
                            default:
                                Intent graficasUnidadMes = new Intent(AsistenciaUnidad.this, AsistenciaUnidadMes.class);
                                graficasUnidadMes.putExtra("mes", seleccionado);
                                graficasUnidadMes.putExtra("id", idString);
                                startActivity(graficasUnidadMes);
                                break;
                        }
                    }
                });
                setListViewHeightBasedOnChildren(listaMeses);
                listaMeses.setFocusable(false);
                adaptador.notifyDataSetChanged();

                LineDataSet datosAsistido = new LineDataSet(diasAsistidos, "Dias Asistidos");
                datosAsistido.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                datosAsistido.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.azul));
                datosAsistido.setDrawFilled(true);
                datosAsistido.setLineWidth(3f);
                datosAsistido.setColor(ContextCompat.getColor(getApplicationContext(), R.color.azul));
                datosAsistido.setCircleColor(ContextCompat.getColor(getApplicationContext(), R.color.azul));
                datosAsistido.setCircleRadius(5f);

                LineDataSet datosFalta = new LineDataSet(diasFaltados, "Dias Faltados");
                datosFalta.setMode(LineDataSet.Mode.CUBIC_BEZIER);
                datosFalta.setFillColor(ContextCompat.getColor(getApplicationContext(), R.color.rojoGrafica));
                datosFalta.setDrawFilled(true);
                datosFalta.setLineWidth(3f);
                datosFalta.setColor(ContextCompat.getColor(getApplicationContext(), R.color.rojoGrafica));
                datosFalta.setCircleColor(ContextCompat.getColor(getApplicationContext(), R.color.rojoGrafica));
                datosFalta.setCircleRadius(5f);

                ArrayList<ILineDataSet> datasets = new ArrayList<>();
                datasets.add(datosFalta);
                datasets.add(datosAsistido);
                XAxis valoresx = graficaUnidad.getXAxis();
                valoresx.setValueFormatter(new IAxisValueFormatter() {
                    @Override
                    public String getFormattedValue(float value, AxisBase axis) {
                        return meses.get((int)value);
                    }
                });
                valoresx.setLabelCount(meses.size(),true);
                LineData todo = new LineData(datasets);
                graficaUnidad.setData(todo);
                graficaUnidad.animateY(1500, Easing.EasingOption.EaseInOutExpo);
                graficaUnidad.setTouchEnabled(false);
                graficaUnidad.getDescription().setText("");
            }
            else{
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
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
