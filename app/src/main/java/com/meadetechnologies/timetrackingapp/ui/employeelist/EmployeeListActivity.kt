package com.meadetechnologies.timetrackingapp.ui.employeelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Employee
import kotlinx.coroutines.delay

class EmployeeListActivity : AppCompatActivity() {

    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    lateinit var recyclerView : RecyclerView
    lateinit var employees : List<Employee>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_list)

        employees = listOf(
            Employee(Math.random().toInt(), "John Doe", "https://example.com/john.jpg", 28, ""),
            Employee(Math.random().toInt(), "Jane Smith", "https://example.com/jane.jpg", 28, ""),
            Employee(Math.random().toInt(), "Bob Johnson", "https://example.com/bob.jpg", 28, "")
        )

        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)

        timeTrackingDatabase.employeeDao().getAllEmployees().observe(this, Observer {
            Log.d("nathanTest", "Employees: $it")
            employees = it
            val adapter = EmployeeAdapter(employees)
            recyclerView.adapter = adapter
        })

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val adapter = EmployeeAdapter(employees)
        recyclerView.adapter = adapter
    }
}
