package edu.cecyt9.ipn.poliasistenciaandroid;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class ServiceNotificaciones extends Service {

    private static final int CREAR_NOTIFICACION = 1;

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

        NotificationCompat.Builder constructor = new NotificationCompat.Builder(this, "notificaciones");
        Intent noti = new Intent(this.getApplicationContext(), CambiarCorreo.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, noti, 0);

        NotificationCompat.BigTextStyle txtGrande = new NotificationCompat.BigTextStyle();
        txtGrande.bigText("Texto grande");
        txtGrande.setBigContentTitle("Titulo grande");
        txtGrande.setSummaryText("detalles");

        constructor.setContentIntent(pendingIntent);
        constructor.setSmallIcon(R.mipmap.ic_launcher_round);
        constructor.setContentTitle("Titulo");
        constructor.setContentText("Texto");
        constructor.setPriority(Notification.PRIORITY_MAX);
        constructor.setStyle(txtGrande);

        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal = new NotificationChannel("notificaciones", "notificaciones", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(canal);
        }
        manager.notify(0, constructor.build());

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Servicio destruido", Toast.LENGTH_SHORT).show();
    }
}
