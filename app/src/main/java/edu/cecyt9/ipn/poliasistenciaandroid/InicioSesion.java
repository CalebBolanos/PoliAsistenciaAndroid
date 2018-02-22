package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.cecyt9.ipn.poliasistenciaandroid.alumno.InicioAlumno;

public class InicioSesion extends AppCompatActivity {
    EditText usuario, contrasena;
    Button ingresar;
    String usr, psw;
    TextView mensaje;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        usuario = findViewById(R.id.usuario);
        contrasena = findViewById(R.id.contrasena);
        ingresar = findViewById(R.id.ingresar);
        mensaje = findViewById(R.id.mensaje);
        Typeface calibri = Typeface.createFromAsset(getAssets(),  "fonts/calibri.ttf");
        TextView titulo = (TextView)findViewById(R.id.tit_1);
        TextView titulo2 = findViewById(R.id.tit_2);
        titulo.setTypeface(calibri);
        titulo2.setTypeface(calibri, Typeface.BOLD);
    }

    public void iniciarSesion(View view) {
        //aqui tendriamos que implementar un web service y hacer switch en el idTipo para
        //redirijirlo a su respectivo activity
        usr = usuario.getText().toString();
        psw = usuario.getText().toString();
        if(usr.equals(psw)){
            switch (usr){
                case "alumno":
                    Intent inicioAlumno = new Intent(this, InicioAlumno.class);
                    inicioAlumno.putExtra("usuario", usr);
                    startActivity(inicioAlumno);
                    break;
                case "profesor":
                    Intent inicioProfesor = new Intent(this, InicioAlumno.class);
                    inicioProfesor.putExtra("usuario", usr);
                    startActivity(inicioProfesor);
                    break;
                case "jefe":
                    Intent inicioJefe = new Intent(this, InicioAlumno.class);
                    inicioJefe.putExtra("usuario", usr);
                    startActivity(inicioJefe);
                    break;
                default:
                    mensaje.setText("Usario o contraseña incorrecta");
                    break;

            }
        }
        else{
            mensaje.setText("Usario o contraseña incorrecta");
        }

    }
}
