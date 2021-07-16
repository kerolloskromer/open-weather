package com.kromer.openweather.features.weather.domain.usecases

import com.kromer.openweather.core.usecases.UseCase
import com.kromer.openweather.features.weather.domain.entities.WeatherRequest
import com.kromer.openweather.features.weather.domain.entities.WeatherResponse
import com.kromer.openweather.features.weather.domain.repositories.WeatherRepository

class GetWeatherUseCase(private val weatherRepository: WeatherRepository) :
    UseCase<WeatherResponse, WeatherRequest> {
    override suspend fun call(params: WeatherRequest): WeatherResponse =
        weatherRepository.getWeather(params)
}