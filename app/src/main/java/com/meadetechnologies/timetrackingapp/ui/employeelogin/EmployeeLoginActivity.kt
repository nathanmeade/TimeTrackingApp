package com.meadetechnologies.timetrackingapp.ui.employeelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Employee
import com.meadetechnologies.timetrackingapp.ui.adminlogin.AdminLoginActivity
import com.meadetechnologies.timetrackingapp.ui.timetracking.TimeTrackingActivity
import kotlinx.coroutines.*

class EmployeeLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_login)

        val timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)
//        val employee = Employee(Math.random().toInt(), "Nathan", "Meade", 29, "Android Developer")
        val employee = Employee(Math.random().toInt(), "Nathan", "Meade", 29, "Android Developer")

        timeTrackingDatabase.employeeDao().getAllEmployees().observe(this, Observer {
            Log.d("nathanTest", "Employees: $it")
        })
        val myScope = CoroutineScope(Dispatchers.IO)
        myScope.launch {
            timeTrackingDatabase.employeeDao().addEmployee(employee)
        }
    }

    fun login(view: View) {
        val loginIntent = Intent(this, TimeTrackingActivity::class.java)
        startActivity(loginIntent)
    }

    fun navigateToAdminLogin(view: View) {
        val navigateToAdminLoginIntent = Intent(this, AdminLoginActivity::class.java)
        startActivity(navigateToAdminLoginIntent)
    }
}
