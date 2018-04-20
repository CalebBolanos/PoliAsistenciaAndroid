package edu.cecyt9.ipn.poliasistenciaandroid.jefeAcademia;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.cecyt9.ipn.poliasistenciaandroid.R;

/**
 * Created by Caleb on 19/04/2018.
 */

public class AlumnoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public List<DatosAlumno> alumno;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView imagen;
        TextView nombre;
        TextView grupo;
        TextView boleta;

        public ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_alumno);
            nombre = itemView.findViewById(R.id.nombre);
            grupo = itemView.findViewById(R.id.grupo);
            boleta = itemView.findViewById(R.id.boleta);
        }
    }

    public AlumnoAdapter(Context context, List<DatosAlumno> alumno) {
        this.context = context;
        this.alumno = alumno;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarjeta_alumno, parent, false);
        ViewHolder viewHolder = new ViewHolder(vista);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        viewHolder.imagen.setImageResource(alumno.get(position).getImagen());
        viewHolder.nombre.setText(alumno.get(position).getNombre());
        viewHolder.grupo.setText(alumno.get(position).getGrupo());
        viewHolder.boleta.setText(alumno.get(position).getBoleta());
    }

    @Override
    public int getItemCount() {
        return alumno.size();
    }
}
