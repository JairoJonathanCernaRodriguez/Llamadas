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

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS)
            != PackageManager.PERMISSION_GRANTED) {
            return // No se necesita log aquí, ya que la app no puede hacer nada sin permiso
        }

        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        } catch (e: Exception) {
            Log.e("AutoResponder", "Error al enviar SMS: ${e.message}")
        }
    }
}
