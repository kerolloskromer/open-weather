package com.kromer.openweather.features.weather.data.datasources.remote

import com.kromer.openweather.features.weather.domain.entities.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiInterface {
    @GET("data/2.5/forecast")
    suspend fun getWeather(
        @Query("q") city: String?,
        @Query("lat") lat: Double?,
        @Query("lon") lng: Double?
    ): WeatherResponse
}