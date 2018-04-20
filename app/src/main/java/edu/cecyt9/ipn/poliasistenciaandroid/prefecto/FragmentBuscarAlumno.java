package edu.cecyt9.ipn.poliasistenciaandroid.prefecto;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia.AlumnoAdapter;
import edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia.DatosAlumno;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentBuscarAlumno.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentBuscarAlumno#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentBuscarAlumno extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerAlumno;
    AlumnoAdapter adaptador;

    public FragmentBuscarAlumno() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBuscarAlumno.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBuscarAlumno newInstance(String param1, String param2) {
        FragmentBuscarAlumno fragment = new FragmentBuscarAlumno();
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
        View vista = inflater.inflate(R.layout.fragment_buscar_alumno, container, false);
        recyclerAlumno = vista.findViewById(R.id.recycler_alumnos);
        recyclerAlumno.setLayoutManager(new LinearLayoutManager(getContext()));
        List<DatosAlumno> alumnos = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            DatosAlumno alumnox = new DatosAlumno(R.drawable.sanic, "Alumno "+ i, "6IM7", "201609000"+i);
            alumnos.add(alumnox);
            alumnox = null;
        }
        adaptador = new AlumnoAdapter(getContext(), alumnos);
        recyclerAlumno.setAdapter(adaptador);

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
}
