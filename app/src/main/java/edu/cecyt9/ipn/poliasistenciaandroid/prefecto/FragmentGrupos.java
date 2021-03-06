package edu.cecyt9.ipn.poliasistenciaandroid.prefecto;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.cecyt9.ipn.poliasistenciaandroid.AsistenciaUnidad;
import edu.cecyt9.ipn.poliasistenciaandroid.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentGrupos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentGrupos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentGrupos extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ListView listaGrupo;

    public FragmentGrupos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentGrupos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentGrupos newInstance(String param1, String param2) {
        FragmentGrupos fragment = new FragmentGrupos();
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
        //setHasOptionsMenu(true);
        View vista = inflater.inflate(R.layout.fragment_grupos, container, false);
        listaGrupo = vista.findViewById(R.id.listview_grupos);
        ArrayList<String> arrayGrupos = new ArrayList<>();
        for(int i = 1; i<=50; i++){
            arrayGrupos.add("Grupo " + i);
        }
        ArrayAdapter adaptador = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrayGrupos);
        listaGrupo.setAdapter(adaptador);
        listaGrupo.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String seleccionado = listaGrupo.getItemAtPosition(i).toString();
                switch (seleccionado){
                    case "":

                        break;
                    default:
                        Intent graficas = new Intent(getActivity(), AsistenciaUnidad.class);
                        startActivity(graficas);
                        break;
                }

            }
        });
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
