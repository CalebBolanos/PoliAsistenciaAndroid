package edu.cecyt9.ipn.poliasistenciaandroid.profesor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.jetbrains.annotations.Contract;
import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import edu.cecyt9.ipn.poliasistenciaandroid.DatosNotificacion;
import edu.cecyt9.ipn.poliasistenciaandroid.NotificacionesAdapter;
import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.WebViewNotificaciones;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;

public class FragmentNotificacionesProfesor extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    RecyclerView recyclerNotificaciones;
    NotificacionesAdapter adaptador;
    SwipeRefreshLayout refrescar;
    String resultado;
    View vista;
    obtenerNotificacionesAndroid obtener;

    public FragmentNotificacionesProfesor() {
        // Required empty public constructor
    }

    public static FragmentNotificacionesProfesor newInstance(String param1, String param2) {
        FragmentNotificacionesProfesor fragment = new FragmentNotificacionesProfesor();
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
        if(vista != null){
            if((ViewGroup)vista.getParent()!=null){
                ((ViewGroup)vista.getParent()).removeView(vista);
            }
            return vista;
        }
        vista = inflater.inflate(R.layout.fragment_notificaciones_profesor, container, false);
        recyclerNotificaciones = vista.findViewById(R.id.recycler_notificaciones);
        recyclerNotificaciones.setLayoutManager(new LinearLayoutManager(getContext()));
        refrescar = vista.findViewById(R.id.swipeRefreshLayout);
        refrescar.setColorSchemeResources(R.color.colorPrimary);
        refrescar.setOnRefreshListener(this);
        obtener = new obtenerNotificacionesAndroid();
        obtener.execute();
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

    @Override
    public void onPause() {
        super.onPause();
        obtener.cancel(true);
    }

    @Override
    public void onRefresh() {
        obtener = new obtenerNotificacionesAndroid();
        obtener.execute();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    @SuppressLint("StaticFieldLeak")
    public class obtenerNotificacionesAndroid extends AsyncTask<String, String, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {

            String NAMESPACE = "http://servicios/";
            String URL = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/alumno?WSDL";
            String METHOD_NAME = "obtenerNotificacionesAndroid";
            String SOAP_ACTION = "http://servicios/obtenerNotificacionesAndroid";


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("tipoNotificacion", "2");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);

            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.debug = true;

            try{
                ht.call(SOAP_ACTION, envelope);
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
                List<DatosNotificacion> noti = new ArrayList<>();
                try {
                    JSONArray notificaciones = new JSONArray(resultado);
                    for (int i = 0; i < notificaciones.length(); i++) {
                        DatosNotificacion notificacionx = new DatosNotificacion(
                                Integer.parseInt(notificaciones.getJSONObject(i).getString("tipoNotificacion")),
                                notificaciones.getJSONObject(i).getString("usuario"),
                                notificaciones.getJSONObject(i).getString("imagenUsuario"),
                                notificaciones.getJSONObject(i).getString("titulo"),
                                notificaciones.getJSONObject(i).getString("descripcion"),
                                notificaciones.getJSONObject(i).getString("imagen"),
                                notificaciones.getJSONObject(i).getString("url"),
                                false,
                                notificaciones.getJSONObject(i).getInt("idNoti"));
                        noti.add(notificacionx);
                        notificacionx = null;
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adaptador = new NotificacionesAdapter(getContext(), noti);
                recyclerNotificaciones.setAdapter(adaptador);
                adaptador.notifyDataSetChanged();
                refrescar.setRefreshing(false);
            }
            else{
                Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                refrescar.setRefreshing(false);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

}
