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
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnBasicNotification;
    Button btnActionButtonNotification;
    public static String CHANNEL_ID = "TEST";
    public static int notificationID = 100;
    public static String ACTION_SNOOZE = "Send_Message";
    public static String EXTRA_NOTIFICATION_ID = "extraNotification";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //creamos el canal
        createNotificationChannel();
        //Nuestros botones
        btnBasicNotification = findViewById(R.id.btn_basica);
        btnActionButtonNotification = findViewById(R.id.notification_action_button);

        btnBasicNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);
                long[] x = new long[]{Long.parseLong("100"), Long.parseLong("250"), Long.parseLong("100"), Long.parseLong("500")};
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
        //Notificacion con boton de accion
        btnActionButtonNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long[] x = new long[]{Long.parseLong("100"), Long.parseLong("250"), Long.parseLong("100"), Long.parseLong("500")};

                //Indicación de la acción del botón en la notificación
                Intent snoozeIntent = new Intent(getApplicationContext(), MyBroadcastReceiver.class);
                snoozeIntent.setAction(ACTION_SNOOZE);
                snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
                snoozeIntent.putExtra("data", "Hola mundo");

                PendingIntent snoozePendingIntent =
                        PendingIntent.getBroadcast(getApplicationContext(), 0, snoozeIntent, 0);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle("Título de la notificación")
                        .setContentText("Contenido de la notificación")
                        .setVibrate(x)
                        .setAutoCancel(true)
                        .setPriority(NotificationManager.IMPORTANCE_DEFAULT)
                        .addAction(R.drawable.ic_launcher_background, "Acción Botón", snoozePendingIntent);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());

                notificationManager.notify(notificationID, builder.build());
            }
        });
    }

    private void createNotificationChannel() {
        //Create the notificationchannel, but only on API 26+ because
        //the notificationchannel class is new and not in the support library
        //los canales permiten agrupar las notificaciones , a partir del api 26, las notificaciones
        //se agrupan en canales
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
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
