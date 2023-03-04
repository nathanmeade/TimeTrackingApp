package com.meadetechnologies.timetrackingapp

import androidx.lifecycle.LiveData
import androidx.room.*
import com.meadetechnologies.timetrackingapp.data.model.Employee

@Dao
interface EmployeeDao {

    @Insert
    suspend fun addEmployee(employee: Employee)

    @Update
    suspend fun updateEmployee(employee: Employee)

    @Delete
    suspend fun deleteEmployee(employee: Employee)

    @Query("SELECT * FROM employee_table ORDER BY id ASC")
    fun getAllEmployees(): LiveData<List<Employee>>

    @Query("SELECT * FROM employee_table WHERE id = :id")
    fun getEmployeeById(id: Int): LiveData<Employee>
}
