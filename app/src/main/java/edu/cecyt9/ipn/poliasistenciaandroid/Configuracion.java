package edu.cecyt9.ipn.poliasistenciaandroid;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.DatosHorarioAlumno;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.HorarioUnidadAdapter;
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
    TextView nombre;
    CircleImageView imagen;
    Bitmap imagenBitmap;
    String resultado;
    ProgressDialog proceso;
    CoordinatorLayout coordinator;
    TextView usuario;

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
        usuario = findViewById(R.id.textView3);
        switch (sesion.getIdTipo()){
            case ALUMNO:
                usuario.setText("Alumno");
                break;
            case PROFESOR:
                usuario.setText("Profesor");
                break;
            case JEFE_ACADEMIA:
                usuario.setText("Jefe de Academia");
                break;
            case PREFECTO:
                usuario.setText("Prefecto");
                break;
        }
        coordinator = findViewById(R.id.coordinator_configuracion);
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
        Picasso.with(this).load("http://" + InicioSesion.IP + ":" + InicioSesion.PUERTO + "/poliAsistenciaWeb/" + sesion.getUrlImagen()).into(imagen);

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
                                Intent tomarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                if (tomarFoto.resolveActivity(getPackageManager()) != null) {
                                    startActivityForResult(tomarFoto, 0);
                                }
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
                    Bundle extras = imageReturnedIntent.getExtras();
                    imagenBitmap = (Bitmap) extras.get("data");
                    proceso = new ProgressDialog(Configuracion.this);
                    proceso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    proceso.setMessage("Subiendo Imagen...");
                    proceso.setCancelable(false);
                    proceso.show();
                    subirFoto subir = new subirFoto();
                    subir.execute();
                    //imagen.setImageBitmap(imagenBitmap);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri imagenSeleccionada = imageReturnedIntent.getData();
                    try {
                        imagenBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imagenSeleccionada);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    proceso = new ProgressDialog(Configuracion.this);
                    proceso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    proceso.setMessage("Subiendo Imagen...");
                    proceso.setCancelable(false);
                    proceso.show();
                    subirFoto subir = new subirFoto();
                    subir.execute();
                    //imagen.setImageURI(imagenSeleccionada);
                }
                break;
        }
    }

    @SuppressLint("StaticFieldLeak")
    public class subirFoto extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String valor = "";
            JSONObject datos = new JSONObject();
            String idtipo =  ""+sesion.getIdTipo();
            String idper = ""+sesion.getIdPer();

            ByteArrayOutputStream stream = new ByteArrayOutputStream();

            imagenBitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            byte[] byteArray = stream.toByteArray();
            String imagenString = Base64.encode(byteArray);
            String nom = sesion.getNombre() + " " + sesion.getPaterno() + " " + sesion.getMaterno();

            try {
                datos.put("idPer", idper);
                datos.put("idTipo", idtipo);
                datos.put("foto", imagenString);
                datos.put("nombre", nom);
                valor = datos.toString();
            }
            catch (Exception e){
                return false;
            }


            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/usuario?WSDL";
            String METHOD_NAME = "subirFotoPerfilAndroid";
            String SOAP_ACTION = "http://servicios/subirFotoPerfilAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("datos", valor);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.debug = true;

            try{
                ht.call(SOAP_ACTION, envelope);
                String a = ht.requestDump;
                String b = ht.responseDump;
                Log.println(Log.INFO, "request", a);
                Log.println(Log.INFO, "response", b);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
                resultado = response.toString();
                Log.i("Respuesta" ,  resultado);
            }
            catch(Exception error){
                error.printStackTrace();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if(success){
                proceso.dismiss();
                Snackbar.make(coordinator, "Foto de perfil actualizada", Snackbar.LENGTH_LONG).show();
                sesion.setUrlImagen(resultado);
                Picasso.with(getApplicationContext()).load("http://" + InicioSesion.IP + ":" + InicioSesion.PUERTO + "/poliAsistenciaWeb/" + sesion.getUrlImagen()).into(imagen);
            }
            else{
                proceso.dismiss();
                Snackbar.make(coordinator, "Error al subir foto, no se puede conectar al servidor", Snackbar.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            proceso.dismiss();
        }
    }

}
