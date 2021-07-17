package com.kromer.openweather.features.weather.data.datasources.local

import androidx.lifecycle.LiveData
import com.kromer.openweather.features.weather.domain.entities.City

interface WeatherLocalDataSource {
    fun getAll(): LiveData<List<City>>
    suspend fun getByName(name: String): City?
    suspend fun getById(id: Long): City?
    suspend fun insert(city: City)
    suspend fun delete(city: City)
}