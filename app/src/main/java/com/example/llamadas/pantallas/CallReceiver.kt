package com.example.llamadas.pantallas

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager

class CallReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

            if (state == TelephonyManager.EXTRA_STATE_RINGING) {
                val incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)

                if (context != null && incomingNumber != null) {
                    val preferences = context.getSharedPreferences("AutoResponderPrefs", Context.MODE_PRIVATE)
                    val savedNumber = preferences.getString("phoneNumber", "")
                    val message = preferences.getString("autoReplyMessage", "Lo siento, estoy ocupado.")

                    if (incomingNumber == savedNumber) {
                        AutoResponder.sendAutoReply(context, incomingNumber, message ?: "")
                    }
                }
            }
        }
    }
}

