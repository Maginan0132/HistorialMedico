package com.unie.historialMedico.ui.records

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unie.historialMedico.data.MedicalRecord
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MedicalRecordsScreen(
    modifier: Modifier = Modifier,
    viewModel: MedicalRecordsViewModel = viewModel(),
    animatedVisibilityScope: AnimatedVisibilityScope,
    onRecordClick: (MedicalRecord) -> Unit
) {
    val records by viewModel.records.collectAsState()
    var newRecordDescription by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Historial Médico", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            TextField(
                value = newRecordDescription,
                onValueChange = { newRecordDescription = it },
                label = { Text("Nuevo registro") },
                modifier = Modifier.weight(1f)
            )
            Button(modifier = Modifier.padding(start = 8.dp), onClick = {
                if (newRecordDescription.isNotBlank()) {
                    viewModel.addRecord(newRecordDescription)
                    newRecordDescription = ""
                }
            }) {
                Text("Añadir")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(records, key = { it.id }) { record ->
                MedicalRecordItem(
                    record = record,
                    animatedVisibilityScope = animatedVisibilityScope,
                    onClick = { onRecordClick(record) },
                    onDelete = { viewModel.deleteRecord(record) }
                )
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.MedicalRecordItem(
    record: MedicalRecord,
    animatedVisibilityScope: AnimatedVisibilityScope,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    val sdf = remember { SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .sharedElement(
                state = rememberSharedContentState(key = "record-card-${record.id}"),
                animatedVisibilityScope = animatedVisibilityScope
            )
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(record.description, style = MaterialTheme.typography.bodyLarge)
                Text(sdf.format(Date(record.date)), style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar registro")
            }
        }
    }
}