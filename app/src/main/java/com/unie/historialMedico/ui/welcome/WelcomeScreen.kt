package com.unie.historialMedico.ui.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unie.historialMedico.ui.theme.MyApplicationTheme

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToRecords: () -> Unit,
    welcomeViewModel: WelcomeViewModel = viewModel(factory = WelcomeViewModelFactory(LocalContext.current.applicationContext as android.app.Application))
) {
    var name by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val (loadedName, loadedPin) = welcomeViewModel.loadUser()
        if (loadedName != null && loadedPin != null) {
            name = loadedName
            pin = loadedPin
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mi Historial Médico",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = pin,
            onValueChange = { if (it.length <= 4) pin = it },
            label = { Text("PIN de 4 dígitos") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            visualTransformation = PasswordVisualTransformation(),
            textStyle = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                welcomeViewModel.saveUser(name, pin)
                onNavigateToRecords()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Guardar y Entrar",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    MyApplicationTheme {
        // In preview, we don't have a real ViewModel, so we pass a dummy lambda.
        WelcomeScreen(onNavigateToRecords = {})
    }
}
