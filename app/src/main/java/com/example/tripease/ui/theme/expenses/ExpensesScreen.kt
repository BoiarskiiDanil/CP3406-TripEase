package com.example.tripease.ui.theme.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tripease.viewmodel.TripEaseViewModel

@Composable
fun ExpensesScreen(
    viewModel: TripEaseViewModel,
    onAddExpenseClick: () -> Unit
) {
    val expenses by viewModel.expensesForActiveTrip.collectAsState()
    val total by viewModel.totalForActiveTrip.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Expenses",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = "Total spent: $${"%.2f".format(total)}",
            fontWeight = FontWeight.SemiBold
        )

        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(expenses) { exp ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${exp.category} - $${exp.amount}")
                        if (exp.note.isNotBlank()) {
                            Text(exp.note, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = onAddExpenseClick,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Add Expense")
        }
    }
}
