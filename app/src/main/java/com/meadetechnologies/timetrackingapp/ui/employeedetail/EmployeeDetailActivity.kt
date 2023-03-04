package com.meadetechnologies.timetrackingapp.ui.employeedetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Employee
import com.meadetechnologies.timetrackingapp.data.model.Shift
import com.meadetechnologies.timetrackingapp.ui.employeelist.EmployeeAdapter

class EmployeeDetailActivity : AppCompatActivity() {

    lateinit var employeeNameTextView : TextView
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
        val extras = intent.extras
        val employeeId = extras?.getInt("employeeId") ?: 0
        timeTrackingDatabase.employeeDao().getEmployeeById(employeeId).observe(this, Observer {
            employeeNameTextView.text = it.username
        })
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
