package com.example.tripease.ui.theme.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


@Composable
fun DashboardScreen(
    onViewTripsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "TripEase",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Plan trips and track spending in one place.",
            style = MaterialTheme.typography.bodyMedium
        )

        Card {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Quick summary", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.height(8.dp))
                Text("• Add a trip to start building your itinerary.")
                Text("• Select a trip to track expenses.")
            }
        }

        Button(onClick = onViewTripsClick) {
            Text("View Trips")
        }
    }
}
