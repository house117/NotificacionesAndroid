package com.miapp.notificacionesandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationManagerCompat;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = "";
        int id = -1;
        if(!intent.getExtras().isEmpty()){
            Log.d("MyBroadCastReceiver", "Hay algo:))");
            data = intent.getExtras().getString("data");
            id = intent.getExtras().getInt("extraNotification");
        }
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.cancel(100);
        String action = intent.getAction();
        Toast.makeText(context, id + " - " + data + " - "+ action, Toast.LENGTH_LONG).show();
        //Intent i = new Intent(context, NotificationActivity.class);
        //context.startActivity(i);
    }
}
