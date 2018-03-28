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
 * Created by Caleb on 28/03/2018.
 */

public class AsistenciaUnidadDiaAdapter extends ArrayAdapter<DatosAsistenciaUnidadDia> {

    private Context context;
    private int resource;

    static class ViewHolder {
        TextView boleta;
        TextView nombre;
        TextView asistencia;
    }

    public AsistenciaUnidadDiaAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DatosAsistenciaUnidadDia> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String boleta = getItem(position).getBoleta();
        String nombre = getItem(position).getNombre();
        String asistencia = getItem(position).getAsistencia();

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflador = LayoutInflater.from(context);
            convertView = inflador.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.boleta = convertView.findViewById(R.id.textview_boleta);
            holder.nombre = convertView.findViewById(R.id.textview_nombre);
            holder.asistencia = convertView.findViewById(R.id.textview_asistencia);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.boleta.setText(boleta);
        holder.nombre.setText(nombre);
        holder.asistencia.setText(asistencia);
        return convertView;
    }
}
