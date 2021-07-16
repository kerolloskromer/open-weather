package com.kromer.openweather.features.location.data.repositories

import com.kromer.openweather.features.location.data.datasources.LocationDataSource
import com.kromer.openweather.features.location.domain.entities.Location
import com.kromer.openweather.features.location.domain.repositories.LocationRepository

class LocationRepositoryImpl(private val locationDataSource: LocationDataSource) :
    LocationRepository {
    override suspend fun getLocation(): Location? = locationDataSource.getLocation()
    override suspend fun cancel() = locationDataSource.cancel()
}