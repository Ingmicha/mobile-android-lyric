package com.mura.android.lyrics.data.repository

import android.app.Application
import android.util.Log
import com.google.android.gms.location.LocationServices
import com.mura.android.lyrics.data.model.Location
import com.mura.android.lyrics.utils.extentions.checkLocationPermission
import com.mura.android.lyrics.utils.extentions.isGPSEnabled
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val application: Application,
    private val database: DataBaseRepository
) {

    fun getLocation() {
        /*
         * One time location request
         */
        if (application.isGPSEnabled() && application.checkLocationPermission()) {
            LocationServices.getFusedLocationProviderClient(application)
                .lastLocation
                .addOnSuccessListener { location: android.location.Location? ->
                    if (location != null) {
                        Log.d("WORK-MANAGER", location.altitude.toString())
                        saveLocation(
                            Location(
                                0,
                                location.latitude,
                                location.longitude,
                                System.currentTimeMillis()
                            )
                        )
                    }
                }
        } else {
            Log.d("WORK-MANAGER","ERROR")
        }
    }

    private fun saveLocation(location: Location) =
        GlobalScope.launch { database.saveLocation(location) }

}