package com.example.theyelpapp

import android.annotation.SuppressLint
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.IBinder
import com.google.android.gms.location.FusedLocationProviderClient
import javax.inject.Inject

class LocationService @Inject constructor(
    private val fusedLocation: FusedLocationProviderClient
): Service() {

    override fun onCreate() {
        super.onCreate()
        isRunning = false
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        intent?.let {
            fusedLocation.lastLocation.addOnCompleteListener {task ->
                location = task.result
            }
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    companion object{

        var isRunning = false

        var location: Location? = null

        private fun createLocationIntent(context: Context): Intent =
            Intent(context,LocationService::class.java)

        fun startLocationService(context: Context){
            context.startService(createLocationIntent(context))
        }

        fun stopLocationService(context: Context){
            context.stopService(createLocationIntent(context))
        }

    }

}