package com.kromer.openweather.features.location.domain.usecases

import com.kromer.openweather.core.usecases.UseCase
import com.kromer.openweather.features.location.domain.repositories.LocationRepository

class CancelLocationRequestUseCase(private val locationRepository: LocationRepository) :
    UseCase<Any?, Any?> {
    override suspend fun call(params: Any?) = locationRepository.cancel()
}