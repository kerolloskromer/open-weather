package com.kromer.openweather.features.weather.domain.usecases

import com.kromer.openweather.core.usecases.UseCase
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.entities.WeatherRequest
import com.kromer.openweather.features.weather.domain.repositories.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) :
    UseCase<City, WeatherRequest> {
    override suspend fun call(params: WeatherRequest): City =
        weatherRepository.getWeather(params)
}