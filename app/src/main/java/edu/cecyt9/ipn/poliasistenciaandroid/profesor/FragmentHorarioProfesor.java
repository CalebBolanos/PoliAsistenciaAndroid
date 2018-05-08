package edu.cecyt9.ipn.poliasistenciaandroid.profesor;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
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

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;

public class FragmentHorarioProfesor extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    ListView listaGrupos;
    TextView txtHP[][] = new TextView[14][5];
    ObtenerHorarioProfesor ohp;

    private OnFragmentInteractionListener mListener;

    public FragmentHorarioProfesor() {
        // Required empty public constructor
    }

    public static FragmentHorarioProfesor newInstance(String param1, String param2) {
        FragmentHorarioProfesor fragment = new FragmentHorarioProfesor();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_horario_profesor, container, false);
        txtHP[0][0] = vista.findViewById(R.id.txt_horario_profesor_l7);
        txtHP[0][1] = vista.findViewById(R.id.txt_horario_profesor_m7);
        txtHP[0][2] = vista.findViewById(R.id.txt_horario_profesor_mi7);
        txtHP[0][3] = vista.findViewById(R.id.txt_horario_profesor_j7);
        txtHP[0][4] = vista.findViewById(R.id.txt_horario_profesor_v7);
        txtHP[1][0] = vista.findViewById(R.id.txt_horario_profesor_l8);
        txtHP[1][1] = vista.findViewById(R.id.txt_horario_profesor_m8);
        txtHP[1][2] = vista.findViewById(R.id.txt_horario_profesor_mi8);
        txtHP[1][3] = vista.findViewById(R.id.txt_horario_profesor_j8);
        txtHP[1][4] = vista.findViewById(R.id.txt_horario_profesor_v8);
        txtHP[2][0] = vista.findViewById(R.id.txt_horario_profesor_l9);
        txtHP[2][1] = vista.findViewById(R.id.txt_horario_profesor_m9);
        txtHP[2][2] = vista.findViewById(R.id.txt_horario_profesor_mi9);
        txtHP[2][3] = vista.findViewById(R.id.txt_horario_profesor_j9);
        txtHP[2][4] = vista.findViewById(R.id.txt_horario_profesor_v9);
        txtHP[3][0] = vista.findViewById(R.id.txt_horario_profesor_l10);
        txtHP[3][1] = vista.findViewById(R.id.txt_horario_profesor_m10);
        txtHP[3][2] = vista.findViewById(R.id.txt_horario_profesor_mi10);
        txtHP[3][3] = vista.findViewById(R.id.txt_horario_profesor_j10);
        txtHP[3][4] = vista.findViewById(R.id.txt_horario_profesor_v10);
        txtHP[4][0] = vista.findViewById(R.id.txt_horario_profesor_l11);
        txtHP[4][1] = vista.findViewById(R.id.txt_horario_profesor_m11);
        txtHP[4][2] = vista.findViewById(R.id.txt_horario_profesor_mi11);
        txtHP[4][3] = vista.findViewById(R.id.txt_horario_profesor_j11);
        txtHP[4][4] = vista.findViewById(R.id.txt_horario_profesor_v11);
        txtHP[5][0] = vista.findViewById(R.id.txt_horario_profesor_l12);
        txtHP[5][1] = vista.findViewById(R.id.txt_horario_profesor_m12);
        txtHP[5][2] = vista.findViewById(R.id.txt_horario_profesor_mi12);
        txtHP[5][3] = vista.findViewById(R.id.txt_horario_profesor_j12);
        txtHP[5][4] = vista.findViewById(R.id.txt_horario_profesor_v12);
        txtHP[6][0] = vista.findViewById(R.id.txt_horario_profesor_l13);
        txtHP[6][1] = vista.findViewById(R.id.txt_horario_profesor_m13);
        txtHP[6][2] = vista.findViewById(R.id.txt_horario_profesor_mi13);
        txtHP[6][3] = vista.findViewById(R.id.txt_horario_profesor_j13);
        txtHP[6][4] = vista.findViewById(R.id.txt_horario_profesor_v13);
        txtHP[7][0] = vista.findViewById(R.id.txt_horario_profesor_l14);
        txtHP[7][1] = vista.findViewById(R.id.txt_horario_profesor_m14);
        txtHP[7][2] = vista.findViewById(R.id.txt_horario_profesor_mi14);
        txtHP[7][3] = vista.findViewById(R.id.txt_horario_profesor_j14);
        txtHP[7][4] = vista.findViewById(R.id.txt_horario_profesor_v14);
        txtHP[8][0] = vista.findViewById(R.id.txt_horario_profesor_l15);
        txtHP[8][1] = vista.findViewById(R.id.txt_horario_profesor_m15);
        txtHP[8][2] = vista.findViewById(R.id.txt_horario_profesor_mi15);
        txtHP[8][3] = vista.findViewById(R.id.txt_horario_profesor_j15);
        txtHP[8][4] = vista.findViewById(R.id.txt_horario_profesor_v15);
        txtHP[9][0] = vista.findViewById(R.id.txt_horario_profesor_l16);
        txtHP[9][1] = vista.findViewById(R.id.txt_horario_profesor_m16);
        txtHP[9][2] = vista.findViewById(R.id.txt_horario_profesor_mi16);
        txtHP[9][3] = vista.findViewById(R.id.txt_horario_profesor_j16);
        txtHP[9][4] = vista.findViewById(R.id.txt_horario_profesor_v16);
        txtHP[10][0] = vista.findViewById(R.id.txt_horario_profesor_l17);
        txtHP[10][1] = vista.findViewById(R.id.txt_horario_profesor_m17);
        txtHP[10][2] = vista.findViewById(R.id.txt_horario_profesor_mi17);
        txtHP[10][3] = vista.findViewById(R.id.txt_horario_profesor_j17);
        txtHP[10][4] = vista.findViewById(R.id.txt_horario_profesor_v17);
        txtHP[11][0] = vista.findViewById(R.id.txt_horario_profesor_l18);
        txtHP[11][1] = vista.findViewById(R.id.txt_horario_profesor_m18);
        txtHP[11][2] = vista.findViewById(R.id.txt_horario_profesor_mi18);
        txtHP[11][3] = vista.findViewById(R.id.txt_horario_profesor_j18);
        txtHP[11][4] = vista.findViewById(R.id.txt_horario_profesor_v18);
        txtHP[12][0] = vista.findViewById(R.id.txt_horario_profesor_l19);
        txtHP[12][1] = vista.findViewById(R.id.txt_horario_profesor_m19);
        txtHP[12][2] = vista.findViewById(R.id.txt_horario_profesor_mi19);
        txtHP[12][3] = vista.findViewById(R.id.txt_horario_profesor_j19);
        txtHP[12][4] = vista.findViewById(R.id.txt_horario_profesor_v19);
        txtHP[13][0] = vista.findViewById(R.id.txt_horario_profesor_l20);
        txtHP[13][1] = vista.findViewById(R.id.txt_horario_profesor_m20);
        txtHP[13][2] = vista.findViewById(R.id.txt_horario_profesor_mi20);
        txtHP[13][3] = vista.findViewById(R.id.txt_horario_profesor_j20);
        txtHP[13][4] = vista.findViewById(R.id.txt_horario_profesor_v20);
        ohp = new ObtenerHorarioProfesor();
        ohp.execute();
        listaGrupos = vista.findViewById(R.id.listview_datos_grupos);
        DatosGrupos titulo = new DatosGrupos("Grupo", "Unidad", "profesors", "Especialidad");
        ArrayList<DatosGrupos> datos = new ArrayList<>();
        datos.add(titulo);
        GruposAdapter adaptador = new GruposAdapter(getContext(), R.layout.adapter_view_grupos, datos);
        listaGrupos.setAdapter(adaptador);
        listaGrupos.setFocusable(false);
        setListViewHeightBasedOnChildren(listaGrupos);

        return vista;
    }

    @Override
    public void onPause() {
        super.onPause();
        ohp.cancel(true);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
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

    public class ObtenerHorarioProfesor extends AsyncTask<String, String, Boolean> {
        private String resultado;
        private String hor[][] = new String[14][5];

        @Override
        protected Boolean doInBackground(String... strings) {
            // Metodo que queremos ejecutar en el servicio web
            String Metodo = "horarioAndroidProfesor";
            // Namespace definido en el servicio web
            String namespace = "http://servicios/";
            // namespace + metodo
            String accionSoap = "hhtp://servicios/horarioAndroidProfesor";
            // Fichero de definicion del servcio web
            String url = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/profesor?WSDL";
            Sesion ses = new Sesion(getActivity().getApplicationContext());
            String bol = ses.getNum();
            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("numero", bol);
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
        protected void onPostExecute(final Boolean aBoolean) {
            try {
                JSONArray info = new JSONArray(resultado);
                JSONObject info2;
                String Dias[] = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes"};
                for (int i = 0; i < hor.length; i++) {
                    info2 = info.getJSONObject(i);
                    for (int j = 0; j < hor[i].length; j++) {
                        hor[i][j] = info2.getString(Dias[j])!=null?info2.getString(Dias[j]):"";

                        //Log.println(Log.DEBUG, "Error: ", hor[i][j]);
                    }
                }
            } catch (Exception xd) {
                Log.println(Log.ERROR, "Error: ", xd.getMessage());
            }

            for(int i = 0; i<hor.length; i++){
                for(int j = 0; j<hor[i].length; j++){
                    txtHP[i][j].setText(hor[i][j]);
                    Log.println(Log.DEBUG, "Error: ", hor[i][j]);
                }
            }
        }
    }
}
