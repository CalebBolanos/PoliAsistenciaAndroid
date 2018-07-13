package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewNotificaciones extends AppCompatActivity {

    WebView navegador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_notificaciones);

        Toolbar toolbar = findViewById(R.id.toolbar_webview);
        toolbar.setTitleTextColor((Color.parseColor("#000000")));
        toolbar.setBackgroundColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Notificacion");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_close_black_24dp);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            //getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.blanco));
            getWindow().setStatusBarColor(ContextCompat.getColor(this,R.color.blanco));
        }
        Intent nav = getIntent();
        String urlx = nav.getStringExtra("Url");
        navegador = findViewById(R.id.webview_notificacion);
        navegador.setWebViewClient(new WebViewClient());
        navegador.getSettings().setJavaScriptEnabled(true);
        navegador.loadUrl(urlx);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
