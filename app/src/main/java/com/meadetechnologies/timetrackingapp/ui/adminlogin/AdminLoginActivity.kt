package com.meadetechnologies.timetrackingapp.ui.adminlogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.ui.employeelist.EmployeeListActivity
import com.meadetechnologies.timetrackingapp.ui.employeelogin.EmployeeLoginActivity
import com.meadetechnologies.timetrackingapp.ui.timetracking.TimeTrackingActivity

class AdminLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_login)
    }

    fun login(view: View) {
        val loginIntent = Intent(this, EmployeeListActivity::class.java)
        startActivity(loginIntent)
    }

    fun navigateToEmployeeLogin(view: View) {
        val navigateToEmployeeLoginIntent = Intent(this, EmployeeLoginActivity::class.java)
        startActivity(navigateToEmployeeLoginIntent)
    }

}
