package com.kromer.openweather.features.location.data.datasources

import com.kromer.openweather.features.location.domain.entities.Location

interface LocationDataSource {
    suspend fun getLocation(): Location?
    suspend fun cancel()
}