package com.kromer.openweather.features.weather.domain.usecases

import androidx.lifecycle.LiveData
import com.kromer.openweather.core.usecases.NoParamUseCase
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.repositories.WeatherRepository

class GetCitiesUseCase(private val weatherRepository: WeatherRepository) :
    NoParamUseCase<LiveData<List<City>>> {
    override fun call(): LiveData<List<City>> = weatherRepository.getAll()
}