package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import edu.cecyt9.ipn.poliasistenciaandroid.alumno.InicioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia.FragementInicioJefeAcademia;
import edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia.InicioJefe;
import edu.cecyt9.ipn.poliasistenciaandroid.profesor.InicioProfesor;

import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.ALUMNO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.JEFE_ACADEMIA;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.PROFESOR;

public class InicioSesion extends AppCompatActivity {
    EditText usuario, contrasena;
    Button ingresar;
    String usr, psw;
    TextView mensaje;
    CheckBox sesionIniciado;
    Boolean mantenerSesion = false;
    private Sesion sesion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        sesion = new Sesion(this);
        if(!sesion.getUsuario().equals("")){
            Intent inicioAlumno = new Intent(this, InicioAlumno.class);
            inicioAlumno.putExtra("usuario", usr);
            startActivity(inicioAlumno);
            finish();
            return;
        }
        usuario = findViewById(R.id.txt_usuario);
        contrasena = findViewById(R.id.txt_contrasena);
        ingresar = findViewById(R.id.ingresar);
        mensaje = findViewById(R.id.mensaje);
        sesionIniciado = findViewById(R.id.checkBox);
        Typeface calibri = Typeface.createFromAsset(getAssets(),  "fonts/calibri.ttf");
        TextView titulo = findViewById(R.id.tit_1);
        TextView titulo2 = findViewById(R.id.tit_2);
        titulo.setTypeface(calibri);
        titulo2.setTypeface(calibri, Typeface.BOLD);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.blanco));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blanco));
        }
    }

    public void iniciarSesion(View view) {
        //aqui tendriamos que implementar un web service y hacer switch en el idTipo para
        //redirijirlo a su respectivo activity
        usr = usuario.getText().toString();
        psw = usuario.getText().toString();
        if(usr.equals(psw)){
            mantenerSesion = sesionIniciado.isChecked();
            switch (usr){
                case "alumno":
                    if(mantenerSesion){
                        sesion.setDatos(usr, psw, ALUMNO);
                    }
                    Intent inicioAlumno = new Intent(this, InicioAlumno.class);
                    startActivity(inicioAlumno);
                    finish();
                    break;
                case "profesor":
                    if(mantenerSesion){
                        sesion.setDatos(usr, psw, PROFESOR);
                    }
                    Intent inicioProfesor = new Intent(this, InicioProfesor.class);
                    startActivity(inicioProfesor);
                    finish();
                    break;
                case "jefe":
                    if(mantenerSesion){
                        sesion.setDatos(usr, psw, JEFE_ACADEMIA);
                    }
                    Intent inicioJefe = new Intent(this, InicioJefe.class);
                    startActivity(inicioJefe);
                    finish();
                    break;
                default:
                    mensaje.setText("Usuario o contraseña incorrecta");
                    break;

            }
        }
        else{
            mensaje.setText("Usario o contraseña incorrecta");
        }

    }

    public void verificarCheckBox(View view) {
    }
}
