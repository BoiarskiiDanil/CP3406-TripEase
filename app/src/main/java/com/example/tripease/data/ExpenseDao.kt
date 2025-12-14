package com.example.tripease.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tripease.model.Expense
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expenses WHERE tripId = :tripId ORDER BY id DESC")
    fun getExpensesForTrip(tripId: Long): Flow<List<Expense>>

    @Query("SELECT COALESCE(SUM(amount), 0) FROM expenses WHERE tripId = :tripId")
    fun getTotalForTrip(tripId: Long): Flow<Double>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: Expense)
}
