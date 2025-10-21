package com.unie.historialMedico.ui.records

import androidx.lifecycle.ViewModel
import com.unie.historialMedico.data.MedicalRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MedicalRecordsViewModel : ViewModel() {

    private val _records = MutableStateFlow<List<MedicalRecord>>(emptyList())
    val records = _records.asStateFlow()

    init {
        // Add some fake data
        _records.value = listOf(
            MedicalRecord(id = 1, date = System.currentTimeMillis(), description = "Visita al cardiólogo"),
            MedicalRecord(id = 2, date = System.currentTimeMillis() - 86400000, description = "Vacuna de la gripe")
        )
    }

    fun addRecord(description: String) {
        val newRecord = MedicalRecord(
            id = (_records.value.maxOfOrNull { it.id } ?: 0) + 1,
            date = System.currentTimeMillis(),
            description = description
        )
        _records.update { it + newRecord }
    }

    fun deleteRecord(record: MedicalRecord) {
        _records.update { it - record }
    }
}
