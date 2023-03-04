package com.meadetechnologies.timetrackingapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.meadetechnologies.timetrackingapp.data.model.Employee
import com.meadetechnologies.timetrackingapp.data.model.Shift

@Database(entities = [Employee::class, Shift::class], version = 4)
abstract class TimeTrackingDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao
    abstract fun shiftDao(): ShiftDao

    companion object {
        @Volatile
        private var INSTANCE: TimeTrackingDatabase? = null

        fun getDatabase(context: Context): TimeTrackingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimeTrackingDatabase::class.java,
                    "time_tracking_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
