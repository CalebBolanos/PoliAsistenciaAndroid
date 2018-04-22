package edu.cecyt9.ipn.poliasistenciaandroid.prefecto;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Button botonMas;

        public ViewHolder(View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imagen_alumno);
            nombre = itemView.findViewById(R.id.nombre);
            grupo = itemView.findViewById(R.id.grupo);
            boleta = itemView.findViewById(R.id.boleta);
            botonMas = itemView.findViewById(R.id.botonMas);
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
        final String boleta = alumno.get(position).getBoleta();
        viewHolder.botonMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent infoAlumno = new Intent(context, InformacionAlumno.class);
                infoAlumno.putExtra("boleta", boleta);
                context.startActivity(infoAlumno);
            }
        });
    }

    @Override
    public int getItemCount() {
        return alumno.size();
    }
}
