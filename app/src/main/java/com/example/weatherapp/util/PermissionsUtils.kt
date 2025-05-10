package com.example.weatherapp.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.weatherapp.data.local.CacheManager

object PermissionsUtils {
    const val LOCATION_PERMISSION_REQUEST_CODE = 100

    fun hasLocationPermission(activity: Activity): Boolean {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        return ContextCompat.checkSelfPermission(
            activity,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestLocationPermission(activity: Activity) {
        val permission = Manifest.permission.ACCESS_FINE_LOCATION
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission),
            LOCATION_PERMISSION_REQUEST_CODE
        )
    }

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun handleLocationPermissionAndFetch(
        context: Context,
        onPermissionGranted: (latitude: Double, longitude: Double) -> Unit,
    ) {
        val activity = context as? Activity
        if (activity != null && hasLocationPermission(activity)) {
            val location = LocationProvider.getCurrentLocation(context)
            if (location != null) {
                CacheManager.saveCoordinates(context, location.first, location.second)
                onPermissionGranted(location.first, location.second)
            } else {
                val locationManager =
                    context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, 0L, 0f
                ) { newLocation ->
                    CacheManager.saveCoordinates(
                        context,
                        newLocation.latitude,
                        newLocation.longitude
                    )
                    onPermissionGranted(newLocation.latitude, newLocation.longitude)
                }
            }
        } else {
            activity?.let { requestLocationPermission(it) }
        }
    }
}