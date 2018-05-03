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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;


public class AsistenciaPorMes extends AppCompatActivity {

    BarChart graficaBarra;
    ListView listaDatosAsistencia;
    String mesString;
    int mes;
    int id;
    String resultado = "";
    String resultado2 = "";

    asistenciaIndividualAndroid asistenciaIndividualAsync;
    infoAsistenciaIndividualAndroid infoAsistenciaAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_por_mes_alumno);

        Intent intent = getIntent();
        mesString = intent.getStringExtra("mes");
        id = intent.getIntExtra("id", 0);
        mes = numeroMes(mesString);

        Toolbar toolbar = findViewById(R.id.toolbar_asistencia_mes_alumno);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(mesString);
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        graficaBarra = findViewById(R.id.grafica_barra_asistencia_mes);

        listaDatosAsistencia = findViewById(R.id.listview_asistencia_individual);

        asistenciaIndividualAsync = new asistenciaIndividualAndroid();
        asistenciaIndividualAsync.execute();
        infoAsistenciaAsync = new infoAsistenciaIndividualAndroid();
        infoAsistenciaAsync.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        asistenciaIndividualAsync.cancel(true);
        infoAsistenciaAsync.cancel(true);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
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

    public class infoAsistenciaIndividualAndroid extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/alumno?WSDL";
            String METHOD_NAME = "infoAsistenciaIndividualAndroid";
            String SOAP_ACTION = "http://servicios/infoAsistenciaIndividualAndroid";

            String info;

            JSONObject datos = new JSONObject();
            try {
                datos.put("idPer", ""+id);
                datos.put("mes", ""+mes);
                info = datos.toString();
            }
            catch (Exception e){
                return false;
            }


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("datos", info);

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

                datosAsistenciaIndividual dia1 = new datosAsistenciaIndividual("Asistido", "Día", "Entrada", "Salida");
                ArrayList<datosAsistenciaIndividual> dias = new ArrayList<>();
                dias.add(dia1);

                try {
                    JSONArray info = new JSONArray(resultado);
                    for (int i = 0; i < info.length(); i++) {
                        datosAsistenciaIndividual diax = new datosAsistenciaIndividual(info.getJSONObject(i).getString("asistecia"),
                                info.getJSONObject(i).getString("dia"),
                                info.getJSONObject(i).getString("entrada"),
                                info.getJSONObject(i).getString("salida"));
                        dias.add(diax);
                        diax = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                AsistenciaIndividualAdapter adaptador = new AsistenciaIndividualAdapter(AsistenciaPorMes.this, R.layout.adapter_view_asistencia_individual, dias);
                listaDatosAsistencia.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(listaDatosAsistencia);
                listaDatosAsistencia.setFocusable(false);

            }
            else{
                Toast.makeText(AsistenciaPorMes.this, "Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(AsistenciaPorMes.this, "Cancelado", Toast.LENGTH_LONG).show();
        }
    }

    public class asistenciaIndividualAndroid extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/alumno?WSDL";
            String METHOD_NAME = "asistenciaIndividualAndroid";
            String SOAP_ACTION = "http://servicios/asistenciaIndividualAndroid";

            String info;

            JSONObject datos = new JSONObject();
            try {
                datos.put("idPer", ""+id);
                datos.put("mes", ""+mes);
                info = datos.toString();
            }
            catch (Exception e){
                return false;
            }


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("datos", info);

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
                int asistido = 0;
                int faltado = 0;
                try {
                    JSONObject info = new JSONObject(resultado2);
                    asistido = Integer.parseInt(info.getString("asistido"));
                    faltado = Integer.parseInt(info.getString("faltado"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                List<BarEntry> valoresAsistencia = new ArrayList<>();
                valoresAsistencia.add(new BarEntry(0f, asistido, "Asistido"));
                valoresAsistencia.add(new BarEntry(1f, faltado, "Faltado"));
                BarDataSet asistencia = new BarDataSet(valoresAsistencia, "Días");
                int colores[] = new int[2];
                colores[0] = ContextCompat.getColor(AsistenciaPorMes.this, R.color.azul);
                colores[1] = ContextCompat.getColor(AsistenciaPorMes.this, R.color.rojoGrafica);
                asistencia.setColors(colores, 150);
                asistencia.setBarBorderWidth(3f);
                asistencia.setBarBorderColor(Color.WHITE);

                BarData valoresGrafica = new BarData(asistencia);
                graficaBarra.setData(valoresGrafica);
                graficaBarra.setFitBars(true);
                graficaBarra.setDrawValueAboveBar(true);
                graficaBarra.getXAxis().setEnabled(false);
                graficaBarra.animateY(1500, Easing.EasingOption.EaseInOutExpo);
                graficaBarra.getDescription().setText("");
                graficaBarra.setTouchEnabled(false);
                graficaBarra.getLegend().setEnabled(false);
            }
            else{
                Toast.makeText(AsistenciaPorMes.this, "Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(AsistenciaPorMes.this, "Cancelado", Toast.LENGTH_LONG).show();
        }
    }

    public int numeroMes(String mes){
        int numeroMes;
        switch (mes) {
            case "Enero":  numeroMes = 1;
                break;
            case "Febrero":  numeroMes = 2;
                break;
            case "Marzo":  numeroMes = 3;
                break;
            case "Abril":  numeroMes = 4;
                break;
            case "Mayo":  numeroMes = 5;
                break;
            case "Junio":  numeroMes = 6;
                break;
            case "Julio":  numeroMes = 7;
                break;
            case "Agosto":  numeroMes = 8;
                break;
            case "Septiembre":  numeroMes = 9;
                break;
            case "Octubre": numeroMes = 10;
                break;
            case "Noviembre": numeroMes = 11;
                break;
            case "Diciembre": numeroMes = 12;
                break;
            default: numeroMes = 0;
                break;
        }
        return numeroMes;
    }
}
