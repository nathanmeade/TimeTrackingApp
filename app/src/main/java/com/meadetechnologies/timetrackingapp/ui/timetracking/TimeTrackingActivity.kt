package com.meadetechnologies.timetrackingapp.ui.timetracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.meadetechnologies.timetrackingapp.R

class TimeTrackingActivity : AppCompatActivity() {

    lateinit var employeeIdTextView : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_tracking)
        employeeIdTextView = findViewById(R.id.userIdTextView)
        val intentExtras = intent.extras
        val employeeId = intentExtras?.get("id") ?: 0
        employeeIdTextView.text = employeeId.toString()
    }
}
