package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.HashMap;

import edu.cecyt9.ipn.poliasistenciaandroid.profesor.AsistenciaIndividualProfesor;

public class AsistenciaUnidad extends AppCompatActivity {

    ListView listaMeses;
    LineChart graficaUnidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistencia_unidad);

        Toolbar toolbar = findViewById(R.id.toolbar_asistencia_unidad);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Nombre Grupo");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listaMeses = findViewById(R.id.listview_meses_unidad);
        graficaUnidad = findViewById(R.id.linechart_asistencia_unidad);

        ArrayList<String> arrayMeses = new ArrayList<>();
        for(int i = 1; i<=12; i++){
            arrayMeses.add("Mes " + i);
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
                        Intent graficasUnidadMes = new Intent(AsistenciaUnidad.this, AsistenciaUnidadMes.class);
                        startActivity(graficasUnidadMes);
                        break;
                }
            }
        });
        setListViewHeightBasedOnChildren(listaMeses);
        listaMeses.setFocusable(false);
        generarGrafica();


    }

    public void generarGrafica(){
        final HashMap<Integer, String> meses = new HashMap<>();
        meses.put(0, "Meses");
        meses.put(1, "Enero");
        meses.put(2, "Febrero");
        meses.put(3, "Marzo");

        //Asistido

        ArrayList<Entry> diasAsistidos = new ArrayList<>();
        diasAsistidos.add(new Entry(0f, 0f));
        diasAsistidos.add(new Entry(1f, 0f));
        diasAsistidos.add(new Entry(2f, 1f));
        diasAsistidos.add(new Entry(3f, 0f));

        LineDataSet datosAsistido = new LineDataSet(diasAsistidos, "Dias Asistidos");
        datosAsistido.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        datosAsistido.setFillColor(ContextCompat.getColor(this, R.color.azul));
        datosAsistido.setDrawFilled(true);
        datosAsistido.setLineWidth(3f);
        datosAsistido.setColor(ContextCompat.getColor(this, R.color.azul));
        datosAsistido.setCircleColor(ContextCompat.getColor(this, R.color.azul));
        datosAsistido.setCircleRadius(5f);

        //faltado

        ArrayList<Entry> diasFaltados = new ArrayList<>();
        diasFaltados.add(new Entry(0f, 0f));
        diasFaltados.add(new Entry(1f, 1f));
        diasFaltados.add(new Entry(2f, 0f));
        diasFaltados.add(new Entry(3f, 1f));

        LineDataSet datosFalta = new LineDataSet(diasFaltados, "Dias Faltados");
        datosFalta.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        datosFalta.setFillColor(ContextCompat.getColor(this, R.color.rojoGrafica));
        datosFalta.setDrawFilled(true);
        datosFalta.setLineWidth(3f);
        datosFalta.setColor(ContextCompat.getColor(this, R.color.rojoGrafica));
        datosFalta.setCircleColor(ContextCompat.getColor(this, R.color.rojoGrafica));
        datosFalta.setCircleRadius(5f);

        ArrayList<ILineDataSet> datasets = new ArrayList<>();
        datasets.add(datosFalta);
        datasets.add(datosAsistido);
        XAxis valoresx = graficaUnidad.getXAxis();
        valoresx.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return meses.get((int)value);
            }
        });
        valoresx.setLabelCount(meses.size(),true);
        LineData todo = new LineData(datasets);
        graficaUnidad.setData(todo);
        graficaUnidad.animateY(1500, Easing.EasingOption.EaseInOutExpo);
        graficaUnidad.setTouchEnabled(false);
        graficaUnidad.getDescription().setText("");
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
