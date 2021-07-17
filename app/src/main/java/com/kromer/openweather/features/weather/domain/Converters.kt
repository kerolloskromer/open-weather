package com.kromer.openweather.features.weather.domain

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kromer.openweather.features.weather.domain.entities.Forecast
import java.lang.reflect.Type


class Converters {
    @TypeConverter
    fun fromString(value: String): List<Forecast> {
        val listType: Type = object : TypeToken<List<Forecast>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Forecast>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}