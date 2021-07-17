package com.kromer.openweather.features.weather.data.repositories

import androidx.lifecycle.LiveData
import com.kromer.openweather.features.weather.data.datasources.local.WeatherLocalDataSource
import com.kromer.openweather.features.weather.data.datasources.remote.WeatherRemoteDataSource
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.entities.WeatherRequest
import com.kromer.openweather.features.weather.domain.entities.WeatherResponse
import com.kromer.openweather.features.weather.domain.repositories.WeatherRepository

class WeatherRepositoryImpl(
    private val weatherRemoteDataSource: WeatherRemoteDataSource,
    private val weatherLocalDataSource: WeatherLocalDataSource
) :
    WeatherRepository {
    override suspend fun getWeather(request: WeatherRequest): WeatherResponse {
        val response = weatherRemoteDataSource.getWeather(request)
        weatherLocalDataSource.insert(response.city)
        return response
    }

    override fun getAll(): LiveData<List<City>> = weatherLocalDataSource.getAll()
    override suspend fun getByName(name: String): City? = weatherLocalDataSource.getByName(name)
    override suspend fun getById(id: Long): City? = weatherLocalDataSource.getById(id)
    override suspend fun addCity(city: City) = weatherLocalDataSource.insert(city)
    override suspend fun deleteCity(city: City) = weatherLocalDataSource.delete(city)
}