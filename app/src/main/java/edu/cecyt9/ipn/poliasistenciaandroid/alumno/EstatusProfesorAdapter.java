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

public class EstatusProfesorAdapter extends ArrayAdapter<DatosEstatusProfesor> {

    private Context context;
    private int resource;

    static class ViewHolder {
        TextView unidad;
        TextView profesor;
        TextView estatus;
    }

    public EstatusProfesorAdapter(@NonNull Context context, int resource, @NonNull ArrayList<DatosEstatusProfesor> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String unidad = getItem(position).getUnidad();
        String profesor = getItem(position).getProfesor();
        String estatus = getItem(position).getEstatus();

        ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflador = LayoutInflater.from(context);
            convertView = inflador.inflate(resource, parent, false);

            holder = new ViewHolder();
            holder.unidad = convertView.findViewById(R.id.textview_unidad);
            holder.profesor = convertView.findViewById(R.id.textview_profesor);
            holder.estatus = convertView.findViewById(R.id.textview_estatus);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.unidad.setText(unidad);
        holder.profesor.setText(profesor);
        holder.estatus.setText(estatus);

        return convertView;
    }
}
