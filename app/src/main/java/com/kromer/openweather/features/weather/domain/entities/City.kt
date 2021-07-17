package com.kromer.openweather.features.weather.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class City(
    @PrimaryKey
    val id: Long,
    val name: String,
    val country: String,
    val forecastList: List<Forecast>
)

data class Forecast(
    val timestamp: Long,
    val timestamp_text: String,
    val temp: Float,
    val humidity: Float,
    val pressure: Float,
    val temp_min: Float,
    val temp_max: Float,
    val wind_speed: Float,
    val wind_deg: Float,
    val description: String,
)


