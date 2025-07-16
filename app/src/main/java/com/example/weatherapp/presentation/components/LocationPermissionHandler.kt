package com.example.weatherapp.presentation.components

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionHandler(
    onGranted: () -> Unit,
    onDenied: () -> Unit = {},
) {
    val permissionState = rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)

    // Step 1: اطلب الإذن
    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    // Step 2: راقب التغير في حالة الإذن
    LaunchedEffect(permissionState.status) {
        if (permissionState.status.isGranted) {
            onGranted()
        } else if (!permissionState.status.isGranted && permissionState.status.shouldShowRationale.not()) {
            onDenied()
        }
    }
}
