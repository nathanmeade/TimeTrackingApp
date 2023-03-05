package com.meadetechnologies.timetrackingapp.ui.timetracking

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageFormat
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.media.ImageReader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
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
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class TimeTrackingActivity : AppCompatActivity() {

    lateinit var employeeIdTextView : TextView
    lateinit var clockInClockOutButton : Button
    lateinit var imageView : ImageView
    lateinit var employeeImageView : ImageView
    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    val REQUEST_CODE = 22
    var employeeId: Int = 0
    var shiftId: Int = 0
    lateinit var shiftStartTime : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_tracking)
        employeeIdTextView = findViewById(R.id.userIdTextView)
        clockInClockOutButton = findViewById(R.id.clockInClockOutButton)
        imageView = findViewById(R.id.imageView)
        employeeImageView = findViewById(R.id.employeeImageView)
        timeTrackingDatabase = TimeTrackingDatabase.getDatabase(this)
        val intentExtras = intent.extras
        employeeId = intentExtras?.getInt("id") ?: 0
        employeeIdTextView.text = employeeId.toString()
        timeTrackingDatabase.employeeDao().getEmployeeById(employeeId).observe(this, Observer {
            employeeIdTextView.text = it.name
            Glide.with(this).load(it.image).into(employeeImageView)
        })
        timeTrackingDatabase.shiftDao().getShiftsByEmployeeId(employeeId).observe(this, Observer {
            Log.d("nathanTest", "Shifts: $it")
            it.forEach {
                if (it.startTime.equals(it.endTime)){
                    Log.d("nathanTest", "it.startTime.equals(it.endTime)")
                    Log.d("nathanTest", "it.startTime: ${it.startTime}, it.endTime: ${it.endTime})")
                    shiftId = it.id
                    shiftStartTime = it.startTime
                    clockInClockOutButton.text = "Clock Out"
                }
            }
        })



    }

    fun clockInClockOut(view: View) {
//        val myScope = CoroutineScope(Dispatchers.IO)
//        myScope.launch {
//            timeTrackingDatabase.shiftDao().addShift(Shift(Math.random().toInt(), Math.random().toInt(), "startTime", "endTime"))
//        }
        val shiftApi = MyApiService.shiftApi
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val current = formatter.format(time)
        if (clockInClockOutButton.text.equals("Clock Out")){
            Log.d("nathanTest", "Clock out")
            shiftApi.updateShift(shiftId, Shift(shiftId, employeeId, shiftStartTime, current)).enqueue(object :
                Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d("nathanTest", "shiftApi.createShift onResponse")
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("nathanTest", "shiftApi.createShift onFailure")
                }

            })
        } else {

            shiftApi.createShift(Shift(Math.random().toInt(), employeeId, current, current)).enqueue(object :
                Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.d("nathanTest", "shiftApi.createShift onResponse")
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.d("nathanTest", "shiftApi.createShift onFailure")
                }

            })
        }

    }

    fun takePicture(view: View) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        startActivityForResult(takePictureIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            val bitmap = data?.extras?.get("data") as Bitmap
            imageView.setImageBitmap(bitmap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
