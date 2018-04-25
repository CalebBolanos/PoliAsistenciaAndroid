package edu.cecyt9.ipn.poliasistenciaandroid;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.opengl.EGLExt;
import android.os.AsyncTask;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.Vector;

import edu.cecyt9.ipn.poliasistenciaandroid.alumno.InicioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia.FragementInicioJefeAcademia;
import edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia.InicioJefe;
import edu.cecyt9.ipn.poliasistenciaandroid.prefecto.InicioPrefecto;
import edu.cecyt9.ipn.poliasistenciaandroid.profesor.InicioProfesor;

import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.ALUMNO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.JEFE_ACADEMIA;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.PREFECTO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.PROFESOR;

public class InicioSesion extends AppCompatActivity {
    EditText usuario, contrasena;
    Button ingresar;
    String usr, psw;
    CheckBox sesionIniciado;
    Boolean mantenerSesion = false;
    private Sesion sesion;
    ConstraintLayout constraintLayout;
    String resultado = "";
    ArrayList datosUsuario = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        sesion = new Sesion(this);
        if(!sesion.getUsuario().equals("")){
            switch (sesion.getTipoUsr()){
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
        sesionIniciado = findViewById(R.id.checkBox);
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
        new prueba().execute();
    }

    public void iniciarSesion(View view) {
        //aqui tendriamos que implementar un web service y hacer switch en el idTipo para
        //redirijirlo a su respectivo activity
        ProgressDialog proceso = new ProgressDialog(InicioSesion.this);
        proceso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        proceso.setMessage("Iniciando Sesión");
        proceso.setCancelable(false);
        proceso.show();

        usr = usuario.getText().toString();
        psw = usuario.getText().toString();
        if(usr.equals(psw)){
            mantenerSesion = sesionIniciado.isChecked();
            switch (usr){
                case "alumno":
                    if(mantenerSesion){
                        sesion.setDatos(usr, psw, ALUMNO);
                    }
                    proceso.dismiss();
                    Intent inicioAlumno = new Intent(this, InicioAlumno.class);
                    startActivity(inicioAlumno);
                    finish();
                    break;
                case "profesor":
                    if(mantenerSesion){
                        sesion.setDatos(usr, psw, PROFESOR);
                    }
                    proceso.dismiss();
                    Intent inicioProfesor = new Intent(this, InicioProfesor.class);
                    startActivity(inicioProfesor);
                    finish();
                    break;
                case "jefe":
                    if(mantenerSesion){
                        sesion.setDatos(usr, psw, JEFE_ACADEMIA);
                    }
                    proceso.dismiss();
                    Intent inicioJefe = new Intent(this, InicioJefe.class);
                    startActivity(inicioJefe);
                    finish();
                    break;
                case "prefecto":
                    if(mantenerSesion){
                        sesion.setDatos(usr, psw, PREFECTO);
                    }
                    proceso.dismiss();
                    Intent inicioPrefecto = new Intent(this, InicioPrefecto.class);
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

    public void verificarCheckBox(View view) {
    }

    public class prueba extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {

            //ws usuario
            String NAMESPACE = "http://servicios/";
            String URL = "http://192.168.1.65:8080/serviciosWebPoliAsistencia/usuario?WSDL";
            String METHOD_NAME = "traerUrl";
            String SOAP_ACTION = "http://servicios/traerUrl";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("idPer", 1);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.setAddAdornments(false);
            envelope.dotNet = true;

            HttpTransportSE ht = new HttpTransportSE(URL);

            try{
                ht.call(SOAP_ACTION, envelope);
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
            if(!success){
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

            }
            else{
                Toast.makeText(getApplicationContext(), "Bien "+resultado, Toast.LENGTH_LONG).show();

            }
        }

        @Override
        protected void onCancelled() {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
        }
    }
}
