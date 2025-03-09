package com.example.llamadas

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppScreen(this)
        }
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
            requestPermissions(permissions, 1)
        }
    }
}

@Composable
fun AppScreen(context: Context) {
    var phoneNumber by remember { mutableStateOf("") }
    var autoReplyMessage by remember { mutableStateOf("Lo siento, estoy ocupado.") }
    var showMessage by remember { mutableStateOf(false) }  // Estado para mostrar el mensaje "Información guardada"
    val sharedPreferences = context.getSharedPreferences("AutoResponderPrefs", Context.MODE_PRIVATE)

    LaunchedEffect(Unit) {
        phoneNumber = sharedPreferences.getString("phoneNumber", "") ?: ""
        autoReplyMessage = sharedPreferences.getString("autoReplyMessage", "Lo siento, estoy ocupado.") ?: ""
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Número de teléfono") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = autoReplyMessage,
            onValueChange = { autoReplyMessage = it },
            label = { Text("Mensaje de respuesta automática") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                sharedPreferences.edit()
                    .putString("phoneNumber", phoneNumber)
                    .putString("autoReplyMessage", autoReplyMessage)
                    .apply()
                showMessage = true // Activar mensaje de confirmación
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar")
        }

        if (showMessage) {
            Text(
                text = "Información guardada",
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
