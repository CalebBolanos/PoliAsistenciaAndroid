package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHorarioAlumno.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHorarioAlumno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHorarioAlumno extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView[][]txtH=new TextView[14][5];
    ListView listaProfesores;

    private OnFragmentInteractionListener mListener;

    public FragmentHorarioAlumno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHorarioAlumno.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHorarioAlumno newInstance(String param1, String param2) {
        FragmentHorarioAlumno fragment = new FragmentHorarioAlumno();
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
        View vista = inflater.inflate(R.layout.fragment_fragment_horario_alumno, container, false);
        txtH[0][0] = vista.findViewById(R.id.txt_horario_alumno_l7);
        txtH[0][1] = vista.findViewById(R.id.txt_horario_alumno_m7);
        txtH[0][2] = vista.findViewById(R.id.txt_horario_alumno_mi7);
        txtH[0][3] = vista.findViewById(R.id.txt_horario_alumno_j7);
        txtH[0][4] = vista.findViewById(R.id.txt_horario_alumno_v7);
        txtH[1][0] = vista.findViewById(R.id.txt_horario_alumno_l8);
        txtH[1][1] = vista.findViewById(R.id.txt_horario_alumno_m8);
        txtH[1][2] = vista.findViewById(R.id.txt_horario_alumno_mi8);
        txtH[1][3] = vista.findViewById(R.id.txt_horario_alumno_j8);
        txtH[1][4] = vista.findViewById(R.id.txt_horario_alumno_v8);
        txtH[2][0] = vista.findViewById(R.id.txt_horario_alumno_l9);
        txtH[2][1] = vista.findViewById(R.id.txt_horario_alumno_m9);
        txtH[2][2] = vista.findViewById(R.id.txt_horario_alumno_mi9);
        txtH[2][3] = vista.findViewById(R.id.txt_horario_alumno_j9);
        txtH[2][4] = vista.findViewById(R.id.txt_horario_alumno_v9);
        txtH[3][0] = vista.findViewById(R.id.txt_horario_alumno_l10);
        txtH[3][1] = vista.findViewById(R.id.txt_horario_alumno_m10);
        txtH[3][2] = vista.findViewById(R.id.txt_horario_alumno_mi10);
        txtH[3][3] = vista.findViewById(R.id.txt_horario_alumno_j10);
        txtH[3][4] = vista.findViewById(R.id.txt_horario_alumno_v10);
        txtH[4][0] = vista.findViewById(R.id.txt_horario_alumno_l11);
        txtH[4][1] = vista.findViewById(R.id.txt_horario_alumno_m11);
        txtH[4][2] = vista.findViewById(R.id.txt_horario_alumno_mi11);
        txtH[4][3] = vista.findViewById(R.id.txt_horario_alumno_j11);
        txtH[4][4] = vista.findViewById(R.id.txt_horario_alumno_v11);
        txtH[5][0] = vista.findViewById(R.id.txt_horario_alumno_l12);
        txtH[5][1] = vista.findViewById(R.id.txt_horario_alumno_m12);
        txtH[5][2] = vista.findViewById(R.id.txt_horario_alumno_mi12);
        txtH[5][3] = vista.findViewById(R.id.txt_horario_alumno_j12);
        txtH[5][4] = vista.findViewById(R.id.txt_horario_alumno_v12);
        txtH[6][0] = vista.findViewById(R.id.txt_horario_alumno_l13);
        txtH[6][1] = vista.findViewById(R.id.txt_horario_alumno_m13);
        txtH[6][2] = vista.findViewById(R.id.txt_horario_alumno_mi13);
        txtH[6][3] = vista.findViewById(R.id.txt_horario_alumno_j13);
        txtH[6][4] = vista.findViewById(R.id.txt_horario_alumno_v13);
        txtH[7][0] = vista.findViewById(R.id.txt_horario_alumno_l14);
        txtH[7][1] = vista.findViewById(R.id.txt_horario_alumno_m14);
        txtH[7][2] = vista.findViewById(R.id.txt_horario_alumno_mi14);
        txtH[7][3] = vista.findViewById(R.id.txt_horario_alumno_j14);
        txtH[7][4] = vista.findViewById(R.id.txt_horario_alumno_v14);
        txtH[8][0] = vista.findViewById(R.id.txt_horario_alumno_l15);
        txtH[8][1] = vista.findViewById(R.id.txt_horario_alumno_m15);
        txtH[8][2] = vista.findViewById(R.id.txt_horario_alumno_mi15);
        txtH[8][3] = vista.findViewById(R.id.txt_horario_alumno_j15);
        txtH[8][4] = vista.findViewById(R.id.txt_horario_alumno_v15);
        txtH[9][0] = vista.findViewById(R.id.txt_horario_alumno_l16);
        txtH[9][1] = vista.findViewById(R.id.txt_horario_alumno_m16);
        txtH[9][2] = vista.findViewById(R.id.txt_horario_alumno_mi16);
        txtH[9][3] = vista.findViewById(R.id.txt_horario_alumno_j16);
        txtH[9][4] = vista.findViewById(R.id.txt_horario_alumno_v16);
        txtH[10][0] = vista.findViewById(R.id.txt_horario_alumno_l17);
        txtH[10][1] = vista.findViewById(R.id.txt_horario_alumno_m17);
        txtH[10][2] = vista.findViewById(R.id.txt_horario_alumno_mi17);
        txtH[10][3] = vista.findViewById(R.id.txt_horario_alumno_j17);
        txtH[10][4] = vista.findViewById(R.id.txt_horario_alumno_v17);
        txtH[11][0] = vista.findViewById(R.id.txt_horario_alumno_l18);
        txtH[11][1] = vista.findViewById(R.id.txt_horario_alumno_m18);
        txtH[11][2] = vista.findViewById(R.id.txt_horario_alumno_mi18);
        txtH[11][3] = vista.findViewById(R.id.txt_horario_alumno_j18);
        txtH[11][4] = vista.findViewById(R.id.txt_horario_alumno_v18);
        txtH[12][0] = vista.findViewById(R.id.txt_horario_alumno_l19);
        txtH[12][1] = vista.findViewById(R.id.txt_horario_alumno_m19);
        txtH[12][2] = vista.findViewById(R.id.txt_horario_alumno_mi19);
        txtH[12][3] = vista.findViewById(R.id.txt_horario_alumno_j19);
        txtH[12][4] = vista.findViewById(R.id.txt_horario_alumno_v19);
        txtH[13][0] = vista.findViewById(R.id.txt_horario_alumno_l20);
        txtH[13][1] = vista.findViewById(R.id.txt_horario_alumno_m20);
        txtH[13][2] = vista.findViewById(R.id.txt_horario_alumno_mi20);
        txtH[13][3] = vista.findViewById(R.id.txt_horario_alumno_j20);
        txtH[13][4] = vista.findViewById(R.id.txt_horario_alumno_v20);
        ObtenerHorarioAlumnos oH = new ObtenerHorarioAlumnos();
        oH.execute();
        listaProfesores = vista.findViewById(R.id.listview_estatus_profesores);
        DatosEstatusProfesor titulo = new DatosEstatusProfesor("Unidad de Aprendizaje", "Profesor", "Estatus");
        ArrayList<DatosEstatusProfesor> datos = new ArrayList<>();
        datos.add(titulo);
        for(int i = 1; i<=12; i++){
            DatosEstatusProfesor profesorx = new DatosEstatusProfesor("Unidad", "Profesor"+i, "Asistio");
            datos.add(profesorx);
            profesorx = null;
        }
        EstatusProfesorAdapter adaptador = new EstatusProfesorAdapter(getContext(), R.layout.adapter_view_estatus_profesor, datos);
        listaProfesores.setAdapter(adaptador);
        setListViewHeightBasedOnChildren(listaProfesores);
        //datosEstatusprofesor
        //EstatusProfesorAdapter


        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
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

    public class ObtenerHorarioAlumnos extends AsyncTask<String, String, Boolean> {
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
                        hor[i][j] = info2.getString(Dias[j]);

                        //Log.println(Log.DEBUG, "Error: ", hor[i][j]);
                    }
                }
            } catch (Exception xd) {
                Log.println(Log.ERROR, "Error: ", xd.getMessage());
            }

            for(int i = 0; i<hor.length; i++){
                for(int j = 0; j<hor[i].length; j++){
                    txtH[i][j].setText(hor[i][j]);
                    Log.println(Log.DEBUG, "Error: ", hor[i][j]);
                }
            }
        }
    }
}
