package edu.cecyt9.ipn.poliasistenciaandroid.prefecto;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.cecyt9.ipn.poliasistenciaandroid.AsistenciaPorMes;
import edu.cecyt9.ipn.poliasistenciaandroid.R;

public class InfoMesesEstadisticasAlumno extends AppCompatActivity {

    ListView listaMeses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_meses_estadisticas_alumno);

        Toolbar toolbar = findViewById(R.id.toolbar_info_estadisticas_alumno);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Selecciona un mes");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }


        listaMeses = findViewById(R.id.listview_meses);
        ArrayList<String> arrayMeses = new ArrayList<>();
        for(int i = 1; i<=20; i++){
            arrayMeses.add("Mes" + i);
        }

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayMeses);
        listaMeses.setAdapter(adaptador);
        listaMeses.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccionado = listaMeses.getItemAtPosition(i).toString();
                switch (seleccionado){
                    case "":

                        break;
                    default:
                        Intent graficas = new Intent(InfoMesesEstadisticasAlumno.this, AsistenciaPorMes.class);
                        startActivity(graficas);
                        break;
                }

            }
        });



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
