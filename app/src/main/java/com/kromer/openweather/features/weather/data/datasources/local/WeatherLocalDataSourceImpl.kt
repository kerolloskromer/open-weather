package com.kromer.openweather.features.weather.data.datasources.local

import androidx.lifecycle.LiveData
import com.kromer.openweather.features.weather.domain.entities.City

class WeatherLocalDataSourceImpl(private val weatherDao: WeatherDao) :
    WeatherLocalDataSource {
    override fun getAll(): LiveData<List<City>> = weatherDao.getAll()
    override suspend fun getByName(name: String): City? = weatherDao.getByName(name)
    override suspend fun getById(id: Long): City? = weatherDao.getById(id)
    override suspend fun insert(city: City) = weatherDao.insert(city)
}