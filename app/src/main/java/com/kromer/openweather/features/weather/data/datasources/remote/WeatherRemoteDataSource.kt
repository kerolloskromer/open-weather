package com.kromer.openweather.features.weather.data.datasources.remote

import com.kromer.openweather.features.weather.domain.entities.WeatherRequest
import com.kromer.openweather.features.weather.domain.entities.WeatherResponse

interface WeatherRemoteDataSource {
    suspend fun getWeather(request: WeatherRequest): WeatherResponse
}