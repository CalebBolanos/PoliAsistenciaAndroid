package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
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
import android.support.v7.widget.Toolbar;

import edu.cecyt9.ipn.poliasistenciaandroid.Configuracion;
import edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion;
import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.Sesion;


public class InicioAlumno extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentInicioAlumno.OnFragmentInteractionListener,
        FragmentHorarioAlumno.OnFragmentInteractionListener,
        FragmentEstadisticasAlumno.OnFragmentInteractionListener,
        FragmentNotificacionesAlumno.OnFragmentInteractionListener{

    private NavigationView navegador;
    FragmentInicioAlumno inicio;
    FragmentHorarioAlumno horario;
    FragmentEstadisticasAlumno estadisticas;
    FragmentNotificacionesAlumno notificaciones;
    BottomNavigationView barraNavegacion;
    Toolbar toolbarInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_alumno);
        navegador = findViewById(R.id.navegador);
        navegador.setNavigationItemSelectedListener(this);
        barraNavegacion = findViewById(R.id.navigation);
        barraNavegacion.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        toolbarInicio = findViewById(R.id.toolbar);
        setSupportActionBar(toolbarInicio);
        final Drawable drwmenu = getResources().getDrawable(R.drawable.ic_menu_black_24dp);
        drwmenu.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHideOnContentScrollEnabled(false);
        toolbarInicio.setNavigationIcon(drwmenu);
        getSupportActionBar().setHideOnContentScrollEnabled(false);


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
            FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();/*.setCustomAnimations(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out);*/
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
            Sesion sesion = new Sesion(this);
            sesion.clearDatos();
            Intent iniciarSesion = new Intent(this, InicioSesion.class);
            startActivity(iniciarSesion);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.container);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        DrawerLayout drawer = findViewById(R.id.container);
        if (item.getItemId() == android.R.id.home) {
            drawer.openDrawer(GravityCompat.START);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void reemplazarFragment(int itemId){
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();/*.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out,
                android.R.anim.fade_in,
                android.R.anim.fade_out);*/

        switch (itemId){
            case R.id.navigation_inicio:
                transaccion.replace(R.id.contenedorFragment, inicio);
                transaccion.commit();
                barraNavegacion.setSelectedItemId(R.id.navigation_inicio);
                break;
            case R.id.navigation_horario:
                transaccion.replace(R.id.contenedorFragment, horario);
                transaccion.commit();
                barraNavegacion.setSelectedItemId(R.id.navigation_horario);
                break;
            case R.id.navigation_estadisticas:
                transaccion.replace(R.id.contenedorFragment, estadisticas);
                transaccion.commit();
                barraNavegacion.setSelectedItemId(R.id.navigation_estadisticas);
                break;
            case R.id.navigation_notificaciones:
                transaccion.replace(R.id.contenedorFragment, notificaciones);
                transaccion.commit();
                barraNavegacion.setSelectedItemId(R.id.navigation_notificaciones);
                break;
        }

    }
}
