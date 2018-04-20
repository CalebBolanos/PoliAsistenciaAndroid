package edu.cecyt9.ipn.poliasistenciaandroid.prefecto;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import edu.cecyt9.ipn.poliasistenciaandroid.Configuracion;
import edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion;
import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.Sesion;

public class InicioPrefecto extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        FragmentBuscarAlumno.OnFragmentInteractionListener,
        FragmentGrupos.OnFragmentInteractionListener{

    private NavigationView navegador;
    FragmentBuscarAlumno buscarAlumno;
    FragmentGrupos grupos;
    BottomNavigationView barraNavegacion;
    Toolbar toolbarInicio;
    MaterialSearchView busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_prefecto);
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
        Typeface calibri = Typeface.createFromAsset(getAssets(),  "fonts/calibri.ttf");
        TextView tit1 = (TextView)findViewById(R.id.toolbar_title);
        TextView tit2 = (TextView)findViewById(R.id.toolbar_title_2);
        tit1.setTypeface(calibri);
        tit2.setTypeface(calibri, Typeface.BOLD);

        buscarAlumno = new FragmentBuscarAlumno();
        grupos = new FragmentGrupos();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragment, buscarAlumno).commit();

        busqueda = findViewById(R.id.search_view);
        busqueda.closeSearch();
        busqueda.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();

            switch (item.getItemId()){
                case R.id.navigation_buscar_alumno:
                    transaccion.replace(R.id.contenedorFragment, buscarAlumno);
                    transaccion.commit();
                    return true;
                case R.id.navigation_grupos:
                    transaccion.replace(R.id.contenedorFragment, grupos);
                    transaccion.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.container);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder constructorAlerta = new AlertDialog.Builder(InicioPrefecto.this);
            constructorAlerta.setTitle("PoliAsistencia");
            constructorAlerta.setMessage("¿Estás seguro de que quieres salir?");
            constructorAlerta.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    InicioPrefecto.this.finish();
                }
            })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
            AlertDialog alerta = constructorAlerta.create();
            alerta.show();
        }
    }

    public void reemplazarFragmentPrefecto(int itemId){
        FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
        switch (itemId){
            case R.id.navigation_buscar_alumno:
                transaccion.replace(R.id.contenedorFragment, buscarAlumno);
                barraNavegacion.setSelectedItemId(R.id.navigation_buscar_alumno);
                transaccion.commit();
            case R.id.navigation_grupos:
                transaccion.replace(R.id.contenedorFragment, grupos);
                transaccion.commit();
                barraNavegacion.setSelectedItemId(R.id.navigation_grupos    );
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.busqueda, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        busqueda.setMenuItem(item);

        return true;
    }
}
