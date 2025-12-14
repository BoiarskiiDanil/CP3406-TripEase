package com.example.tripease.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tripease.data.TripEaseDatabase
import com.example.tripease.data.TripEaseRepository
import com.example.tripease.model.Expense
import com.example.tripease.model.Trip
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TripEaseViewModel(application: Application) : AndroidViewModel(application) {

    private val db = TripEaseDatabase.getInstance(application)
    private val repo = TripEaseRepository(db.tripDao(), db.expenseDao())

    private val _activeTripId = MutableStateFlow<Long?>(null)
    val activeTripId: StateFlow<Long?> = _activeTripId.asStateFlow()

    val trips: StateFlow<List<Trip>> =
        repo.getAllTrips()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val expensesForActiveTrip: StateFlow<List<Expense>> =
        activeTripId.flatMapLatest { id ->
            if (id == null) flowOf(emptyList())
            else repo.getExpensesForTrip(id)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    val totalForActiveTrip: StateFlow<Double> =
        activeTripId.flatMapLatest { id ->
            if (id == null) flowOf(0.0)
            else repo.getTotalForTrip(id)
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), 0.0)

    fun setActiveTrip(id: Long) {
        _activeTripId.value = id
    }

    fun addTrip(name: String, destination: String, startDate: Long, endDate: Long) {
        viewModelScope.launch {
            repo.addTrip(
                Trip(
                    name = name,
                    destination = destination,
                    startDate = startDate,
                    endDate = endDate
                )
            )
        }
    }

    fun addExpense(amount: Double, category: String, note: String) {
        val tripId = _activeTripId.value ?: return
        viewModelScope.launch {
            repo.addExpense(
                Expense(
                    tripId = tripId,
                    amount = amount,
                    category = category,
                    note = note
                )
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TripEaseViewModel::class.java)) {
                return TripEaseViewModel(app) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
