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

/**
 * Created by Caleb on 29/03/2018.
 */

public class NotificacionesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    public List<DatosNotificacion> notificaciones;

    public static final int NOTIFICACION = 1;
    public static final int NOTIFICACION_IMAGEN = 2;
    public static final int NOTIFICACION_URL = 3;
    public static final int NOTIFICACION_IMAGEN_URL = 4;

    //Notificaciones con imagen y url
    public static class ViewHolderNotificacionImagenUrl extends RecyclerView.ViewHolder{
        CircleImageView imagenUsuario;
        TextView titulo;
        AppCompatTextView descripcion;
        ImageView imagen;
        Button botonUrl;

        public ViewHolderNotificacionImagenUrl(View itemView) {
            super(itemView);

            imagenUsuario = itemView.findViewById(R.id.imagen_notificacion_imagen);
            titulo = itemView.findViewById(R.id.txt_notificacion_imagen);
            descripcion = itemView.findViewById(R.id.descripcion_imagen);
            imagen = itemView.findViewById(R.id.imageView2);
            botonUrl = itemView.findViewById(R.id.button_imagen);
        }
    }

    //Notificaciones con url
    public static class ViewHolderNotificacionUrl extends RecyclerView.ViewHolder{
        CircleImageView imagenUsuario;
        TextView titulo;
        AppCompatTextView descripcion;
        Button botonUrl;

        public ViewHolderNotificacionUrl(View itemView) {
            super(itemView);

            imagenUsuario = itemView.findViewById(R.id.imagen_notificacion);
            titulo = itemView.findViewById(R.id.txt_notificacion);
            descripcion = itemView.findViewById(R.id.descripcion);
            botonUrl = itemView.findViewById(R.id.botonNotifi);
        }
    }



    public NotificacionesAdapter(Context context, List<DatosNotificacion> notificaciones) {
        this.context = context;
        this.notificaciones = notificaciones;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            /*
            case NOTIFICACION:
                break;
            case NOTIFICACION_IMAGEN:
                break;*/
            case NOTIFICACION_URL:
                View viewUrl = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacion_url, parent, false);
                ViewHolderNotificacionUrl holderUrl = new ViewHolderNotificacionUrl(viewUrl);
                return holderUrl;
            case NOTIFICACION_IMAGEN_URL:
                View viewImagenUrl = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notificacion_imagen_url, parent, false);
                ViewHolderNotificacionImagenUrl holderImagenUrl = new ViewHolderNotificacionImagenUrl(viewImagenUrl);
                return holderImagenUrl;
            default:
                return  null;

        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        switch (viewType){
            case NOTIFICACION:
                break;
            case NOTIFICACION_IMAGEN:
                break;
            case NOTIFICACION_URL:
                ViewHolderNotificacionUrl holderNotificacionUrl = (ViewHolderNotificacionUrl) holder;
                holderNotificacionUrl.imagenUsuario.setImageResource(notificaciones.get(position).getImagenUsuario());
                holderNotificacionUrl.titulo.setText(notificaciones.get(position).getTitulo());
                holderNotificacionUrl.descripcion.setText(notificaciones.get(position).getDescripcion());
                final String url = notificaciones.get(position).getUrl();
                holderNotificacionUrl.botonUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent navegador = new Intent(context, WebViewNotificaciones.class);
                        navegador.putExtra("Url", url);
                        context.startActivity(navegador);
                    }
                });
                break;
            case NOTIFICACION_IMAGEN_URL:
                ViewHolderNotificacionImagenUrl holderNotificacionImagenUrl = (ViewHolderNotificacionImagenUrl) holder;
                holderNotificacionImagenUrl.imagenUsuario.setImageResource(notificaciones.get(position).getImagenUsuario());
                holderNotificacionImagenUrl.titulo.setText(notificaciones.get(position).getTitulo());
                holderNotificacionImagenUrl.descripcion.setText(notificaciones.get(position).getDescripcion());
                holderNotificacionImagenUrl.imagen.setImageResource(notificaciones.get(position).getImagen());
                final String urlImagen = notificaciones.get(position).getUrl();
                holderNotificacionImagenUrl.botonUrl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent navegador = new Intent(context, WebViewNotificaciones.class);
                        navegador.putExtra("Url", urlImagen);
                        context.startActivity(navegador);
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return notificaciones.get(position).getTipoNotificacion();
    }

    @Override
    public int getItemCount() {
        return notificaciones.size();
    }
}
