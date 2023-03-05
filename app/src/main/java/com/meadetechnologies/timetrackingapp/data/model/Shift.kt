package com.meadetechnologies.timetrackingapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shift_table")
data class Shift(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val employeeId: Int,
    val startTime: String,
    val endTime: String,
    val clockInPhotoLocation: String? = null,
    val clockOutPhotoLocation: String? = null,
)
