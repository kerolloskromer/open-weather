package com.kromer.openweather.features.weather.domain.entities

data class WeatherRequest(
    val city: String? = null,
    val lat: Double? = null,
    val lng: Double? = null,
)


