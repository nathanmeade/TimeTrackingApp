package com.meadetechnologies.timetrackingapp.ui.employeelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.ui.adminlogin.AdminLoginActivity
import com.meadetechnologies.timetrackingapp.ui.timetracking.TimeTrackingActivity

class EmployeeLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_login)
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
