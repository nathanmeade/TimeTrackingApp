package com.meadetechnologies.timetrackingapp.ui.employeelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meadetechnologies.timetrackingapp.MyApiService
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmployeeListActivity : AppCompatActivity() {

    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    lateinit var recyclerView : RecyclerView
    lateinit var employees : List<Employee>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)

        val employeeApi = MyApiService.employeeApi

// Get all employees
        employeeApi.getEmployees().enqueue(object : Callback<List<Employee>> {
            override fun onResponse(call: Call<List<Employee>>, response: Response<List<Employee>>) {
                if (response.isSuccessful) {
                    val localEmployees = response.body()
                    Log.d("nathanTest", "response.isSuccessful")
                    Log.d("nathanTest", "response.isSuccessful, employees: $localEmployees")
                    employees = localEmployees ?: listOf(Employee(76, "blah", "blah", "name", ""))
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

        employees = listOf(
            Employee(Math.random().toInt(), "John Doe", "https://example.com/john.jpg", "", ""),
            Employee(Math.random().toInt(), "Jane Smith", "https://example.com/jane.jpg", "28", ""),
            Employee(Math.random().toInt(), "Bob Johnson", "https://example.com/bob.jpg", "28", "")
        )

        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)

//        timeTrackingDatabase.employeeDao().getAllEmployees().observe(this, Observer {
//            Log.d("nathanTest", "Employees: $it")
//            employees = it
//            val adapter = EmployeeAdapter(employees)
//            recyclerView.adapter = adapter
//        })

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val adapter = EmployeeAdapter(employees)
        recyclerView.adapter = adapter
    }
}
