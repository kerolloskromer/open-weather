package com.kromer.openweather.features.weather.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kromer.openweather.core.network.Resource
import com.kromer.openweather.features.weather.domain.usecases.GetCityByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CityDetailsViewModel @Inject constructor(
    private val getCityByIdUseCase: GetCityByIdUseCase
) : ViewModel() {

    fun getCityById(id: Long) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getCityByIdUseCase.call(id)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}