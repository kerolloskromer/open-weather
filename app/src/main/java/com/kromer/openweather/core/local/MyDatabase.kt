package com.kromer.openweather.core.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kromer.openweather.features.weather.data.datasources.local.WeatherDao
import com.kromer.openweather.features.weather.domain.Converters
import com.kromer.openweather.features.weather.domain.entities.City

@Database(entities = [City::class], version = MyDatabase.DATABASE_VERSION)
@TypeConverters(Converters::class)
abstract class MyDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "WeatherDatabase"
    }

    abstract fun getWeatherDao(): WeatherDao
}