package com.example.llamadas.pantallas

import android.content.Context
import android.telephony.SmsManager
import android.util.Log

object AutoResponder {

    fun sendAutoReply(context: Context, phoneNumber: String?, message: String) {
        if (phoneNumber.isNullOrEmpty()) return

        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("AutoResponder", "SMS enviado a $phoneNumber")
        } catch (e: Exception) {
            Log.e("AutoResponder", "Error al enviar SMS: ${e.message}")
        }
    }
}
