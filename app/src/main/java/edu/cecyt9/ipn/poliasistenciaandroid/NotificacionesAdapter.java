package edu.cecyt9.ipn.poliasistenciaandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.cecyt9.ipn.poliasistenciaandroid.alumno.FragmentNotificacionesAlumno;

/**
 * Created by Caleb on 29/03/2018.
 */

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.ViewHolder> {
    Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imagenUsuario;
        TextView titulo;
        AppCompatTextView descripcion;
        ImageView imagen;
        Button botonUrl;

        public ViewHolder(View itemView) {
            super(itemView);

            imagenUsuario = itemView.findViewById(R.id.imagen_notificacion_imagen);
            titulo = itemView.findViewById(R.id.txt_notificacion_imagen);
            descripcion = itemView.findViewById(R.id.descripcion_imagen);
            imagen = itemView.findViewById(R.id.imageView2);
            botonUrl = itemView.findViewById(R.id.button_imagen);
        }
    }

    public List<DatosNotificacionImagenUrl> notificacionesImagen;

    public NotificacionesAdapter(Context context, List<DatosNotificacionImagenUrl> notificacionesImagen) {
        this.context = context;
        this.notificacionesImagen = notificacionesImagen;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacion_imagen_url, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.imagenUsuario.setImageResource(notificacionesImagen.get(position).getImagenUsuario());
        holder.titulo.setText(notificacionesImagen.get(position).getTitulo());
        holder.descripcion.setText(notificacionesImagen.get(position).getDescripcion());
        holder.imagen.setImageResource(notificacionesImagen.get(position).getImagen());
        holder.botonUrl.setTag(notificacionesImagen.get(position).getUrl());
        holder.botonUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navegador = new Intent(context, WebViewNotificaciones.class);
                //navegador.putExtra("Url", holder.botonUrl.getTag());
                context.startActivity(navegador);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notificacionesImagen.size();
    }
}
