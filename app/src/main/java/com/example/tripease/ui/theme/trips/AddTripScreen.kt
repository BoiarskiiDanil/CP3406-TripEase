package com.example.tripease.ui.theme.trips

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tripease.viewmodel.TripEaseViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTripScreen(
    viewModel: TripEaseViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var destination by remember { mutableStateOf("") }
    var startDateText by remember { mutableStateOf("") }
    var endDateText by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Add Trip",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Trip name") },
            singleLine = true
        )

        OutlinedTextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text("Destination") },
            singleLine = true
        )

        OutlinedTextField(
            value = startDateText,
            onValueChange = { startDateText = it },
            label = { Text("Start date (yyyy-MM-dd)") },
            singleLine = true
        )

        OutlinedTextField(
            value = endDateText,
            onValueChange = { endDateText = it },
            label = { Text("End date (yyyy-MM-dd)") },
            singleLine = true
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Button(onClick = {
                if (name.isNotBlank() && destination.isNotBlank()) {
                    val startMillis = parseDateToMillis(startDateText)
                    val endMillis = parseDateToMillis(endDateText)

                    viewModel.addTrip(
                        name = name,
                        destination = destination,
                        startDate = startMillis,
                        endDate = endMillis
                    )
                    onSave()
                }
            }) {
                Text("Save")
            }

            OutlinedButton(onClick = onCancel) {
                Text("Cancel")
            }
        }
    }
}

private fun parseDateToMillis(input: String): Long {
    return try {
        val sdf = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault())
        sdf.parse(input)?.time ?: System.currentTimeMillis()
    } catch (e: Exception) {
        System.currentTimeMillis()
    }
}
