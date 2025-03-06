package com.example.llamadas.pantallas

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log

class CallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)
            val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                Log.d("CallReceiver", "Llamada entrante de: $incomingNumber")

                // Obtener número y mensaje guardado en SharedPreferences
                val sharedPreferences = context.getSharedPreferences("AutoResponderPrefs", Context.MODE_PRIVATE)
                val savedNumber = sharedPreferences.getString("phoneNumber", "")
                val savedMessage = sharedPreferences.getString("autoReplyMessage", "Lo siento, estoy ocupado.")

                // Si el número entrante coincide con el guardado, enviar SMS
                if (incomingNumber == savedNumber) {
                    AutoResponder.sendAutoReply(context, incomingNumber, savedMessage ?: "Lo siento, estoy ocupado.")
                }
            }
        }
    }
}
