package edu.cecyt9.ipn.poliasistenciaandroid;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.InicioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia.InicioJefe;
import edu.cecyt9.ipn.poliasistenciaandroid.prefecto.InicioPrefecto;
import edu.cecyt9.ipn.poliasistenciaandroid.profesor.InicioProfesor;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.ALUMNO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.JEFE_ACADEMIA;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.PREFECTO;
import static edu.cecyt9.ipn.poliasistenciaandroid.Sesion.PROFESOR;

public class Configuracion extends AppCompatActivity {

    ListView lista;
    Sesion sesion;
    TextView nombre, boleta, nacimiento;
    CircleImageView imagen;
    LinearLayout datosGenerales;
    String resultado, nombreArchivo;
    Bitmap bitMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        sesion = new Sesion(this);
        if (sesion.getIdTipo() == 0) {
            Intent iniciarSesion = new Intent(this, InicioSesion.class);
            startActivity(iniciarSesion);
            finish();
            return;
        }


        Toolbar toolbar = findViewById(R.id.toolbar_configuracion);
        toolbar.setTitleTextColor((Color.parseColor("#ffffff")));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Configuración");
            final Drawable flechaAtras = getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp);
            flechaAtras.setColorFilter(getResources().getColor(R.color.blanco), PorterDuff.Mode.SRC_ATOP);
            getSupportActionBar().setHomeAsUpIndicator(flechaAtras);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        nombre = findViewById(R.id.textView2);
        String nombreString = sesion.getNombre() + " " + sesion.getPaterno() + " " + sesion.getMaterno();
        nombre.setText(nombreString);
        imagen = findViewById(R.id.imageView);
        datosGenerales = findViewById(R.id.linear_datos);
        Picasso.with(this).load("http://" + InicioSesion.IP + ":" + InicioSesion.PUERTO + "/poliAsistenciaWeb/" + sesion.getUrlImagen()).into(imagen);
        boleta = findViewById(R.id.boletaDatos);
        boleta.setText(sesion.getNum());
        nacimiento = findViewById(R.id.nacimiento);
        nacimiento.setText(sesion.getNacimiento());

        lista = findViewById(R.id.listview_info);
        setListViewHeightBasedOnChildren(lista);
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(this, R.array.array_info, android.R.layout.simple_list_item_1);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccionado = adapterView.getItemAtPosition(i).toString();
                switch (seleccionado) {
                    case "Cambiar correo electrónico":
                        Intent cambioCorreo = new Intent(Configuracion.this, CambiarCorreo.class);
                        startActivity(cambioCorreo);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.boton_izquierdo, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_foto:
                View boton = findViewById(R.id.action_foto);
                PopupMenu pop = new PopupMenu(Configuracion.this, boton);
                pop.getMenuInflater().inflate(R.menu.popup_foto, pop.getMenu());
                pop.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.tomar_foto:
                                Intent camara = new Intent("android.media.action.IMAGE_CAPTURE");
                                startActivity(camara);
                                break;
                            case R.id.escoger:
                                Intent foto = new Intent(Intent.ACTION_GET_CONTENT);
                                foto.setType("image/*");
                                startActivityForResult(Intent.createChooser(foto, "Selecciona una imagen"), 1);
                                break;
                        }
                        return true;
                    }
                });
                pop.show();
                break;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    bitMap = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    nombreArchivo = imageReturnedIntent.getDataString();
                    cambiarimagen cambImg = new cambiarimagen();
                    cambImg.execute();
                    imagen.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imagen.setImageURI(selectedImage);
                }
                break;
        }
    }

    public class cambiarimagen extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            // Metodo que queremos ejecutar en el servicio web
            String Metodo = "guradaImagenAndroid";
// Namespace definido en el servicio web
            String namespace = "http://servicios/";
// namespace + metodo
            String accionSoap = "hhtp://servicios/guradaImagenAndroid";
// Fichero de definicion del servcio web
            String url = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/usuario?WSDL";
            Sesion ses = new Sesion(getApplicationContext());
            String bol = ses.getNum();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            bitMap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            byte[] bitmapdata = bos.toByteArray();
            JSONObject jeiSon = new JSONObject();
            try {
                jeiSon.put("numero", bol);
                jeiSon.put("img", bitmapdata);
                jeiSon.put("nombreArchivo", nombreArchivo);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("StringDeJSON", jeiSon);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(url);
            ht.debug = true;

            try {
                ht.call(accionSoap, envelope);
                SoapPrimitive responce = (SoapPrimitive) envelope.getResponse();
                resultado = responce.toString();
            } catch (Exception xd) {
                Log.println(Log.ERROR, "Error: ", xd.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if(resultado.equalsIgnoreCase("Se subio correctamente")){

            }
        }
    }

}
