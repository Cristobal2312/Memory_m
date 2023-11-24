package com.example.memory_m.modelo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

import com.example.memory_m.R;
import com.example.memory_m.vista.calendario;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String ACTION_CUSTOM = "YOUR_CUSTOM_ACTION";
    private static final String EXTRA_KEY = "extra";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action != null && action.equals(ACTION_CUSTOM)) {
            handleCustomAction(context, intent);
        } else {
            handleDefaultAction(context);
        }
    }

    private void handleCustomAction(Context context, Intent intent) {
        String string = intent.getStringExtra(EXTRA_KEY);

        if (string != null) {
            if (string.equals("on")) {
                // Acción a realizar cuando la alarma está encendida
                // Puedes agregar tu lógica aquí según la acción "on"
                calendario.activeAlarm = intent.getStringExtra("active");

                // También puedes agregar más lógica específica para la acción "on"
            } else if (string.equals("off")) {
                // Acción a realizar cuando la alarma está apagada
                // Puedes agregar tu lógica aquí según la acción "off"
                calendario.activeAlarm = "";
            }
        }
    }

    private void handleDefaultAction(Context context) {
        // Acción a realizar cuando la alarma se activa
        // Por ejemplo, mostrar una notificación y reproducir el sonido
        MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.bell);

        // Verificar si el MediaPlayer se creó correctamente
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }
}
