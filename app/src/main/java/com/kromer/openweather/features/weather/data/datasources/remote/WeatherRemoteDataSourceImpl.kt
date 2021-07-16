package com.kromer.openweather.features.weather.data.datasources.remote

import com.kromer.openweather.features.weather.domain.entities.WeatherRequest
import com.kromer.openweather.features.weather.domain.entities.WeatherResponse

class WeatherRemoteDataSourceImpl(private val weatherApiInterface: WeatherApiInterface) :
    WeatherRemoteDataSource {
    override suspend fun getWeather(request: WeatherRequest): WeatherResponse =
        weatherApiInterface.getWeather(request.city, request.lat, request.lng)
}