package com.meadetechnologies.timetrackingapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.meadetechnologies.timetrackingapp.data.model.Employee

@Database(entities = [Employee::class], version = 2)
abstract class TimeTrackingDatabase : RoomDatabase() {

    abstract fun employeeDao(): EmployeeDao

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
