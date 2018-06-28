package edu.cecyt9.ipn.poliasistenciaandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;

public class CrearNotificacion extends AppCompatActivity {

    Button botonImagen;
    TextView txtTitulo, txtUrl, txtInfo;
    String titulo, urlTit, info, resultado;
    Bitmap imagenBitmap;
    Sesion sesion;
    ProgressDialog proceso;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_notificacion);

        sesion = new Sesion(this);
        txtTitulo = findViewById(R.id.txt_titulo);
        txtUrl = findViewById(R.id.txt_url);
        txtInfo = findViewById(R.id.txt_informacion);
        constraintLayout = findViewById(R.id.constraint);
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
        Intent foto = new Intent(Intent.ACTION_GET_CONTENT);
        foto.setType("image/*");
        startActivityForResult(Intent.createChooser(foto, "Selecciona una imagen"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            Uri imagenSeleccionada = data.getData();
            try {
                imagenBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagenSeleccionada);
            } catch (IOException e) {
                e.printStackTrace();
            }
            botonImagen.setText("imagen seleccionada");
        }
    }

    public void subirNotificacion(View view){
        titulo = txtTitulo.getText().toString();
        urlTit = txtUrl.getText().toString();
        info = txtInfo.getText().toString();
        View vista = this.getCurrentFocus();
        if (vista != null) {
            InputMethodManager teclado = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            teclado.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if(!titulo.equals("") && !urlTit.equals("") && !info.equals("")){
            proceso = new ProgressDialog(CrearNotificacion.this);
            proceso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            proceso.setMessage("Subiendo notificación...");
            proceso.setCancelable(false);
            proceso.show();
            subirNotificaciones subir = new subirNotificaciones();
            subir.execute();
        }
        else{
            Snackbar.make(constraintLayout, "No dejes espacios vacios", Snackbar.LENGTH_LONG).show();
        }

    }

    public class subirNotificaciones  extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String valor = "";
            JSONObject datos = new JSONObject();
            String idper = ""+sesion.getIdPer();
            String imagenString = "no";

            if(imagenBitmap != null){
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                imagenBitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
                byte[] byteArray = stream.toByteArray();
                imagenString = Base64.encode(byteArray);
            }

            try {
                datos.put("tipoNotificacion", "2");
                datos.put("idPersona", idper);
                datos.put("titulo", titulo);
                datos.put("descripcion", info);
                datos.put("url", urlTit);
                datos.put("contenidoFoto", imagenString);

                valor = datos.toString();
            }
            catch (Exception e){
                return false;
            }


            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/gestion?WSDL";
            String METHOD_NAME = "guardarNotificacionesAndroid";
            String SOAP_ACTION = "http://servicios/guardarNotificacionesAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("datos", valor);

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
                proceso.dismiss();
                Snackbar.make(constraintLayout, "Notificación publicada", Snackbar.LENGTH_LONG).show();
            }
            else{
                proceso.dismiss();
                Snackbar.make(constraintLayout, "Error al subir notificación, no se puede conectar al servidor", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            proceso.dismiss();
        }
    }
}
