package edu.cecyt9.ipn.poliasistenciaandroid.prefecto;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.DatosEstatusProfesor;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.EstatusProfesorAdapter;

public class InfoHorarioAlumno extends AppCompatActivity {

    ListView listaProfesores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_horario_alumno);

        Toolbar toolbar = findViewById(R.id.toolbar_info_horario_alumno);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Horario");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        listaProfesores = findViewById(R.id.listview_estatus_profesores);
        DatosEstatusProfesor titulo = new DatosEstatusProfesor("Unidad de Aprendizaje", "Profesor", "Estatus");
        ArrayList<DatosEstatusProfesor> datos = new ArrayList<>();
        datos.add(titulo);
        for(int i = 1; i<=12; i++){
            DatosEstatusProfesor profesorx = new DatosEstatusProfesor("Unidad", "Profesor"+i, "Asistio");
            datos.add(profesorx);
            profesorx = null;
        }
        EstatusProfesorAdapter adaptador = new EstatusProfesorAdapter(this, R.layout.adapter_view_estatus_profesor, datos);
        listaProfesores.setAdapter(adaptador);
        setListViewHeightBasedOnChildren(listaProfesores);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
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
