package com.example.weatherapp.util

import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.annotation.RequiresPermission

object LocationProvider {
    @RequiresPermission(allOf = [android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION])
    fun getCurrentLocation(context: Context): Pair<Double, Double>? {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val provider = LocationManager.NETWORK_PROVIDER
        val location: Location? = locationManager.getLastKnownLocation(provider)
        return location?.let { Pair(it.latitude, it.longitude) }
    }
}