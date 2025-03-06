package com.example.llamadas


import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var editTextPhone: EditText
    private lateinit var editTextMessage: EditText
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextPhone = findViewById(R.id.editTextPhone)
        editTextMessage = findViewById(R.id.editTextMessage)
        val btnSave: Button = findViewById(R.id.btnSave)

        preferences = getSharedPreferences("AutoResponderPrefs", Context.MODE_PRIVATE)

        // Cargar valores guardados
        editTextPhone.setText(preferences.getString("phoneNumber", ""))
        editTextMessage.setText(preferences.getString("autoReplyMessage", "Lo siento, estoy ocupado."))

        // Guardar datos cuando el usuario presiona el botón
        btnSave.setOnClickListener {
            val editor = preferences.edit()
            editor.putString("phoneNumber", editTextPhone.text.toString())
            editor.putString("autoReplyMessage", editTextMessage.text.toString())
            editor.apply()
        }

        // Solicitar permisos al iniciar la aplicación
        requestPermissions()
    }

    private fun requestPermissions() {
        val permissions = arrayOf(
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.SEND_SMS
        )
        if (permissions.any {
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            }) {
            ActivityCompat.requestPermissions(this, permissions, 1)
        }
    }
}
