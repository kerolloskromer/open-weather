package com.kromer.openweather.features.weather.domain.usecases

import com.kromer.openweather.core.usecases.UseCase
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.repositories.WeatherRepository

class GetCityByIdUseCase(private val weatherRepository: WeatherRepository) :
    UseCase<City?, Long> {
    override suspend fun call(params: Long): City? =
        weatherRepository.getById(params)
}