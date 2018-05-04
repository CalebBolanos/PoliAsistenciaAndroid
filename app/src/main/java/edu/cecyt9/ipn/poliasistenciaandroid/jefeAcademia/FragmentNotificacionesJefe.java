package edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.cecyt9.ipn.poliasistenciaandroid.CrearNotificacion;
import edu.cecyt9.ipn.poliasistenciaandroid.DatosNotificacion;
import edu.cecyt9.ipn.poliasistenciaandroid.NotificacionesAdapter;
import edu.cecyt9.ipn.poliasistenciaandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentNotificacionesJefe.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentNotificacionesJefe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentNotificacionesJefe extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerNotificaciones;
    NotificacionesAdapter adaptador;
    SwipeRefreshLayout refrescar;

    private OnFragmentInteractionListener mListener;

    public FragmentNotificacionesJefe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentNotificacionesJefe.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentNotificacionesJefe newInstance(String param1, String param2) {
        FragmentNotificacionesJefe fragment = new FragmentNotificacionesJefe();
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
        View vista = inflater.inflate(R.layout.fragment_notificaciones_jefe, container, false);
        recyclerNotificaciones = vista.findViewById(R.id.recycler_notificaciones);
        recyclerNotificaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        final List<DatosNotificacion> notificaciones = new ArrayList<>();//Hacer notificaciones con boton borrar
        DatosNotificacion notificacionprueba = new DatosNotificacion(NotificacionesAdapter.NOTIFICACION_URL, "Jefe", "", "notificacion sin imagen", "descripcion", "", "sin imagen", true);
        notificaciones.add(notificacionprueba);
        for (int i = 0; i < 5; i++) {
            DatosNotificacion notificacionx = new DatosNotificacion(NotificacionesAdapter.NOTIFICACION_IMAGEN_URL, "Jefe", "", "notificacion"+i, "Descripcion xd", "", "Url"+i, true);
            notificaciones.add(notificacionx);
            notificacionx = null;
        }

        adaptador = new NotificacionesAdapter(getContext(), notificaciones);
        recyclerNotificaciones.setAdapter(adaptador);

        final RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(getContext()) {
            @Override protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };

        FloatingActionButton fab = vista.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent subirNotificacion = new Intent(getContext(), CrearNotificacion.class);
                startActivity(subirNotificacion);
            }
        });

        refrescar = vista.findViewById(R.id.swipeRefreshLayout);
        refrescar.setColorSchemeResources(R.color.colorPrimary);
        refrescar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refrescar.setRefreshing(true);
                notificaciones.add(0, new DatosNotificacion(NotificacionesAdapter.NOTIFICACION_URL, "Jefe", "", "Notificacion", "notificacion Agregada con SwipeRefreshLayout", "", "sin imagen", true));
                adaptador.notifyItemInserted(0);//a veces da error xdxd
                smoothScroller.setTargetPosition(0);
                recyclerNotificaciones.getLayoutManager().startSmoothScroll(smoothScroller);
                //notificaciones.add(new DatosNotificacion(NotificacionesAdapter.NOTIFICACION_URL, "Jefe", R.drawable.sanic, "Notificacion", "notificacion Agregada con SwipeRefreshLayout", 0, "sin imagen", true));
                //adaptador.notifyItemInserted(notificaciones.size() - 1);
                refrescar.setRefreshing(false);
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
