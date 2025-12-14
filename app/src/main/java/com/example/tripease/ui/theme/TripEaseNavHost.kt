package com.example.tripease.ui.theme

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tripease.viewmodel.TripEaseViewModel

// ---------- Root composable ----------

@Composable
fun TripEaseRoot() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val viewModel: TripEaseViewModel = viewModel(
        factory = TripEaseViewModel.Factory(context.applicationContext as android.app.Application)
    )

    val bottomItems = listOf(
        Screen.Dashboard,
        Screen.Trips,
        Screen.Expenses,
        Screen.Profile
    )

    Scaffold(
        bottomBar = {
            TripEaseBottomBar(
                navController = navController,
                items = bottomItems
            )
        }
    ) { innerPadding ->
        TripEaseNavHost(
            navController = navController,
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

// ---------- Bottom navigation bar ----------

@Composable
fun TripEaseBottomBar(
    navController: NavHostController,
    items: List<Screen>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    screen.icon?.let {
                        Icon(it, contentDescription = screen.label)
                    }
                },
                label = { Text(screen.label) }
            )
        }
    }
}

// ---------- NavHost for screens ----------

@Composable
fun TripEaseNavHost(
    navController: NavHostController,
    viewModel: TripEaseViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Dashboard.route,
        modifier = modifier
    ) {
        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onViewTripsClick = { navController.navigate(Screen.Trips.route) },
                viewModel = viewModel
            )
        }

        composable(Screen.Trips.route) {
            TripsScreen(
                viewModel = viewModel,
                onAddTripClick = { navController.navigate(Screen.AddTrip.route) },
                onTripClick = { tripId ->
                    viewModel.setActiveTrip(tripId)
                    navController.navigate(Screen.Expenses.route)
                }
            )
        }

        composable(Screen.Expenses.route) {
            ExpensesScreen(
                viewModel = viewModel,
                onAddExpenseClick = { navController.navigate(Screen.AddExpense.route) }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen()
        }

        composable(Screen.AddExpense.route) {
            AddExpenseScreen(
                onSaveClick = { navController.popBackStack() },
                onCancelClick = { navController.popBackStack() },
                viewModel = viewModel
            )
        }

        composable(Screen.AddTrip.route) {
            AddTripScreen(
                viewModel = viewModel,
                onSave = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}

// ---------- Dashboard ----------

@Composable
fun DashboardScreen(
    onViewTripsClick: () -> Unit,
    viewModel: TripEaseViewModel
) {
    // For now we don't use viewModel here, but it's available for future summary info
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "TripEase Dashboard",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Quick overview of your upcoming trips and spending.",
            style = MaterialTheme.typography.bodyMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Next Trip:", fontWeight = FontWeight.SemiBold)
                Text("Add a trip to see it here.")
            }
        }

        Button(onClick = onViewTripsClick) {
            Text("View All Trips")
        }
    }
}

// ---------- Trips list ----------

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
                    Column(modifier = Modifier.padding(16.dp)) {
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

// ---------- Expenses overview ----------

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
            text = "Total spent: \$${"%.2f".format(total)}",
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(expenses) { exp ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp)) {
                        Text("${exp.category} - \$${exp.amount}")
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

// ---------- Add Expense form ----------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenseScreen(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    viewModel: TripEaseViewModel
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
                    onSaveClick()
                }
            }) {
                Text("Save")
            }
            OutlinedButton(onClick = onCancelClick) {
                Text("Cancel")
            }
        }
    }
}

// ---------- Add Trip form ----------

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

// ---------- Profile ----------

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )

        Text("User: Demo Traveller")
        Text("Default currency: USD")
        Text("More settings can go here later.")
    }
}
