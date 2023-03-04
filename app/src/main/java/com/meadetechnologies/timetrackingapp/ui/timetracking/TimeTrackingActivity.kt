package com.meadetechnologies.timetrackingapp.ui.timetracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Shift
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimeTrackingActivity : AppCompatActivity() {

    lateinit var employeeIdTextView : TextView
    lateinit var clockInClockOutButton : Button
    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_tracking)
        employeeIdTextView = findViewById(R.id.userIdTextView)
        clockInClockOutButton = findViewById(R.id.clockInClockOutButton)
        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)
        val intentExtras = intent.extras
        val employeeId = intentExtras?.get("id") ?: 0
        employeeIdTextView.text = employeeId.toString()
        timeTrackingDatabase.shiftDao().getAllShifts().observe(this, Observer {
            Log.d("nathanTest", "Shifts: $it")
        })
    }

    fun clockInClockOut(view: View) {
        val myScope = CoroutineScope(Dispatchers.IO)
        myScope.launch {
            timeTrackingDatabase.shiftDao().addShift(Shift(Math.random().toInt(), Math.random().toInt(), "startTime", "endTime"))
        }
    }
}
