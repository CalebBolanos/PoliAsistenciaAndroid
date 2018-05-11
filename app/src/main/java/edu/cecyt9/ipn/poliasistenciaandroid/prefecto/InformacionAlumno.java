package edu.cecyt9.ipn.poliasistenciaandroid.prefecto;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;
import java.util.HashMap;

import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.DatosHorarioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.HorarioUnidadAdapter;

public class InformacionAlumno extends AppCompatActivity {

    LineChart grafica;
    ListView listaHorario;
    private  String bole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion_alumno);
        Intent inten = getIntent();
        bole = inten.getStringExtra("boleta");
        Toolbar toolbar = findViewById(R.id.toolbar_info_alumno);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Nombre Alumno");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        grafica = findViewById(R.id.grafica_linechart_asistencia);
        listaHorario = findViewById(R.id.listview_horario_dia);
        DatosHorarioAlumno titulo = new DatosHorarioAlumno("Unidad de Aprendizaje", "Hora");
        ArrayList<DatosHorarioAlumno> datos = new ArrayList<>();
        datos.add(titulo);
        for (int i = 0; i <9 ; i++) {
            DatosHorarioAlumno unidadx = new DatosHorarioAlumno("Unidad"+i, "00:00");
            datos.add(unidadx);
            unidadx = null;
        }
        HorarioUnidadAdapter adaptador = new HorarioUnidadAdapter(this, R.layout.adapter_view_horario_unidad, datos);
        listaHorario.setAdapter(adaptador);
        listaHorario.setFocusable(false);
        listaHorario.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            }
        });

        generarGrafica();
    }


    public void generarGrafica(){
        final HashMap<Integer, String> meses = new HashMap<>();
        meses.put(0, "Meses");
        meses.put(1, "Enero");
        meses.put(2, "Febrero");
        meses.put(3, "Marzo");


        ArrayList<Entry> dias = new ArrayList<>();
        dias.add(new Entry(0f, 0f));
        dias.add(new Entry(1f, 0f));
        dias.add(new Entry(2f, 1f));
        dias.add(new Entry(3f, 0f));

        LineDataSet datos = new LineDataSet(dias, "Dias");
        datos.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        datos.setFillColor(ContextCompat.getColor(this, R.color.azul));
        datos.setDrawFilled(true);
        datos.setLineWidth(3f);
        datos.setColor(ContextCompat.getColor(this, R.color.azul));
        datos.setCircleColor(ContextCompat.getColor(this, R.color.azul));
        datos.setCircleRadius(5f);
        LineData todo = new LineData(datos);

        XAxis valoresx = grafica.getXAxis();
        valoresx.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return meses.get((int)value);
            }
        });
        valoresx.setLabelCount(meses.size(),true);

        grafica.setData(todo);
        grafica.animateY(1500, Easing.EasingOption.EaseInOutExpo);
        grafica.setTouchEnabled(false);
        grafica.getDescription().setText("");
    }

    public void abrirMas(View view) {
        switch (view.getId()){
            case R.id.boton_horario:
                Intent horario = new Intent(this, InfoHorarioAlumno.class);
                horario.putExtra("boleta", bole);
                startActivity(horario);
                break;
            case R.id.boton_estadisticas:
                Intent estadisticas = new Intent(this, InfoMesesEstadisticasAlumno.class);
                estadisticas.putExtra("boleta", bole);
                startActivity(estadisticas);
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
