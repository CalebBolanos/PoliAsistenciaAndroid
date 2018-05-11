package edu.cecyt9.ipn.poliasistenciaandroid.prefecto;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;
import java.util.List;

import edu.cecyt9.ipn.poliasistenciaandroid.R;
import edu.cecyt9.ipn.poliasistenciaandroid.Sesion;

import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.IP;
import static edu.cecyt9.ipn.poliasistenciaandroid.InicioSesion.PUERTO;


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
    MaterialSearchView busqueda;
    List<DatosAlumno> alumnos = new ArrayList<>();
    ArrayList<String[]> listaAlumnosAS = new ArrayList<>();

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
        ObtenerListaAlumnos obLiAl = new ObtenerListaAlumnos();
        obLiAl.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View vista = inflater.inflate(R.layout.fragment_buscar_alumno, container, false);
        recyclerAlumno = vista.findViewById(R.id.recycler_alumnos);
        recyclerAlumno.setLayoutManager(new LinearLayoutManager(getContext()));
        alumnos = null;
        alumnos = new ArrayList<>();
        for (int i = 0; i < listaAlumnosAS.size(); i++) {
            DatosAlumno alumnox = new DatosAlumno(R.drawable.sanic, listaAlumnosAS.get(i)[0], listaAlumnosAS.get(i)[1], listaAlumnosAS.get(i)[2]);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.busqueda, menu);
        busqueda = getActivity().findViewById(R.id.search_view);
        final MenuItem item = menu.findItem(R.id.action_search);
        busqueda.setMenuItem(item);

        busqueda.closeSearch();
        busqueda.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<DatosAlumno> busqueda = filtrarAlumno(alumnos, newText);
                AlumnoAdapter adaptadorBusqueda = new AlumnoAdapter(getContext(), busqueda);
                recyclerAlumno.swapAdapter(adaptadorBusqueda, false);
                return true;
            }
        });

        busqueda.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                recyclerAlumno.swapAdapter(adaptador, false);
            }
        });


    }

    public List<DatosAlumno> filtrarAlumno(List<DatosAlumno> alumnos, String busqueda){
        List<DatosAlumno> filtrado = new ArrayList<>();
        String boleta = "";
        String nombre = "";
        String grupo = "";

        for(DatosAlumno alumno: alumnos ){
            boleta = alumno.getBoleta().toLowerCase();
            nombre = alumno.getNombre().toLowerCase();
            grupo = alumno.getGrupo().toLowerCase();
            if(boleta.contains(busqueda) || nombre.contains(busqueda) || grupo.contains(busqueda)){
                filtrado.add(alumno);
            }
        }
        return filtrado;
    }

    public class ObtenerListaAlumnos extends AsyncTask<String, String, Boolean> {
        private String resultado;
        @Override
        protected Boolean doInBackground(String... strings) {
            // Metodo que queremos ejecutar en el servicio web
            String Metodo = "listaDeAlumnosAndroid";
// Namespace definido en el servicio web
            String namespace = "http://servicios/";
// namespace + metodo
            String accionSoap = "hhtp://servicios/listaDeAlumnosAndroid";
// Fichero de definicion del servcio web
            String url = "http://"+IP+":"+PUERTO+"/serviciosWebPoliAsistencia/prefectos?WSDL";
            Sesion ses = new Sesion(getActivity().getApplicationContext());
            String bol = ses.getNum();
            SoapObject request = new SoapObject(namespace, Metodo);
            request.addProperty("numeroPrefecto", bol);
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
                String [] primerArreglo;
                JSONArray info = new JSONArray(resultado);
                JSONObject info2;
                for(int i = 0; i<info.length(); i++){
                    info2 = info.getJSONObject(i);
                    primerArreglo = new String[3];
                    primerArreglo[0] = info2.getString("Nombre");
                    primerArreglo[1] = info2.getString("boleta");
                    primerArreglo[2] = info2.getString("grupo");
                    listaAlumnosAS.add(primerArreglo);
                }
            } catch (Exception xd) {
                Log.println(Log.ERROR, "Error: ", xd.getMessage());
            }
        }
    }
}
