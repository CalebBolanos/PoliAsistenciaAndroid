package edu.cecyt9.ipn.poliasistenciaandroid.profesor;

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

import edu.cecyt9.ipn.poliasistenciaandroid.R;

/**
 * Created by Caleb on 30/03/2018.
 */

public class GruposAdapter extends ArrayAdapter<DatosGrupos> {
    private Context context;
    private int resource;

    static class ViewHolder {
        TextView grupo;
        TextView unidad;
        TextView alumnos;
        TextView especialidad;
    }

    public GruposAdapter(@NonNull Context context, int resource, @NonNull List<DatosGrupos> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String grupo = getItem(position).getGrupo();
        String unidad = getItem(position).getUnidad();
        String alumnos = getItem(position).getAlumnos();
        String especialidad = getItem(position).getEspecialidad();


        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflador = LayoutInflater.from(context);
            convertView = inflador.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.grupo = convertView.findViewById(R.id.textview_grupo);
            holder.unidad = convertView.findViewById(R.id.textview_unidad);
            holder.alumnos = convertView.findViewById(R.id.textview_alumnos);
            holder.especialidad = convertView.findViewById(R.id.textview_especialidad);

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.grupo.setText(grupo);
        holder.unidad.setText(unidad);
        holder.alumnos.setText(alumnos);
        holder.especialidad.setText(especialidad);

        return convertView;
    }
}
