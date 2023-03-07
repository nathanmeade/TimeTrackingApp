package com.meadetechnologies.timetrackingapp.ui.employeelogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Employee
import com.meadetechnologies.timetrackingapp.ui.adminlogin.AdminLoginActivity
import com.meadetechnologies.timetrackingapp.ui.timetracking.TimeTrackingActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var usernameEditText : EditText
    lateinit var passwordEditText : EditText
    lateinit var loginVerifiedTextView : TextView
    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    var loginVerified = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        loginVerifiedTextView = findViewById(R.id.loginVerifiedTextView)

        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)
//        val employee = Employee(Math.random().toInt(), "Nathan", "Meade", 29, "Android Developer")
        val employee = Employee(Math.random().toInt(), "Nathan", "Meade", "29", "Android Developer")

        timeTrackingDatabase.employeeDao().getAllEmployees().observe(this, Observer {
            Log.d("nathanTest", "Employees: $it")
        })
        timeTrackingDatabase.employeeDao().getEmployeeIdByUsernameAndPassword(usernameEditText.text.toString(), passwordEditText.text.toString()).observe(this, Observer {
            it?.id?.let {
                //loginVerified = true
                loginVerifiedTextView.text = "true"
            }
        })
        val myScope = CoroutineScope(Dispatchers.IO)
        myScope.launch {
            timeTrackingDatabase.employeeDao().addEmployee(employee)
        }
    }

    fun login(view: View) {
        timeTrackingDatabase.employeeDao().getEmployeeIdByUsernameAndPassword(usernameEditText.text.toString(), passwordEditText.text.toString()).observe(this, Observer { it ->
            if (it == null){
                loginVerifiedTextView.text = "false"
            }
            else {
                it?.id?.let {
                    //loginVerified = true
                    loginVerifiedTextView.text = "true"
                    val loginIntent = Intent(this, TimeTrackingActivity::class.java)
                    loginIntent.putExtra("id", it)
                    startActivity(loginIntent)
                }
            }

        })
        if (loginVerified) {
//            val loginIntent = Intent(this, TimeTrackingActivity::class.java)
//            startActivity(loginIntent)
        }
    }

    fun navigateToAdminLogin(view: View) {
        val navigateToAdminLoginIntent = Intent(this, AdminLoginActivity::class.java)
        startActivity(navigateToAdminLoginIntent)
    }
}
