package com.example.tripease.data

import com.example.tripease.model.Expense
import com.example.tripease.model.Trip
import kotlinx.coroutines.flow.Flow

class TripEaseRepository(
    private val tripDao: TripDao,
    private val expenseDao: ExpenseDao
) {

    fun getAllTrips(): Flow<List<Trip>> = tripDao.getAllTrips()

    fun getExpensesForTrip(tripId: Long): Flow<List<Expense>> =
        expenseDao.getExpensesForTrip(tripId)

    fun getTotalForTrip(tripId: Long): Flow<Double> =
        expenseDao.getTotalForTrip(tripId)

    suspend fun addTrip(trip: Trip) = tripDao.insertTrip(trip)

    suspend fun addExpense(expense: Expense) = expenseDao.insertExpense(expense)

    suspend fun deleteTrip(trip: Trip) = tripDao.deleteTrip(trip)
}

