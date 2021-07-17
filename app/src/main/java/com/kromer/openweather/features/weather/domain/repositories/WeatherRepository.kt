package com.kromer.openweather.features.weather.domain.repositories

import androidx.lifecycle.LiveData
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.entities.WeatherRequest

interface WeatherRepository {
    suspend fun getWeather(request: WeatherRequest): City
    fun getAll(): LiveData<List<City>>
    suspend fun getByName(name: String): City?
    suspend fun getById(id: Long): City?
    suspend fun addCity(city: City)
    suspend fun deleteCity(city: City)
}