package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import edu.cecyt9.ipn.poliasistenciaandroid.R;

public class AsistenciaPorMesAlumno extends AppCompatActivity {

    BarChart graficaBarra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_por_mes_alumno);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_asistencia_mes_alumno);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Nombre Mes");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        graficaBarra = findViewById(R.id.grafica_barra_asistencia_mes_alumno);
        List<BarEntry> valoresAsistencia = new ArrayList<>();
        valoresAsistencia.add(new BarEntry(0f, 5f));
        valoresAsistencia.add(new BarEntry(1f, 7f));
        BarDataSet asistencia = new BarDataSet(valoresAsistencia, "Días");

        ArrayList<String> titulos = new ArrayList<>();
        titulos.add("Asistido");
        titulos.add("No Asistidos");

        BarData valoresGrafica = new BarData(asistencia);
        graficaBarra.setData(valoresGrafica);
        graficaBarra.setFitBars(true);
        graficaBarra.setDrawValueAboveBar(true);
        graficaBarra.getXAxis().setEnabled(false);
        graficaBarra.animateY(2000, Easing.EasingOption.EaseOutBack);



    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
