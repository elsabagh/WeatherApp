package com.example.weatherapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.location.Location
import com.example.weatherapp.domain.model.UserLocation
import com.example.weatherapp.domain.repository.LocationRepository
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.tasks.await
import org.koin.core.annotation.Single
import java.util.Locale
import kotlin.collections.isNullOrEmpty

@Single
@SuppressLint("MissingPermission")
class LocationRepositoryImpl(
    private val context: Context
) : LocationRepository {
    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    override suspend fun getCurrentLocation(): UserLocation {
        val location = getCurrentLocationFromProvider()
        val cityName = getCityNameFromCoordinates(location.latitude, location.longitude)
        return UserLocation(
            latitude = location.latitude,
            longitude = location.longitude,
            cityName = cityName
        )
    }

    private suspend fun getCurrentLocationFromProvider(): Location {
        val currentLocationRequest = CurrentLocationRequest.Builder()
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .setDurationMillis(30000)
            .setMaxUpdateAgeMillis(5000)
            .build()

        return fusedLocationProviderClient.getCurrentLocation(
            currentLocationRequest,
            null
        ).await() ?: throw kotlin.IllegalStateException("No location available")
    }

    private fun getCityNameFromCoordinates(latitude: Double, longitude: Double): String {
        val geocoder = Geocoder(context, Locale.ENGLISH)
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return if (!addresses.isNullOrEmpty()) {
            addresses[0].locality ?: addresses[0].subAdminArea ?: "Unknown city"
        } else "Unknown city"
    }
}


