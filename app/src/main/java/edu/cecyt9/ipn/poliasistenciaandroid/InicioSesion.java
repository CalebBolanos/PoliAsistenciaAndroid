package edu.cecyt9.ipn.poliasistenciaandroid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import edu.cecyt9.ipn.poliasistenciaandroid.alumno.InicioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia.InicioJefe;
import edu.cecyt9.ipn.poliasistenciaandroid.prefecto.InicioPrefecto;
import edu.cecyt9.ipn.poliasistenciaandroid.profesor.InicioProfesor;

import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.ALUMNO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.GESTION;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.JEFE_ACADEMIA;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.PREFECTO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.PROFESOR;

public class InicioSesion extends AppCompatActivity {
    EditText usuario, contrasena;
    Button ingresar;
    private Sesion sesion;
    ConstraintLayout constraintLayout;
    String resultado = "";
    ProgressDialog proceso;
    public static final String IP = "192.168.10.117";
    public static final String PUERTO = "8080";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        sesion = new Sesion(this);
        if(sesion.getIdTipo() != 0){
            switch (sesion.getIdTipo()){
                case ALUMNO:
                    Intent inicioAlumno = new Intent(this, InicioAlumno.class);
                    startActivity(inicioAlumno);
                    finish();
                    break;
                case PROFESOR:
                    Intent inicioProfesor = new Intent(this, InicioProfesor.class);
                    startActivity(inicioProfesor);
                    finish();
                    break;
                case JEFE_ACADEMIA:
                    Intent inicioJefe = new Intent(this, InicioJefe.class);
                    startActivity(inicioJefe);
                    finish();
                    break;
                case PREFECTO:
                    Intent inicioPrefecto = new Intent(this, InicioPrefecto.class);
                    startActivity(inicioPrefecto);
                    finish();
                    break;
            }
            return;
        }
        usuario = findViewById(R.id.txt_usuario);
        contrasena = findViewById(R.id.txt_contrasena);
        ingresar = findViewById(R.id.ingresar);
        Typeface calibri = Typeface.createFromAsset(getAssets(),  "fonts/calibri.ttf");
        TextView titulo = findViewById(R.id.tit_1);
        TextView titulo2 = findViewById(R.id.tit_2);
        titulo.setTypeface(calibri);
        titulo2.setTypeface(calibri, Typeface.BOLD);
        constraintLayout = findViewById(R.id.constraint_inicio_sesion);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.blanco));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blanco));
        }
        //startService(new Intent(InicioSesion.this, ServiceNotificaciones.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void iniciarSesion(View view) {
        new obtenerDatos().execute(usuario.getText().toString().trim(), contrasena.getText().toString().trim());

        proceso = new ProgressDialog(InicioSesion.this);
        proceso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proceso.setMessage("Iniciando Sesión");
        proceso.setCancelable(false);
        proceso.show();
    }

    public void abrirPrefecto(View view) {
        Intent inicioPrefecto = new Intent(getApplicationContext(), InicioPrefecto.class);
        startActivity(inicioPrefecto);
        finish();
    }

    public class obtenerDatos extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String valor = "";
            JSONObject datos = new JSONObject();
            try {
                datos.put("usuario", params[0]);
                datos.put("contrasena", params[1]);
                valor = datos.toString();
            }
            catch (Exception e){
                return false;
            }


            //ws inicio sesion
            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/usuario?WSDL";
            String METHOD_NAME = "validarUsuarioAndroid";
            String SOAP_ACTION = "http://servicios/validarUsuarioAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("numero", valor);

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
                SoapPrimitive  response = (SoapPrimitive) envelope.getResponse();
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
            String nombre = "";
            String paterno = "";
            String materno = "";
            String genero = "";
            String num = "";
            String nacimiento = "";
            String urlImagen = "";

            int idPersona = 0;
            int idTipo = 0;

            if(success){
                if(resultado.equals("incorrecto") || resultado.equals("error")){
                    proceso.dismiss();
                    Snackbar.make(constraintLayout, "Usuario o contraseña incorrecta", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if(resultado.equals("gestion")){
                    proceso.dismiss();
                    Snackbar.make(constraintLayout, "Solo puedes iniciar sesion en un navegador", Snackbar.LENGTH_LONG).show();
                    return;
                }

                try{
                    JSONObject info = new JSONObject(resultado);
                    idPersona = Integer.parseInt(info.getString("idPersona"));
                    idTipo = Integer.parseInt(info.getString("idTipo"));
                    nombre = info.getString("nombre");
                    paterno = info.getString("paterno");
                    materno = info.getString("materno");
                    genero = info.getString("genero");
                    num = info.getString("numero");
                    nacimiento = info.getString("nacimiento");
                    urlImagen = info.getString("urlImagen");

                    sesion.setDatos(idPersona, idTipo, nombre, paterno, materno, genero, num, nacimiento, urlImagen);
                }
                catch (JSONException error){
                    Snackbar.make(constraintLayout, "Errrooooooor", Snackbar.LENGTH_LONG).show();
                    proceso.dismiss();
                    return;
                }
                switch (idTipo){
                    case GESTION://gestion
                        proceso.dismiss();
                        Snackbar.make(constraintLayout, "Solo puedes iniciar sesion en un navegador", Snackbar.LENGTH_LONG).show();
                        break;
                    case ALUMNO://alumno
                        proceso.dismiss();
                        Intent inicioAlumno = new Intent(getApplicationContext(), InicioAlumno.class);
                        startActivity(inicioAlumno);
                        finish();
                        break;
                    case PROFESOR://profesor
                        proceso.dismiss();
                        Intent inicioProfesor = new Intent(getApplicationContext(), InicioProfesor.class);
                        startActivity(inicioProfesor);
                        finish();
                        break;
                    case JEFE_ACADEMIA://jefe academia
                        proceso.dismiss();
                        Intent inicioJefe = new Intent(getApplicationContext(), InicioJefe.class);
                        startActivity(inicioJefe);
                        finish();
                        break;
                    case PREFECTO://prefecto
                        proceso.dismiss();
                        Intent inicioPrefecto = new Intent(getApplicationContext(), InicioPrefecto.class);
                        startActivity(inicioPrefecto);
                        finish();
                        break;
                    default:
                        proceso.dismiss();
                        Snackbar.make(constraintLayout, "Usuario o contraseña incorrecta", Snackbar.LENGTH_LONG).show();
                        break;
                }
            }
            else{
                proceso.dismiss();
                Snackbar.make(constraintLayout, "Usuario o contraseña incorrecta", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Cancelado", Toast.LENGTH_LONG).show();
        }
    }
}
