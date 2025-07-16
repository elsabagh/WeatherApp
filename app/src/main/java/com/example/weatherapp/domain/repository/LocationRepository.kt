package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.UserLocation

interface LocationRepository {
    suspend fun getCurrentLocation(): UserLocation
}