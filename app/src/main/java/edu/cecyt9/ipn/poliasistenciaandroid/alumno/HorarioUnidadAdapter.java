package edu.cecyt9.ipn.poliasistenciaandroid.alumno;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import edu.cecyt9.ipn.poliasistenciaandroid.R;

/**
 * Created by Caleb on 29/03/2018.
 */

public class HorarioUnidadAdapter extends ArrayAdapter<DatosHorarioAlumno> {

    private Context context;
    private int resource;

    static class ViewHolder {
        TextView unidad;
        TextView hora;
    }

    public HorarioUnidadAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DatosHorarioAlumno> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String unidad = getItem(position).getUnidad();
        String hora = getItem(position).getHora();

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflador = LayoutInflater.from(context);
            convertView = inflador.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.unidad = convertView.findViewById(R.id.textview_unidad);
            holder.hora = convertView.findViewById(R.id.textview_hora);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }
        holder.unidad.setText(unidad);
        holder.hora.setText(hora);

        return convertView;
    }
}
