package com.meadetechnologies.timetrackingapp.ui.shiftdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase

class ShiftDetailActivity : AppCompatActivity() {

    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    lateinit var shiftInfoTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shift_detail)
        shiftInfoTextView = findViewById(R.id.shiftInfoTextView)
        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)

        val extras = intent.extras
        val shiftId = extras?.getInt("shiftId") ?: 0
        timeTrackingDatabase.shiftDao().getShiftById(shiftId).observe(this, Observer {
            shiftInfoTextView.text = it.toString()
        })
    }
}
