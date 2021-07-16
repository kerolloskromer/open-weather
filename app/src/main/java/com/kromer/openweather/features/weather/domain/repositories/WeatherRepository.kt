package com.kromer.openweather.features.weather.domain.repositories

import androidx.lifecycle.LiveData
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.entities.WeatherRequest
import com.kromer.openweather.features.weather.domain.entities.WeatherResponse

interface WeatherRepository {
    suspend fun getWeather(request: WeatherRequest): WeatherResponse
    fun getAll(): LiveData<List<City>>
    suspend fun getByName(name: String): City?
    suspend fun getById(id: Long): City?
}