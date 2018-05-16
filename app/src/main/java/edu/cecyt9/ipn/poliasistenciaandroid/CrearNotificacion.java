package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;

public class CrearNotificacion extends AppCompatActivity {

    Button botonImagen;
    TextView txtTitulo, txtUrl, txtInfo;
    String titulo, urlT, info, resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_notificacion);
        txtTitulo = findViewById(R.id.txt_titulo);
        txtUrl = findViewById(R.id.txt_url);
        txtInfo = findViewById(R.id.txt_informacion);
        Toolbar toolbar = findViewById(R.id.toolbar_notificacion);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Crear Notificacion");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            botonImagen = findViewById(R.id.btn_subir_imagen);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void obtenerImagen(View view) {
        Intent imagen = new Intent(Intent.ACTION_PICK);
        imagen.setType("image/*");
        startActivityForResult(imagen, 1);
    }

    public void subirNonificaciones(View view){

        titulo = txtTitulo.getText().toString();
        urlT = txtUrl.getText().toString();
        info = txtInfo.getText().toString();
        cambiarimagen cb = new cambiarimagen();
        cb.execute();
    }

    public class cambiarimagen extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            // Metodo que queremos ejecutar en el servicio web
            String Metodo = "guardarNotificacionesAndroid";
// Namespace definido en el servicio web
            String namespace = "http://servicios/";
// namespace + metodo
            String accionSoap = "hhtp://servicios/guardarNotificacionesAndroid";
// Fichero de definicion del servcio web
            String url = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/gestion?WSDL";
            Sesion ses = new Sesion(getApplicationContext());
            String bol = ses.getNum();
            JSONObject jeiSon = new JSONObject();
            try {
                jeiSon.put("numero", bol);
                jeiSon.put("titulo", titulo);
                jeiSon.put("descripcion", info);
                jeiSon.put("url", urlT);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SoapObject request = new SoapObject(namespace, Metodo);
            String que = jeiSon.toString();
            request.addProperty("jeisonString", jeiSon.toString());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(url);
            ht.debug = true;

            try {
                ht.call(accionSoap, envelope);
                SoapPrimitive responce = (SoapPrimitive) envelope.getResponse();
                resultado = responce.toString();
                Log.println(Log.ERROR, "Mensaje reponse: ", resultado);
            } catch (Exception xd) {
                Log.println(Log.ERROR, "Error: ", xd.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(resultado.equalsIgnoreCase("OK")){
                txtUrl.setText("Se subio correctamente");
            }
        }
    }
}
