package com.example.tripease.ui.theme.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tripease.viewmodel.TripEaseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    viewModel: TripEaseViewModel,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = "Add Expense",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            singleLine = true
        )

        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Category (e.g. Food, Transport)") },
            singleLine = true
        )

        OutlinedTextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Note (optional)") }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

            Button(onClick = {
                val amt = amount.toDoubleOrNull()
                if (amt != null && category.isNotBlank()) {
                    viewModel.addExpense(
                        amount = amt,
                        category = category,
                        note = note
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
