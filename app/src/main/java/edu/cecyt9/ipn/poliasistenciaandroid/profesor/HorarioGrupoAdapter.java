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

import edu.cecyt9.ipn.poliasistenciaandroid.R;

/**
 * Created by Caleb on 30/03/2018.
 */

public class HorarioGrupoAdapter extends ArrayAdapter<HorarioGrupo> {

    private Context context;
    private int resource;

    static class ViewHolder {
        TextView grupo;
        TextView unidad;
        TextView hora;
    }

    public HorarioGrupoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<HorarioGrupo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String grupo = getItem(position).getGrupo();
        String unidad = getItem(position).getUnidad();
        String hora = getItem(position).getHora();

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflador = LayoutInflater.from(context);
            convertView = inflador.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.grupo = convertView.findViewById(R.id.textview_grupo);
            holder.unidad = convertView.findViewById(R.id.textview_unidad);
            holder.hora = convertView.findViewById(R.id.textview_hora);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.grupo.setText(grupo);
        holder.unidad.setText(unidad);
        holder.hora.setText(hora);

        return convertView;
    }
}
