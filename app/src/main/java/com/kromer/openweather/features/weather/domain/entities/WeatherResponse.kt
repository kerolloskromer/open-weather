package com.kromer.openweather.features.weather.domain.entities

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("cod")
    val cod: Float,
    @SerializedName("list")
    val list: ArrayList<ListObj>,
    @SerializedName("city")
    val city: CityObj
)

data class Weather(
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)

data class Clouds(
    @SerializedName("all")
    val all: Float
)

data class Wind(
    @SerializedName("speed")
    val speed: Float,
    @SerializedName("deg")
    val deg: Float
)

data class Main(
    @SerializedName("temp")
    val temp: Float,
    @SerializedName("humidity")
    val humidity: Float,
    @SerializedName("pressure")
    val pressure: Float,
    @SerializedName("temp_min")
    val temp_min: Float,
    @SerializedName("temp_max")
    val temp_max: Float
)

data class ListObj(
    @SerializedName("dt")
    val dt: Long,
    @SerializedName("main")
    val main: Main,
    @SerializedName("weather")
    val weather: ArrayList<Weather> = ArrayList(),
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("wind")
    val wind: Wind,
    @SerializedName("dt_txt")
    val dt_txt: String,
)

data class CityObj(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("country")
    val country: String
)