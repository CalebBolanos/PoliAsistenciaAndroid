package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

import edu.cecyt9.ipn.poliasistenciaandroid.DatosNotificacion;
import edu.cecyt9.ipn.poliasistenciaandroid.NotificacionesAdapter;
import edu.cecyt9.ipn.poliasistenciaandroid.R;


public class FragmentNotificacionesAlumno extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    RecyclerView recyclerNotificaciones;
    NotificacionesAdapter adaptador;
    SwipeRefreshLayout refrescar;

    public FragmentNotificacionesAlumno() {
        // Required empty public constructor
    }

    public static FragmentNotificacionesAlumno newInstance(String param1, String param2) {
        FragmentNotificacionesAlumno fragment = new FragmentNotificacionesAlumno();
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
        View vista = inflater.inflate(R.layout.fragment_notificaciones_alumno, container, false);
        recyclerNotificaciones = vista.findViewById(R.id.recycler_notificaciones);
        recyclerNotificaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        List<DatosNotificacion> notificaciones = new ArrayList<>();
        DatosNotificacion notificacionprueba = new DatosNotificacion(NotificacionesAdapter.NOTIFICACION_URL, "jefe",R.drawable.sanic, "notificacion sin imagen", "descripcion", 0, "sin imagen", false);
        notificaciones.add(notificacionprueba);
        for (int i = 0; i < 5; i++) {
            DatosNotificacion notificacionx = new DatosNotificacion(NotificacionesAdapter.NOTIFICACION_IMAGEN_URL, "profesor", R.drawable.sanic, "notificacion"+i, "Descripcion xd", R.drawable.sanic, "Url"+i, false);
            notificaciones.add(notificacionx);
            notificacionx = null;
        }

        adaptador = new NotificacionesAdapter(getContext(), notificaciones);
        recyclerNotificaciones.setAdapter(adaptador);

        refrescar = vista.findViewById(R.id.swipeRefreshLayout);
        refrescar.setColorSchemeResources(R.color.colorPrimary);
        refrescar.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refrescar.setRefreshing(true);
                adaptador.notifyDataSetChanged();
                refrescar.setRefreshing(false);
            }
        });

        return vista;
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

}
