package edu.cecyt9.ipn.poliasistenciaandroid.prefecto;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.Sesion;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.DatosEstatusProfesor;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.EstatusProfesorAdapter;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;

public class InfoHorarioAlumno extends AppCompatActivity {

    ListView listaProfesores;
    private String bole;
    TextView[][]txtH=new TextView[14][5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_horario_alumno);
        txtH[0][0] = findViewById(R.id.txt_horario_alumno_l7);
        txtH[0][1] = findViewById(R.id.txt_horario_alumno_m7);
        txtH[0][2] = findViewById(R.id.txt_horario_alumno_mi7);
        txtH[0][3] = findViewById(R.id.txt_horario_alumno_j7);
        txtH[0][4] = findViewById(R.id.txt_horario_alumno_v7);
        txtH[1][0] = findViewById(R.id.txt_horario_alumno_l8);
        txtH[1][1] = findViewById(R.id.txt_horario_alumno_m8);
        txtH[1][2] = findViewById(R.id.txt_horario_alumno_mi8);
        txtH[1][3] = findViewById(R.id.txt_horario_alumno_j8);
        txtH[1][4] = findViewById(R.id.txt_horario_alumno_v8);
        txtH[2][0] = findViewById(R.id.txt_horario_alumno_l9);
        txtH[2][1] = findViewById(R.id.txt_horario_alumno_m9);
        txtH[2][2] = findViewById(R.id.txt_horario_alumno_mi9);
        txtH[2][3] = findViewById(R.id.txt_horario_alumno_j9);
        txtH[2][4] = findViewById(R.id.txt_horario_alumno_v9);
        txtH[3][0] = findViewById(R.id.txt_horario_alumno_l10);
        txtH[3][1] = findViewById(R.id.txt_horario_alumno_m10);
        txtH[3][2] = findViewById(R.id.txt_horario_alumno_mi10);
        txtH[3][3] = findViewById(R.id.txt_horario_alumno_j10);
        txtH[3][4] = findViewById(R.id.txt_horario_alumno_v10);
        txtH[4][0] = findViewById(R.id.txt_horario_alumno_l11);
        txtH[4][1] = findViewById(R.id.txt_horario_alumno_m11);
        txtH[4][2] = findViewById(R.id.txt_horario_alumno_mi11);
        txtH[4][3] = findViewById(R.id.txt_horario_alumno_j11);
        txtH[4][4] = findViewById(R.id.txt_horario_alumno_v11);
        txtH[5][0] = findViewById(R.id.txt_horario_alumno_l12);
        txtH[5][1] = findViewById(R.id.txt_horario_alumno_m12);
        txtH[5][2] = findViewById(R.id.txt_horario_alumno_mi12);
        txtH[5][3] = findViewById(R.id.txt_horario_alumno_j12);
        txtH[5][4] = findViewById(R.id.txt_horario_alumno_v12);
        txtH[6][0] = findViewById(R.id.txt_horario_alumno_l13);
        txtH[6][1] = findViewById(R.id.txt_horario_alumno_m13);
        txtH[6][2] = findViewById(R.id.txt_horario_alumno_mi13);
        txtH[6][3] = findViewById(R.id.txt_horario_alumno_j13);
        txtH[6][4] = findViewById(R.id.txt_horario_alumno_v13);
        txtH[7][0] = findViewById(R.id.txt_horario_alumno_l14);
        txtH[7][1] = findViewById(R.id.txt_horario_alumno_m14);
        txtH[7][2] = findViewById(R.id.txt_horario_alumno_mi14);
        txtH[7][3] = findViewById(R.id.txt_horario_alumno_j14);
        txtH[7][4] = findViewById(R.id.txt_horario_alumno_v14);
        txtH[8][0] = findViewById(R.id.txt_horario_alumno_l15);
        txtH[8][1] = findViewById(R.id.txt_horario_alumno_m15);
        txtH[8][2] = findViewById(R.id.txt_horario_alumno_mi15);
        txtH[8][3] = findViewById(R.id.txt_horario_alumno_j15);
        txtH[8][4] = findViewById(R.id.txt_horario_alumno_v15);
        txtH[9][0] = findViewById(R.id.txt_horario_alumno_l16);
        txtH[9][1] = findViewById(R.id.txt_horario_alumno_m16);
        txtH[9][2] = findViewById(R.id.txt_horario_alumno_mi16);
        txtH[9][3] = findViewById(R.id.txt_horario_alumno_j16);
        txtH[9][4] = findViewById(R.id.txt_horario_alumno_v16);
        txtH[10][0] = findViewById(R.id.txt_horario_alumno_l17);
        txtH[10][1] = findViewById(R.id.txt_horario_alumno_m17);
        txtH[10][2] = findViewById(R.id.txt_horario_alumno_mi17);
        txtH[10][3] = findViewById(R.id.txt_horario_alumno_j17);
        txtH[10][4] = findViewById(R.id.txt_horario_alumno_v17);
        txtH[11][0] = findViewById(R.id.txt_horario_alumno_l18);
        txtH[11][1] = findViewById(R.id.txt_horario_alumno_m18);
        txtH[11][2] = findViewById(R.id.txt_horario_alumno_mi18);
        txtH[11][3] = findViewById(R.id.txt_horario_alumno_j18);
        txtH[11][4] = findViewById(R.id.txt_horario_alumno_v18);
        txtH[12][0] = findViewById(R.id.txt_horario_alumno_l19);
        txtH[12][1] = findViewById(R.id.txt_horario_alumno_m19);
        txtH[12][2] = findViewById(R.id.txt_horario_alumno_mi19);
        txtH[12][3] = findViewById(R.id.txt_horario_alumno_j19);
        txtH[12][4] = findViewById(R.id.txt_horario_alumno_v19);
        txtH[13][0] = findViewById(R.id.txt_horario_alumno_l20);
        txtH[13][1] = findViewById(R.id.txt_horario_alumno_m20);
        txtH[13][2] = findViewById(R.id.txt_horario_alumno_mi20);
        txtH[13][3] = findViewById(R.id.txt_horario_alumno_j20);
        txtH[13][4] = findViewById(R.id.txt_horario_alumno_v20);
        ObtenerHorarioAlumnosPrefectos obtenerHorarioAlumnosPrefectos = new ObtenerHorarioAlumnosPrefectos();
        obtenerHorarioAlumnosPrefectos.execute();
        Intent inten = getIntent();
        bole = inten.getStringExtra("boleta");
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

