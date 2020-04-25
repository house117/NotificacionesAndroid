package com.miapp.notificacionesandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnBasicNotification;
    public static  String CHANNEL_ID="TEST";
    public static int notificationID = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannel();
        btnBasicNotification = findViewById(R.id.btn_basica);
        btnBasicNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent,  0);
                long[] x = new long[] {Long.parseLong("100"), Long.parseLong("250"), Long.parseLong("100"), Long.parseLong("500")};
                //Creación y configuración de la notificación
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(getString(R.string.notification_title))
                        .setContentText("Contenido de la notificación")
                        .setVibrate(x)
                        .setAutoCancel(true)
                        .setPriority(NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(notificationID, builder.build());

            }
        });
    }

    private void createNotificationChannel(){
        //Create the notificationchannel, but only on API 26+ because
        //the notificationchannel class is new and not in the support library
        //los canales permiten agrupar las notificaciones , a partir del api 26, las notificaciones
        //se agrupan en canales
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Canal";
            String descripcion = "Canal para prueba de Notificaciones";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(descripcion);
            //Register the channnel with the system; you can't change the importance
            //or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
