package com.example.weatherapp.presentation.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherapp.data.mapper.toWeatherCondition
import com.example.weatherapp.domain.model.DailyWeather
import com.example.weatherapp.presentation.mapper.toDrawableRes
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyForecastSection(dailyList: List<DailyWeather>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Next Days",
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            dailyList.forEach { item ->
                DailyForecastItem(item)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DailyForecastItem(
    day: DailyWeather,
    modifier: Modifier = Modifier,
) {
    val condition = day.weatherCode.toWeatherCondition()
    val iconRes = painterResource(id = condition.toDrawableRes(isDay = true))

    Surface(
        color = Color(0xFFE3F2FD),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
    ) {
        Box(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = formatToWeekday(day.date),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Start,
                letterSpacing = 0.25.sp,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .align(Alignment.CenterStart)
            )
            Icon(
                painter = iconRes, contentDescription = null, tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.TopCenter).size(40.dp)
            )
            Text(
                text = "${day.maxTemp.toInt()}° / ${day.minTemp.toInt()}°",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatToWeekday(isoDate: String): String {
    return try {
        val date = LocalDate.parse(isoDate, DateTimeFormatter.ISO_DATE)
        date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
    } catch (e: Exception) {
        isoDate
    }
}