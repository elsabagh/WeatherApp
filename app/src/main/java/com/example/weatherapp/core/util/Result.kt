package com.example.weatherapp.core.util

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String?, val throwable: Throwable? = null) : Result<Nothing>()
    object Loading : Result<Nothing>()
}