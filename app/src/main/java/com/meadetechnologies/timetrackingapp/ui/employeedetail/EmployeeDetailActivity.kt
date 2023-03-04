package com.meadetechnologies.timetrackingapp.ui.employeedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Employee
import com.meadetechnologies.timetrackingapp.data.model.Shift
import com.meadetechnologies.timetrackingapp.ui.employeelist.EmployeeAdapter

class EmployeeDetailActivity : AppCompatActivity() {

    lateinit var employeeNameTextView : TextView
    lateinit var employeeImageView: ImageView
    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    lateinit var recyclerView : RecyclerView
    lateinit var shifts : List<Shift>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_detail)

        shifts = listOf(
            Shift(5, 5, "startime", "endtime"),
            Shift(5, 5, "startime", "endtime"),
            Shift(5, 5, "startime", "endtime")
        )
        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)
        employeeNameTextView = findViewById(R.id.employeeNameTextView)
        employeeImageView = findViewById(R.id.employeePictureImageView)
        val extras = intent.extras
        val employeeId = extras?.getInt("employeeId") ?: 0
        timeTrackingDatabase.employeeDao().getEmployeeById(employeeId).observe(this, Observer {
            employeeNameTextView.text = it.name
            Glide.with(this).load(it.image ?: "data:image/png;base64,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"
            ).into(employeeImageView)
        })
//        timeTrackingDatabase.shiftDao().getShiftsByEmployeeId(employeeId).observe(this, Observer {
//            shifts = it
//            val adapter = ShiftAdapter(shifts)
//            recyclerView.adapter = adapter
//        })
//        timeTrackingDatabase.shiftDao().getShiftsByEmployeeId(employeeId).observe(this, Observer {
//            Log.d("nathanTest", "shifts: $it")
//        })
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val adapter = ShiftAdapter(shifts)
        recyclerView.adapter = adapter
        timeTrackingDatabase.shiftDao().getAllShifts().observe(this, Observer {
            Log.d("nathanTest", "shifts: $it")
            shifts = it
            val adapter = ShiftAdapter(shifts)
            recyclerView.adapter = adapter
        })


    }
}
