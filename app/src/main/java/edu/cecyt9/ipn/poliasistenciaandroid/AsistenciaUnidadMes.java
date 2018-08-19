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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

public class AsistenciaUnidadMes extends AppCompatActivity {

    BarChart graficaBarraUnidad;
    ListView datosAsistencia;
    String mes, idString, resultado, resultado2;
    TextView txtImpartido, txtAsistido, txtFaltado;
    asistenciaUnidadMesEspecificoAndroid asistenciaAsync;
    infoAsistenciaUnidadMesEspecificoAndroid  infoAsync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_unidad_mes);

        Intent intent = getIntent();
        mes = intent.getStringExtra("mes");
        idString = intent.getStringExtra("id");

        txtImpartido = findViewById(R.id.valor_clases_impartidas);
        txtAsistido = findViewById(R.id.valor_promedio_asistido);
        txtFaltado = findViewById(R.id.valor_promedio_faltado);
        Toolbar toolbar = findViewById(R.id.toolbar_asistencia_unidad_mes);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(mes);
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        graficaBarraUnidad = findViewById(R.id.grafica_barra_asistencia_unidad_mes);

        datosAsistencia = findViewById(R.id.listview_asistencia_unidad_mes);

        asistenciaAsync = new asistenciaUnidadMesEspecificoAndroid();
        asistenciaAsync.execute();
        infoAsync = new infoAsistenciaUnidadMesEspecificoAndroid();
        infoAsync.execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        asistenciaAsync.cancel(true);
        infoAsync.cancel(true);
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

    public class infoAsistenciaUnidadMesEspecificoAndroid extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
            String METHOD_NAME = "infoAsistenciaUnidadMesEspecificoAndroid";
            String SOAP_ACTION = "http://servicios/infoAsistenciaUnidadMesEspecificoAndroid";

            String info;

            JSONObject datos = new JSONObject();
            try {
                datos.put("idUnidad", idString);
                datos.put("mes", ""+numeroMes(mes));
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
                DatosAsistenciaUnidadMes titulo = new DatosAsistenciaUnidadMes("Boleta", "Nombre", "Dias Asistidos", "Dias Faltados");
                ArrayList<DatosAsistenciaUnidadMes> datos = new ArrayList<>();
                datos.add(titulo);
                try {
                    JSONArray info = new JSONArray(resultado);
                    for (int i = 0; i < info.length(); i++) {
                        DatosAsistenciaUnidadMes filax = new DatosAsistenciaUnidadMes(info.getJSONObject(i).getString("boleta"),
                                info.getJSONObject(i).getString("nombre"),
                                info.getJSONObject(i).getString("asistidos"),
                                info.getJSONObject(i).getString("faltado"));
                        datos.add(filax);
                        filax = null;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                AsistenciaUnidadMesAdapter adaptador = new AsistenciaUnidadMesAdapter(AsistenciaUnidadMes.this, R.layout.adapter_view_asistencia_unidad_mes, datos);
                datosAsistencia.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();
                setListViewHeightBasedOnChildren(datosAsistencia);
                datosAsistencia.setFocusable(false);

            }
            else{
                Toast.makeText(AsistenciaUnidadMes.this, "Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(AsistenciaUnidadMes.this, "Cancelado", Toast.LENGTH_LONG).show();
        }
    }

    public class asistenciaUnidadMesEspecificoAndroid extends AsyncTask<Void, String, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
            String METHOD_NAME = "asistenciaUnidadMesEspecificoAndroid";
            String SOAP_ACTION = "http://servicios/asistenciaUnidadMesEspecificoAndroid";

            String info;

            JSONObject datos = new JSONObject();
            try {
                datos.put("idUnidad", idString);
                datos.put("mes", ""+numeroMes(mes));
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
                float asistido = 0;
                float faltado = 0;
                int clases = 0;
                try {
                    JSONObject info = new JSONObject(resultado2);
                    asistido = Float.parseFloat(info.getString("asistido"));
                    faltado = Float.parseFloat(info.getString("faltado"));
                    clases = Integer.parseInt(info.getString("clases"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String clasesString = ""+ clases;
                txtImpartido.setText(clasesString);
                String asistString = ""+asistido;
                String faltString = ""+faltado;
                txtAsistido.setText(asistString+"%");
                txtFaltado.setText(faltString+"%");
                List<BarEntry> valoresAsistencia = new ArrayList<>();
                valoresAsistencia.add(new BarEntry(0f, asistido, "Asistido"));
                valoresAsistencia.add(new BarEntry(1f, faltado, "Faltado"));
                BarDataSet asistencia = new BarDataSet(valoresAsistencia, "Días");
                int colores[] = new int[2];
                colores[0] = ContextCompat.getColor(AsistenciaUnidadMes.this, R.color.azul);
                colores[1] = ContextCompat.getColor(AsistenciaUnidadMes.this, R.color.rojoGrafica);
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
                Toast.makeText(AsistenciaUnidadMes.this, "Error", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(AsistenciaUnidadMes.this, "Cancelado", Toast.LENGTH_LONG).show();
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
