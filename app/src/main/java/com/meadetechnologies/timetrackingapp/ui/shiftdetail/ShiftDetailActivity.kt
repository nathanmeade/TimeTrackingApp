package com.meadetechnologies.timetrackingapp.ui.shiftdetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
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

class ShiftDetailActivity : AppCompatActivity() {

    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    lateinit var shiftInfoTextView: TextView
    var shiftId : Int = 0
    var employeeId : Int = 0
    //lateinit var shift: Shift
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shift_detail)
        shiftInfoTextView = findViewById(R.id.shiftInfoTextView)
        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)

        val extras = intent.extras
        shiftId = extras?.getInt("shiftId") ?: 0
        employeeId = extras?.getInt("employeeId") ?: 0
        timeTrackingDatabase.shiftDao().getShiftById(shiftId).observe(this, Observer {
            //shift = it ?: Shift(5, 5, "", "")
            shiftInfoTextView.text = it.toString()
        })
    }

    fun update(view: View) {
        val myScope = CoroutineScope(Dispatchers.IO)
        myScope.launch {
            timeTrackingDatabase.shiftDao().updateShift(Shift(shiftId, employeeId, Math.random().toString(), Math.random().toString()))

        }
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
}
