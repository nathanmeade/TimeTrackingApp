package com.meadetechnologies.timetrackingapp

import androidx.lifecycle.LiveData
import androidx.room.*
import com.meadetechnologies.timetrackingapp.data.model.Employee
import com.meadetechnologies.timetrackingapp.data.model.Shift

@Dao
interface ShiftDao {

    @Insert
    suspend fun addShift(shift: Shift)

    @Update
    suspend fun updateShift(shift: Shift)

    @Delete
    suspend fun deleteShift(shift: Shift)

    @Query("SELECT * FROM shift_table ORDER BY id ASC")
    fun getAllShifts(): LiveData<List<Shift>>

    @Query("SELECT * FROM shift_table WHERE id = :id")
    fun getShiftById(id: Int): LiveData<Shift>

    @Query("SELECT * FROM shift_table WHERE employeeId = :employeeId")
    fun getShiftByEmployeeId(employeeId: Int): LiveData<Shift>
}
