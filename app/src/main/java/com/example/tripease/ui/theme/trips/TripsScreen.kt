package com.example.tripease.ui.theme.trips

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tripease.viewmodel.TripEaseViewModel

@Composable
fun TripsScreen(
    viewModel: TripEaseViewModel,
    onAddTripClick: () -> Unit,
    onTripClick: (Long) -> Unit
) {
    val trips by viewModel.trips.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(trips) { trip ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onTripClick(trip.id) }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(trip.name, fontWeight = FontWeight.SemiBold)
                        Text(trip.destination)
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = onAddTripClick,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Text("+")
        }
    }
}
