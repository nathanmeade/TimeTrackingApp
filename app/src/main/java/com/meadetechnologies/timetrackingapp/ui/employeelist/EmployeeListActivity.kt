package com.meadetechnologies.timetrackingapp.ui.employeelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meadetechnologies.timetrackingapp.MyApiService
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Employee
import com.meadetechnologies.timetrackingapp.data.model.Shift
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeListActivity : AppCompatActivity() {

    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    lateinit var recyclerView : RecyclerView
    lateinit var employees : List<Employee>
    lateinit var shifts : List<Shift>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)

        val employeeApi = MyApiService.employeeApi
        val shiftApi = MyApiService.shiftApi
        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)
// Get all employees
        employeeApi.getEmployees().enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>) {
                if (response.isSuccessful) {
                    val localEmployees = response.body()
                    Log.d("nathanTest", "response.isSuccessful")
                    Log.d("nathanTest", "response.isSuccessful, employees: $localEmployees")
                    employees = localEmployees ?: listOf(Employee(76, "blah", "blah", "name", ""))
//                    employees = listOf(Employee(76, "blah", "blah", "name", ""))
                    val myScope = CoroutineScope(Dispatchers.IO)
                    myScope.launch {
                        Log.d("nathanTest", "myScope.launch {")
                        timeTrackingDatabase.employeeDao().clearEmployees()
                        employees.forEach {
                            timeTrackingDatabase.employeeDao().addEmployee(it)
                        }
                    }


//                    val adapter = EmployeeAdapter(employees)
//                    recyclerView.adapter = adapter
                    // Do something with the employees list
                } else {
                    Log.d("nathanTest", "response but not successful")
                    // Handle error
                }
            }

            override fun onFailure(call: Call<List<Employee>>, t: Throwable) {
                // Handle network failure
                Log.d("nathanTest", "no response: $t")
            }
        })

        shiftApi.getShifts().enqueue(object : Callback<List<Shift>> {
            override fun onResponse(call: Call<List<Shift>>, response: Response<List<Shift>>) {
                if (response.isSuccessful) {
                    val localShifts = response.body()
                    Log.d("nathanTest", "response.isSuccessful")
                    Log.d("nathanTest", "response.isSuccessful, shifts: $localShifts")
                    shifts = localShifts ?: listOf(Shift(76, 5, "blah", "name"))
//                    employees = listOf(Employee(76, "blah", "blah", "name", ""))
                    val myScope = CoroutineScope(Dispatchers.IO)
                    myScope.launch {
                        Log.d("nathanTest", "myScope.launch {")
                        timeTrackingDatabase.shiftDao().clearShifts()
                        shifts.forEach {
                            timeTrackingDatabase.shiftDao().addShift(it)
                        }
                    }


//                    val adapter = EmployeeAdapter(employees)
//                    recyclerView.adapter = adapter
                    // Do something with the employees list
                } else {
                    Log.d("nathanTest", "response but not successful")
                    // Handle error
                }
            }

            override fun onFailure(call: Call<List<Shift>>, t: Throwable) {
                // Handle network failure
                Log.d("nathanTest", "no response: $t")
            }
        })

        employees = listOf(
            Employee(Math.random().toInt(), "John Doe", "https://example.com/john.jpg", "", ""),
            Employee(Math.random().toInt(), "Jane Smith", "https://example.com/jane.jpg", "28", ""),
            Employee(Math.random().toInt(), "Bob Johnson", "https://example.com/bob.jpg", "28", "")
        )

        shifts = listOf(
            Shift(Math.random().toInt(), Math.random().toInt(), "asdf", "aodfiagh")
        )

        timeTrackingDatabase.employeeDao().getAllEmployees().observe(this, Observer {
            Log.d("nathanTest", "Employees: $it")
            employees = it
            val adapter = EmployeeAdapter(employees)
            recyclerView.adapter = adapter
        })

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)


        val adapter = EmployeeAdapter(employees)
        recyclerView.adapter = adapter
    }
}
