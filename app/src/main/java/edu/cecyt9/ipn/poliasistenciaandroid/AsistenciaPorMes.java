package edu.cecyt9.ipn.poliasistenciaandroid;

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
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

import edu.cecyt9.ipn.poliasistenciaandroid.R;

public class AsistenciaPorMes extends AppCompatActivity {

    BarChart graficaBarra;
    ListView listaDatosAsistencia;
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
        crearGrafica();

        listaDatosAsistencia = findViewById(R.id.listview_asistencia_individual);
        datosAsistenciaIndividual dia1 = new datosAsistenciaIndividual("Si", "Martes 27", "10:00", "11:00");
        datosAsistenciaIndividual dia2 = new datosAsistenciaIndividual("No", "Miercoles 28", "---", "---");
        ArrayList<datosAsistenciaIndividual> dias = new ArrayList<>();
        dias.add(dia1);
        dias.add(dia2);
        AsistenciaIndividualAdapter adaptador = new AsistenciaIndividualAdapter(this, R.layout.adapter_view_asistencia_individual, dias);
        listaDatosAsistencia.setAdapter(adaptador);
        setListViewHeightBasedOnChildren(listaDatosAsistencia);
        listaDatosAsistencia.setFocusable(false);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void crearGrafica(){
        graficaBarra = findViewById(R.id.grafica_barra_asistencia_mes);
        List<BarEntry> valoresAsistencia = new ArrayList<>();
        valoresAsistencia.add(new BarEntry(0f, 4f, "Asistido"));
        valoresAsistencia.add(new BarEntry(1f, 4f, "Faltado"));
        BarDataSet asistencia = new BarDataSet(valoresAsistencia, "Días");
        int colores[] = new int[2];
        colores[0] = ContextCompat.getColor(this, R.color.azul);
        colores[1] = ContextCompat.getColor(this, R.color.rojoGrafica);
        asistencia.setColors(colores, 150);
        asistencia.setBarBorderWidth(3f);
        asistencia.setBarBorderColor(Color.WHITE);

        BarData valoresGrafica = new BarData(asistencia);
        graficaBarra.setData(valoresGrafica);
        graficaBarra.setFitBars(true);
        graficaBarra.setDrawValueAboveBar(true);
        graficaBarra.getXAxis().setEnabled(false);
        graficaBarra.animateY(1500, Easing.EasingOption.EaseInOutExpo);
        graficaBarra.getDescription().setText("");
        graficaBarra.setTouchEnabled(false);
        //XAxis valoresx  = graficaBarra.getXAxis();
        //valoresx.setValueFormatter(new IndexAxisValueFormatter(estatus));
        //valoresx.setLabelCount(4, true);
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
}
