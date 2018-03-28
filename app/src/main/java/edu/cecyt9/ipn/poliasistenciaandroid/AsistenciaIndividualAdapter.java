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

/**
 * Created by Caleb on 27/03/2018.
 */

public class AsistenciaIndividualAdapter extends ArrayAdapter<datosAsistenciaIndividual> {

    private Context context;
    private int resource;

    static class ViewHolder {
        TextView asistido;
        TextView dia;
        TextView entrada;
        TextView salida;
    }

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
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflador = LayoutInflater.from(context);
            convertView = inflador.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.asistido = convertView.findViewById(R.id.textview_asistido);
            holder.dia = convertView.findViewById(R.id.textview_dia);
            holder.entrada = convertView.findViewById(R.id.textview_entrada);
            holder.salida = convertView.findViewById(R.id.textview_salida);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.asistido.setText(asistido);
        holder.dia.setText(dia);
        holder.entrada.setText(entrada);
        holder.salida.setText(salida);

        return convertView;


    }
}
