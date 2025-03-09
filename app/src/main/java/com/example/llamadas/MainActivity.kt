package com.example.llamadas

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        setContent {
            AppScreen(this)
        }
    }

    private fun requestPermissions() {
        val permissions = arrayOf(Manifest.permission.READ_PHONE_STATE, Manifest.permission.SEND_SMS)
        permissions.forEach { permission ->
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                registerForActivityResult(ActivityResultContracts.RequestPermission()) {}.launch(permission)
            }
        }
    }
}

@Composable
fun AppScreen(context: Context) {
    var phoneNumber by remember { mutableStateOf("") }
    var autoReplyMessage by remember { mutableStateOf("Lo siento, estoy ocupado.") }
    val sharedPreferences = context.getSharedPreferences("AutoResponderPrefs", Context.MODE_PRIVATE)
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    // Cargar valores guardados
    LaunchedEffect(Unit) {
        phoneNumber = sharedPreferences.getString("phoneNumber", "") ?: ""
        autoReplyMessage = sharedPreferences.getString("autoReplyMessage", "Lo siento, estoy ocupado.") ?: ""
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
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

                    // Mostrar Snackbar con el mensaje de confirmación
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Información guardada")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar")
            }
        }
    }
}
