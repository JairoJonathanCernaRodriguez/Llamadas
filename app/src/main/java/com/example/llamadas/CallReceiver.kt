package com.example.llamadas
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("CallReceiver", "Broadcast recibido")

        if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            Log.d("CallReceiver", "Estado del teléfono: $state")

            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                Log.d("CallReceiver", "Llamada entrante detectada (número no disponible en Android 9+).")

                if (context != null) {
                    val preferences = context.getSharedPreferences("AutoResponderPrefs", Context.MODE_PRIVATE)
                    val savedNumber = preferences.getString("phoneNumber", "")
                    val message = preferences.getString("autoReplyMessage", "Lo siento, estoy ocupado.")

                    if (!savedNumber.isNullOrEmpty()) {
                        Log.d("CallReceiver", "Enviando SMS al número preconfigurado: $savedNumber")
                        AutoResponder.sendAutoReply(context, savedNumber, message ?: "")
                    }
                }
            }
        }
    }
}
