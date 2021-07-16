package com.kromer.openweather.features.location.domain.usecases

import com.kromer.openweather.core.usecases.UseCase
import com.kromer.openweather.features.location.domain.entities.Location
import com.kromer.openweather.features.location.domain.repositories.LocationRepository

class GetLocationUseCase(private val locationRepository: LocationRepository) :
    UseCase<Location?, Any?> {
    override suspend fun call(params: Any?): Location? = locationRepository.getLocation()
}