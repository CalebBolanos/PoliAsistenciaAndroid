package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
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

public class Configuracion extends AppCompatActivity {

    ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_configuracion);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Configuración");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        lista = findViewById(R.id.listview_info);
        setListViewHeightBasedOnChildren(lista);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.array_info, android.R.layout.simple_list_item_1);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccionado = adapterView.getItemAtPosition(i).toString();
                switch (seleccionado){
                    case "Cambiar correo electrónico":
                        //Intent correo = new Intent(this, CambiarCorreo.class);
                        //startActivity(correo);
                        break;
                    case "Cambiar contraseña":
                        Intent cambioContrasena = new Intent(Configuracion.this, CambiarContrasena.class);
                        startActivity(cambioContrasena);
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
