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
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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
import java.util.List;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;

public class AsistenciaUnidadDia extends AppCompatActivity {

    BarChart graficaBarraUnidad;
    ListView datosAsistencia;
    String idString, resultado, resultado2;
    infoasistenciaUnidadDiaAndroid infoAsync;
    asistenciaUnidadDiaAndroid asistenciaAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_unidad_dia);

        Intent intent = getIntent();
        idString = intent.getStringExtra("id");
        Toolbar toolbar = findViewById(R.id.toolbar_asistencia_unidad_dia);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Asistencia por día");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        graficaBarraUnidad = findViewById(R.id.grafica_barra_asistencia_unidad_dia);
        datosAsistencia = findViewById(R.id.listview_asistencia_unidad_dia);
        infoAsync = new infoasistenciaUnidadDiaAndroid();
        infoAsync.execute();
        asistenciaAsync = new asistenciaUnidadDiaAndroid();
        asistenciaAsync.execute();

    }

    public void crearGrafica(){
        graficaBarraUnidad = findViewById(R.id.grafica_barra_asistencia_unidad_dia);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public class infoasistenciaUnidadDiaAndroid extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
            String METHOD_NAME = "infoasistenciaUnidadDiaAndroid";
            String SOAP_ACTION = "http://servicios/infoasistenciaUnidadDiaAndroid";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idUnidad", idString);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.debug = true;

            try{
                ht.call(SOAP_ACTION, envelope);
                String a = ht.requestDump;
                String b = ht.responseDump;
                Log.println(Log.INFO, "request", a);
                Log.println(Log.INFO, "response", b);
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
                DatosAsistenciaUnidadDia titulo = new DatosAsistenciaUnidadDia("Boleta", "Nombre", "Asistio");
                ArrayList <DatosAsistenciaUnidadDia> datos = new ArrayList<>();
                datos.add(titulo);
                try {
                    JSONArray info = new JSONArray(resultado);
                    for (int i = 0; i < info.length(); i++) {
                        DatosAsistenciaUnidadDia filax = new DatosAsistenciaUnidadDia(info.getJSONObject(i).getString("boleta"),
                                info.getJSONObject(i).getString("nombre"),
                                info.getJSONObject(i).getString("asistecia"));
                        datos.add(filax);
                        filax = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AsistenciaUnidadDiaAdapter adaptador = new AsistenciaUnidadDiaAdapter(AsistenciaUnidadDia.this, R.layout.adapter_view_asistencia_unidad_dia, datos);
                datosAsistencia.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(datosAsistencia);
                datosAsistencia.setFocusable(false);

            }
            else{
                Toast.makeText(AsistenciaUnidadDia.this, "Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(AsistenciaUnidadDia.this, "Cancelado", Toast.LENGTH_LONG).show();
        }
    }

    public class asistenciaUnidadDiaAndroid  extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
            String METHOD_NAME = "asistenciaUnidadDiaAndroid";
            String SOAP_ACTION = "http://servicios/asistenciaUnidadDiaAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idUnidad", idString);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.debug = true;

            try{
                ht.call(SOAP_ACTION, envelope);
                String a = ht.requestDump;
                String b = ht.responseDump;
                Log.println(Log.INFO, "request", a);
                Log.println(Log.INFO, "response", b);
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
                float asistido = 0;
                float faltado = 0;
                try {
                    JSONObject info = new JSONObject(resultado2);
                    asistido = Float.parseFloat(info.getString("asistido"));
                    faltado = Float.parseFloat(info.getString("faltado"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<BarEntry> valoresAsistencia = new ArrayList<>();
                valoresAsistencia.add(new BarEntry(0f, asistido, "Asistido"));
                valoresAsistencia.add(new BarEntry(1f, faltado, "Faltado"));
                BarDataSet asistencia = new BarDataSet(valoresAsistencia, "Días");
                int colores[] = new int[2];
                colores[0] = ContextCompat.getColor(AsistenciaUnidadDia.this, R.color.azul);
                colores[1] = ContextCompat.getColor(AsistenciaUnidadDia.this, R.color.rojoGrafica);
                asistencia.setColors(colores, 150);
                asistencia.setBarBorderWidth(3f);
                asistencia.setBarBorderColor(Color.WHITE);

                BarData valoresGrafica = new BarData(asistencia);
                graficaBarraUnidad.setData(valoresGrafica);
                graficaBarraUnidad.setFitBars(true);
                graficaBarraUnidad.setDrawValueAboveBar(true);
                graficaBarraUnidad.getXAxis().setEnabled(false);
                graficaBarraUnidad.animateY(1500, Easing.EasingOption.EaseInOutExpo);
                graficaBarraUnidad.getDescription().setText("");
                graficaBarraUnidad.setTouchEnabled(false);
                graficaBarraUnidad.getLegend().setEnabled(false);
            }
            else{
                Toast.makeText(AsistenciaUnidadDia.this, "Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(AsistenciaUnidadDia.this, "Cancelado", Toast.LENGTH_LONG).show();
        }
    }
}
