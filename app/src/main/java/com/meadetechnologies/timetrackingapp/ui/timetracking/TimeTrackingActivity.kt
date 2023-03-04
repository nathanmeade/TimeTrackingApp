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
import com.meadetechnologies.timetrackingapp.R
import com.meadetechnologies.timetrackingapp.TimeTrackingDatabase
import com.meadetechnologies.timetrackingapp.data.model.Shift
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimeTrackingActivity : AppCompatActivity() {

    lateinit var employeeIdTextView : TextView
    lateinit var clockInClockOutButton : Button
    lateinit var imageView : ImageView
    lateinit var timeTrackingDatabase: TimeTrackingDatabase
    val REQUEST_CODE = 22
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_tracking)
        employeeIdTextView = findViewById(R.id.userIdTextView)
        clockInClockOutButton = findViewById(R.id.clockInClockOutButton)
        imageView = findViewById(R.id.imageView)
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
