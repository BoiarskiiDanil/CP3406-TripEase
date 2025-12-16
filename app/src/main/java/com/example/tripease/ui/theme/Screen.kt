package com.example.tripease.ui.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Payments
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource


sealed class Screen(
    val route: String,
    val label: String,
    val icon: ImageVector?   // null = no icon in bottom bar
) {
    // Bottom bar tabs
    data object Dashboard : Screen("dashboard", "Home", Icons.Default.Home)
    data object Trips : Screen("trips", "Trips", Icons.Default.List)
    data object Expenses : Screen("expenses", "Expenses", Icons.Default.Payments)
    data object Profile : Screen("profile", "Profile", Icons.Default.AccountCircle)

    // Hidden routes
    data object AddTrip : Screen("add_trip", "Add Trip", null)
    data object AddExpense : Screen("add_expense", "Add Expense", null)
}
