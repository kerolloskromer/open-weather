package com.kromer.openweather.features.weather.domain.usecases

import com.kromer.openweather.core.usecases.UseCase
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.repositories.WeatherRepository

class GetCityByNameUseCase(private val weatherRepository: WeatherRepository) :
    UseCase<City?, String> {
    override suspend fun call(params: String): City? =
        weatherRepository.getByName(params)
}