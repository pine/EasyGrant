package com.abhinav.easygrantsample

import android.Manifest
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.abhinav.easygrant.EasyGrantUtil
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    val CAM_PERMISSION = 1
    private lateinit var permissions: ArrayList<String>
    private lateinit var rationalMessages: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissions = ArrayList()
        rationalMessages = ArrayList()
        permissions.add(Manifest.permission.CAMERA)
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissions.add(Manifest.permission.READ_SMS)
        permissions.add(Manifest.permission.BODY_SENSORS)
        rationalMessages.add("Need Camera Permission for my use")
        rationalMessages.add("Need Location group permissions for my use")
        setContentView(R.layout.activity_main)
        btn_cam_permission.setOnClickListener {
            askPermission2()
        }
    }

    private fun askPermission2() {
        EasyGrantUtil.Builder()
                .withActivity(this)
                .permissions(permissions)
                .rationalMessageList(rationalMessages)
                .seek()
    }

    private fun askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Log.e("askPermission", "shouldShowRationale")
                    AlertDialog.Builder(this)
                            .setMessage("You need to give Cam Permission")
                            .setPositiveButton("OK", { dialog, which ->
                                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAM_PERMISSION)
                            })
                            .show()

                } else
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAM_PERMISSION)
            } else {
                Toast.makeText(applicationContext, "Cam Granted", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("RequestResult", "reqCode -> ${requestCode} , ${permissions[0]}, grantResult -> ${grantResults[0]}")
    }
}