    public class ObtenerHorarioAlumnosPrefectos extends AsyncTask<String, String, Boolean> {
        private String resultado;
        private String hor[][] = new String[14][5];

        @Override
        protected Boolean doInBackground(String... strings) {
            // Metodo que queremos ejecutar en el servicio web
            String Metodo = "horarioAndroidAlumno";
// Namespace definido en el servicio web
            String namespace = "http://servicios/";
// namespace + metodo
            String accionSoap = "hhtp://servicios/horarioAndroidAlumno";
// Fichero de definicion del servcio web
            String url = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/alumno?WSDL";
            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("numero", bole);
            Log.println(Log.ERROR, "Error ", bole);
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
                Log.println(Log.ERROR, "Error ", xd.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(final Boolean aBoolean) {
            try {
                JSONArray info = new JSONArray(resultado);
                JSONObject info2;
                String Dias[] = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
                for (int i = 0; i < hor.length; i++) {
                    info2 = info.getJSONObject(i);
                    for (int j = 0; j < hor[i].length; j++) {
                        hor[i][j] = info2.getString(Dias[j]);

                        //Log.println(Log.DEBUG, "Error: ", hor[i][j]);
                    }
                }
            } catch (Exception xd) {
                Log.println(Log.ERROR, "Error ", xd.getMessage());
            }

            for(int i = 0; i<hor.length; i++){
                for(int j = 0; j<hor[i].length; j++){
                    if(txtH!=null)
                        txtH[i][j].setText(hor[i][j]);
                    else
                        txtH[i][j].setText("");
                    Log.println(Log.DEBUG, "Error: ", bole);
                }
            }
        }
    }
}
