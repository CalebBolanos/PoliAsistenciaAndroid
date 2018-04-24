package edu.cecyt9.ipn.poliasistenciaandroid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ServiceNotificaciones extends Service {

    public static final String GENERAL = "General";

    public ServiceNotificaciones() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Toast.makeText(this, "Servicio creado", Toast.LENGTH_SHORT).show();

        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Servicio corriendo"+startId, Toast.LENGTH_SHORT).show();
        crearNotificacion();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();
    }

    public void crearNotificacion(){
        int color = getResources().getColor(R.color.azulMas);

        NotificationCompat.Builder constructor = new NotificationCompat.Builder(this, GENERAL);
        Intent noti = new Intent(this.getApplicationContext(), CambiarCorreo.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, noti, 0);

        constructor.setContentIntent(pendingIntent);
        constructor.setSmallIcon(R.drawable.notificacion);
        constructor.setContentTitle("Titulo");
        constructor.setContentText("Texto");
        constructor.setPriority(Notification.PRIORITY_HIGH);
        constructor.setColor(color);
        constructor.setVibrate(new long[1]);
        constructor.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        constructor.setOnlyAlertOnce(true);
        constructor.setChannelId(GENERAL);
        constructor.setAutoCancel(true);
        constructor.setDefaults(Notification.DEFAULT_ALL);
        constructor.setOngoing(false);

        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal = new NotificationChannel(GENERAL, GENERAL, NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(canal);
        }
        manager.notify(0, constructor.build());
    }
}
