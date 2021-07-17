package com.kromer.openweather.features.weather.domain.usecases

import com.kromer.openweather.core.usecases.UseCase
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.repositories.WeatherRepository

class DeleteCityUseCase(private val weatherRepository: WeatherRepository) :
    UseCase<Unit, City> {
    override suspend fun call(params: City): Unit =
        weatherRepository.deleteCity(params)
}