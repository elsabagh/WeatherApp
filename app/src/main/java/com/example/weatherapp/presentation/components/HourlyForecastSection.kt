package com.example.weatherapp.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.mapper.toWeatherCondition
import com.example.weatherapp.domain.model.HourlyWeather
import com.example.weatherapp.presentation.mapper.toDrawableRes
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyForecastSection(
    hourlyList: List<HourlyWeather>,
) {
    Column(modifier = Modifier.padding(start = 16.dp, top = 16.dp)) {
        Text(
            text = "Today",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            itemsIndexed(hourlyList.take(24)) { index, item ->
                HourlyWeatherCard(item)
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HourlyWeatherCard(hour: HourlyWeather) {
    val condition = hour.weatherCode.toWeatherCondition()
    val iconRes = painterResource(id = condition.toDrawableRes(hour.isDay))

    Surface(
        color = MaterialTheme.colorScheme.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.size(80.dp, 120.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = formatHour(hour.time),
                fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant

            )
            Icon(
                painter = iconRes,
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = "${hour.temperature.toInt()}°",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface

            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatHour(isoTime: String): String {
    return try {
        val hour = LocalTime.parse(isoTime.takeLast(5))
        hour.format(DateTimeFormatter.ofPattern("HH:mm"))
    } catch (e: Exception) {
        "-"
    }
}
