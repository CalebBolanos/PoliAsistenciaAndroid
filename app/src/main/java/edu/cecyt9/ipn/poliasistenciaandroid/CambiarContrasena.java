package edu.cecyt9.ipn.poliasistenciaandroid;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import edu.cecyt9.ipn.poliasistenciaandroid.alumno.InicioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia.InicioJefe;
import edu.cecyt9.ipn.poliasistenciaandroid.prefecto.InicioPrefecto;
import edu.cecyt9.ipn.poliasistenciaandroid.profesor.InicioProfesor;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.ALUMNO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.GESTION;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.JEFE_ACADEMIA;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.PREFECTO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.PROFESOR;

public class CambiarContrasena extends AppCompatActivity {

    Sesion sesion;
    EditText actual, nueva, nueva2;
    String stringActual, stringNueva, stringNueva2, resultado;
    ConstraintLayout constraintLayout;
    ProgressDialog proceso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_contrasena);

        sesion = new Sesion(this);
        actual = findViewById(R.id.txt_contrasena_actual);
        nueva = findViewById(R.id.txt_contrasena_nueva);
        nueva2 = findViewById(R.id.txt_contrasena_nueva_validar);
        constraintLayout = findViewById(R.id.constraintt);
        Toolbar toolbar = findViewById(R.id.toolbar_cambiar_contrasena);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Cambiar Contraseña");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void cambiarPsw(View view) {
        stringActual = actual.getText().toString().trim();
        stringNueva = nueva.getText().toString().trim();
        stringNueva2 = nueva2.getText().toString().trim();
        if(!stringActual.equals("") && !stringNueva.equals("") && !stringNueva2.equals("")){
            if(stringNueva.equals(stringNueva2)){
                View vista = this.getCurrentFocus();
                if (vista != null) {
                    InputMethodManager teclado = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    teclado.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                proceso = new ProgressDialog(CambiarContrasena.this);
                proceso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                proceso.setMessage("Cambiando contraseña...");
                proceso.setCancelable(false);
                proceso.show();
                cambioContrasenaAndroid cambiar = new cambioContrasenaAndroid();
                cambiar.execute(stringActual, stringNueva);
            }
            else{
                Snackbar.make(constraintLayout, "Las nuevas contraseñas no coinciden", Snackbar.LENGTH_LONG).show();
            }
        }
        else{
            Snackbar.make(constraintLayout, "No dejes espacios vacios", Snackbar.LENGTH_LONG).show();
        }


    }

    public class cambioContrasenaAndroid  extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String valor = "";
            JSONObject datos = new JSONObject();
            String idtipo =  ""+sesion.getIdTipo();

            try {
                datos.put("idTipo", idtipo);
                datos.put("boleta", sesion.getNum());
                datos.put("antigua", params[0]);
                datos.put("nueva", params[1]);
                valor = datos.toString();
            }
            catch (Exception e){
                return false;
            }


            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/usuario?WSDL";
            String METHOD_NAME = "cambioContrasenaAndroid";
            String SOAP_ACTION = "http://servicios/cambioContrasenaAndroid";


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
                Snackbar.make(constraintLayout, resultado, Snackbar.LENGTH_LONG).show();
            }
            else{
                proceso.dismiss();
                Snackbar.make(constraintLayout, "Usuario o contraseña incorrecta", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            proceso.dismiss();
        }
    }
}
