package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caleb on 27/03/2018.
 */

public class AsistenciaIndividualAdapter extends ArrayAdapter<datosAsistenciaIndividual> {

    private Context context;
    int resource;

    public AsistenciaIndividualAdapter(@NonNull Context context, int resource, @NonNull ArrayList<datosAsistenciaIndividual> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String asistido = getItem(position).getAisistido();
        String dia = getItem(position).getDia();
        String entrada = getItem(position).getEntrada();
        String salida = getItem(position).getSalida();

        datosAsistenciaIndividual diax = new datosAsistenciaIndividual(asistido, dia, entrada, salida);
        LayoutInflater inflador = LayoutInflater.from(context);
        convertView = inflador.inflate(resource, parent, false);

        TextView txtAsistido = convertView.findViewById(R.id.textview_asistido);
        TextView txtDia = convertView.findViewById(R.id.textview_dia);
        TextView txtEntrada = convertView.findViewById(R.id.textview_entrada);
        TextView txtSalida = convertView.findViewById(R.id.textview_salida);
        txtAsistido.setText(asistido);
        txtDia.setText(dia);
        txtEntrada.setText(entrada);
        txtSalida.setText(salida);
        return convertView;


    }
}
