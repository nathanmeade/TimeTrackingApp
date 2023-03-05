package com.meadetechnologies.timetrackingapp.ui.shiftdetail

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.meadetechnologies.timetrackingapp.MyApiService
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Shift
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class ShiftDetailActivity : AppCompatActivity() {

    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    lateinit var shiftInfoTextView: TextView
    lateinit var shiftIdTextView: TextView
    lateinit var shiftEmployeeIdTextView: TextView
    lateinit var shiftStartTimeTextView: TextView
    lateinit var shiftEndTimeTextView: TextView
    lateinit var shiftEmployeeIdEditText: EditText
    lateinit var shiftEndTimeEditText: EditText
    lateinit var shiftStartTimeEditText: EditText
    lateinit var setStartDateButton: Button
    lateinit var setEndDateButton: Button
    lateinit var setStartTimeButton: Button
    lateinit var setEndTimeButton: Button
    var shiftId : Int = 0
    var employeeId : Int = 0
    //lateinit var shift: Shift
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shift_detail)
        shiftInfoTextView = findViewById(R.id.shiftInfoTextView)
        shiftIdTextView = findViewById(R.id.shiftIdTextView)
        shiftEmployeeIdTextView = findViewById(R.id.shiftEmployeeIdTextView)
        shiftStartTimeTextView = findViewById(R.id.shiftStartTimeTextView)
        shiftEndTimeTextView = findViewById(R.id.shiftEndTimeTextView)
        shiftEmployeeIdEditText = findViewById(R.id.shiftEmployeeIdEditText)
        shiftStartTimeEditText = findViewById(R.id.shiftStartTimeEditText)
        shiftEndTimeEditText = findViewById(R.id.shiftEndTimeEditText)
        setStartTimeButton = findViewById(R.id.setStartTimeButton)
        setEndTimeButton = findViewById(R.id.setEndTimeButton)
        setStartDateButton = findViewById(R.id.setStartDateButton)
        setEndDateButton = findViewById(R.id.setEndDateButton)


        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)

        val extras = intent.extras
        shiftId = extras?.getInt("shiftId") ?: 0
        employeeId = extras?.getInt("employeeId") ?: 0
        timeTrackingDatabase.shiftDao().getShiftById(shiftId).observe(this, Observer {
            //shift = it ?: Shift(5, 5, "", "")
            shiftInfoTextView.text = it.toString()
            shiftIdTextView.text = "Shift id: " + it.id.toString()
            shiftEmployeeIdEditText.setText(it.employeeId.toString())
            shiftStartTimeEditText.setText(it.startTime.toString())
            shiftEndTimeEditText.setText(it.endTime.toString())
        })
    }

    fun update(view: View) {
//        val myScope = CoroutineScope(Dispatchers.IO)
//        myScope.launch {
//            timeTrackingDatabase.shiftDao().updateShift(Shift(shiftId, employeeId, Math.random().toString(), Math.random().toString()))
//
//        }
        val shiftApi = MyApiService.shiftApi
//        val employeeId = shiftEmployeeIdEditText.text.toString() as Int
        val employeeId = shiftEmployeeIdEditText.text.toString().toInt()

        val startTimee = shiftStartTimeEditText.text.toString()
        val endTimee = shiftEndTimeEditText.text.toString()
        shiftApi.updateShift(shiftId, Shift(shiftId, employeeId, startTimee, endTimee)).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("nathanTest", "shiftApi.deleteShift onResponse")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("nathanTest", "shiftApi.deleteShift onFailure")
            }

        })
    }

    fun delete(view: View) {
        val shiftApi = MyApiService.shiftApi
        shiftApi.deleteShift(shiftId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Log.d("nathanTest", "shiftApi.deleteShift onResponse")
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.d("nathanTest", "shiftApi.deleteShift onFailure")
            }

        })
//        val myScope = CoroutineScope(Dispatchers.IO)
//        myScope.launch {
//            //timeTrackingDatabase.shiftDao().deleteShift(shift)
//            //timeTrackingDatabase.shiftDao().deleteShiftById(shiftId)
//        }
    }

    fun setStartTime(view: View) {
        val datePickerDialog2 = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

        }, 1, 1, false)
        datePickerDialog2.show()

//        val datePickerDialog = DatePickerDialog(this,
//            { view, year, monthOfYear, dayOfMonth -> txtDate.setText(dayOfMonth.toString() + "-" + (monthOfYear + 1) + "-" + year) },
//            mYear,
//            mMonth,
//            mDay
//        )
//        datePickerDialog.show()
    }
    fun setEndTime(view: View) {
        val datePickerDialog2 = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->

        }, 1, 1, false)
        datePickerDialog2.show()
    }

    fun setStartDate(view: View) {
        val datePickerDialog2 = DatePickerDialog(this)
        datePickerDialog2.show()
    }
    fun setEndDate(view: View) {
        val datePickerDialog2 = DatePickerDialog(this)
        datePickerDialog2.show()
    }
}
