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
import android.widget.Button;

public class CrearNotificacion extends AppCompatActivity {

    Button botonImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_notificacion);

        Toolbar toolbar = findViewById(R.id.toolbar_notificacion);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Crear Notificacion");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            botonImagen = findViewById(R.id.btn_subir_imagen);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void obtenerImagen(View view) {
        Intent imagen = new Intent(Intent.ACTION_PICK);
        imagen.setType("image/*");
        startActivityForResult(imagen, 1);
    }
}
