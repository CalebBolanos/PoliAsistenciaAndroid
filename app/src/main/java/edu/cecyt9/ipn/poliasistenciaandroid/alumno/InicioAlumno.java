package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.Configuracion;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.FragmentEstadisticasAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.FragmentHorarioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.FragmentInicioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.FragmentNotificacionesAlumno;

public class InicioAlumno extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentInicioAlumno.OnFragmentInteractionListener,
        FragmentHorarioAlumno.OnFragmentInteractionListener,
        FragmentEstadisticasAlumno.OnFragmentInteractionListener,
        FragmentNotificacionesAlumno.OnFragmentInteractionListener{

    private TextView mTextMessage;
    private NavigationView navegador;
    FragmentInicioAlumno inicio;
    FragmentHorarioAlumno horario;
    FragmentEstadisticasAlumno estadisticas;
    FragmentNotificacionesAlumno notificaciones;
    BottomNavigationView barraNavegacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_alumno);
        navegador = findViewById(R.id.navegador);
        navegador.setNavigationItemSelectedListener(this);
        barraNavegacion = findViewById(R.id.navigation);
        barraNavegacion.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        inicio = new FragmentInicioAlumno();
        horario = new FragmentHorarioAlumno();
        estadisticas = new FragmentEstadisticasAlumno();
        notificaciones = new FragmentNotificacionesAlumno();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment, inicio).commit();

        Typeface calibri = Typeface.createFromAsset(getAssets(),  "fonts/calibri.ttf");
        TextView tit1 = (TextView)findViewById(R.id.toolbar_title);
        TextView tit2 = (TextView)findViewById(R.id.toolbar_title_2);
        tit1.setTypeface(calibri);
        tit2.setTypeface(calibri, Typeface.BOLD);
/*
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);*/
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_inicio:
                    transaccion.replace(R.id.contenedorFragment, inicio);
                    transaccion.commit();
                    return true;
                case R.id.navigation_horario:
                    transaccion.replace(R.id.contenedorFragment, horario);
                    transaccion.commit();
                    return true;
                case R.id.navigation_estadisticas:
                    transaccion.replace(R.id.contenedorFragment, estadisticas);
                    transaccion.commit();
                    return true;
                case R.id.navigation_notificaciones:
                    transaccion.replace(R.id.contenedorFragment, notificaciones);
                    transaccion.commit();
                    return true;
            }
            return false;
        }
    };


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.barra_configuracion) {
            Intent configuracion = new Intent(this, Configuracion.class);
            startActivity(configuracion);
        } else if (id == R.id.cerrarsesion) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.container);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
