package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.cecyt9.ipn.poliasistenciaandroid.R;


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
    TextView[][]txtH;
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
        String sH[][] = {{"txt_horario_alumno_l7", "txt_horario_alumno_m7", "txt_horario_alumno_mi7", "txt_horario_alumno_j7", "txt_horario_alumno_v7"},
                {"txt_horario_alumno_l8", "txt_horario_alumno_m8", "txt_horario_alumno_mi8", "txt_horario_alumno_j8", "txt_horario_alumno_v8"},
                {"txt_horario_alumno_l9", "txt_horario_alumno_m9", "txt_horario_alumno_mi9", "txt_horario_alumno_j9", "txt_horario_alumno_v9"},
                {"txt_horario_alumno_l10", "txt_horario_alumno_m10", "txt_horario_alumno_mi10", "txt_horario_alumno_j10", "txt_horario_alumno_v10"},
                {"txt_horario_alumno_l11", "txt_horario_alumno_m11", "txt_horario_alumno_mi11", "txt_horario_alumno_j11", "txt_horario_alumno_v11"},
                {"txt_horario_alumno_l12", "txt_horario_alumno_m12", "txt_horario_alumno_mi12", "txt_horario_alumno_j12", "txt_horario_alumno_v12"},
                {"txt_horario_alumno_l13", "txt_horario_alumno_m13", "txt_horario_alumno_mi13", "txt_horario_alumno_j13", "txt_horario_alumno_v13"},
                {"txt_horario_alumno_l14", "txt_horario_alumno_m14", "txt_horario_alumno_mi14", "txt_horario_alumno_j14", "txt_horario_alumno_v14"},
                {"txt_horario_alumno_l15", "txt_horario_alumno_m15", "txt_horario_alumno_mi15", "txt_horario_alumno_j15", "txt_horario_alumno_v15"},
                {"txt_horario_alumno_l16", "txt_horario_alumno_m16", "txt_horario_alumno_mi16", "txt_horario_alumno_j16", "txt_horario_alumno_v16"},
                {"txt_horario_alumno_l17", "txt_horario_alumno_m17", "txt_horario_alumno_mi17", "txt_horario_alumno_j17", "txt_horario_alumno_v17"},
                {"txt_horario_alumno_l18", "txt_horario_alumno_m18", "txt_horario_alumno_mi18", "txt_horario_alumno_j18", "txt_horario_alumno_v18"},
                {"txt_horario_alumno_l19", "txt_horario_alumno_m19", "txt_horario_alumno_mi19", "txt_horario_alumno_j19", "txt_horario_alumno_v19"},
                {"txt_horario_alumno_l20", "txt_horario_alumno_m20", "txt_horario_alumno_mi20", "txt_horario_alumno_j20", "txt_horario_alumno_v20"}};

        // Inflate the layout for this fragment
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
        // Metodo que queremos ejecutar en el servicio web
         String Metodo = "horarioAlumno";
// Namespace definido en el servicio web
         String namespace = "192.168.100.4:16645/serviciosWebPoliAsistencia/alumno";
// namespace + metodo
        String accionSoap = "192.168.100.4:16645/serviciosWebPoliAsistencia/alumno/horarioAlumno";
// Fichero de definicion del servcio web
        String url = "http://www.webservicex.net/globalweather.asmx";

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
}
