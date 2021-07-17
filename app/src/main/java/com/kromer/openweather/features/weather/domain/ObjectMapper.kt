package com.kromer.openweather.features.weather.domain

import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.entities.Forecast
import com.kromer.openweather.features.weather.domain.entities.ListObj
import com.kromer.openweather.features.weather.domain.entities.WeatherResponse

object ObjectMapper {

    fun mapWeatherResponseToCity(weatherResponse: WeatherResponse): City {
        val listOfForecasts = weatherResponse.list.map {
            getForecastObj(it)
        }
        val chunkedList = listOfForecasts.chunked(8)
        val result = chunkedList.map { list: List<Forecast> -> list.first() }
        return City(
            weatherResponse.city.id,
            weatherResponse.city.name,
            weatherResponse.city.country,
            result
        )
    }

    private fun getForecastObj(listObj: ListObj): Forecast {
        return Forecast(
            listObj.dt,
            listObj.dt_txt,
            listObj.main.temp,
            listObj.main.humidity,
            listObj.main.pressure,
            listObj.main.temp_min,
            listObj.main.temp_max,
            listObj.wind.speed,
            listObj.wind.deg,
            listObj.weather.first().description
        )
    }
}