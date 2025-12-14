package com.example.tripease.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tripease.model.Expense
import com.example.tripease.model.Trip

@Database(
    entities = [Trip::class, Expense::class],
    version = 1,
    exportSchema = false
)
abstract class TripEaseDatabase : RoomDatabase() {

    abstract fun tripDao(): TripDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: TripEaseDatabase? = null

        fun getInstance(context: Context): TripEaseDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    TripEaseDatabase::class.java,
                    "tripease_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

