package com.example.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.weatherapp.navigation.NavGraph
import com.example.weatherapp.util.PermissionsUtils

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!PermissionsUtils.hasLocationPermission(this)) {
            PermissionsUtils.requestLocationPermission(this)
        }

        setContent {
            val navController = rememberNavController()
            NavGraph(navController = navController)
        }
    }
}
