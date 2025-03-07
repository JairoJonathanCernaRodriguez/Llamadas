package com.example.llamadas

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.util.Log
import androidx.core.content.ContextCompat

object AutoResponder {
    fun sendAutoReply(context: Context, phoneNumber: String?, message: String) {
        if (phoneNumber.isNullOrEmpty()) return

        // Verificar si el permiso de enviar SMS está concedido
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            Log.e("AutoResponder", "Permiso de SMS no concedido")
            return
        }

        try {
            val smsManager = SmsManager.getDefault()
            Log.d("AutoResponder", "Enviando SMS a $phoneNumber con el mensaje: $message")
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Log.d("AutoResponder", "SMS enviado a $phoneNumber")
        } catch (e: Exception) {
            Log.e("AutoResponder", "Error al enviar SMS: ${e.message}")
        }
    }
}
