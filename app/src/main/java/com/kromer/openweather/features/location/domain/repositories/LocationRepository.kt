package com.kromer.openweather.features.location.domain.repositories

import com.kromer.openweather.features.location.domain.entities.Location

interface LocationRepository {
    suspend fun getLocation(): Location?
    suspend fun cancel()
}