package com.example.llamadas
import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var editTextPhone: EditText
    private lateinit var editTextMessage: EditText
    private lateinit var preferences: SharedPreferences

    companion object {
        const val PREFS_NAME = "AutoResponderPrefs"
        const val KEY_PHONE = "phoneNumber"
        const val KEY_MESSAGE = "autoReplyMessage"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextPhone = findViewById(R.id.editTextPhone)
        editTextMessage = findViewById(R.id.editTextMessage)
        val btnSave: Button = findViewById(R.id.btnSave)
        preferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Cargar valores guardados
        editTextPhone.setText(preferences.getString(KEY_PHONE, ""))
        editTextMessage.setText(preferences.getString(KEY_MESSAGE, "Lo siento, estoy ocupado."))

        // Guardar datos al presionar el botón
        btnSave.setOnClickListener {
            val phone = editTextPhone.text.toString()
            val message = editTextMessage.text.toString()

            if (phone.isNotEmpty() && message.isNotEmpty()) {
                preferences.edit().apply {
                    putString(KEY_PHONE, phone)
                    putString(KEY_MESSAGE, message)
                    apply()
                }
                Toast.makeText(this, "Configuración guardada", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Ingrese un número y mensaje válido", Toast.LENGTH_SHORT).show()
            }
        }

        // Solicitar permisos
        requestPermissions()
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SEND_SMS
        )
        if (permissions.any { ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED }) {
            ActivityCompat.requestPermissions(this, permissions, 1)
        }
    }
}
