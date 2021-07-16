package com.kromer.openweather.features.weather.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.kromer.openweather.core.network.Resource
import com.kromer.openweather.features.location.domain.usecases.CancelLocationRequestUseCase
import com.kromer.openweather.features.location.domain.usecases.GetLocationUseCase
import com.kromer.openweather.features.weather.domain.entities.WeatherRequest
import com.kromer.openweather.features.weather.domain.usecases.GetCitiesUseCase
import com.kromer.openweather.features.weather.domain.usecases.GetCityByNameUseCase
import com.kromer.openweather.features.weather.domain.usecases.GetWeatherUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getCitiesUseCase: GetCitiesUseCase,
    private val getCityByNameUseCase: GetCityByNameUseCase,
    private val getLocationUseCase: GetLocationUseCase,
    private val cancelLocationRequestUseCase: CancelLocationRequestUseCase
) : ViewModel() {

    private val _permissionGranted = MutableLiveData<Boolean>()
    val permissionGranted: LiveData<Boolean>
        get() = _permissionGranted

    fun getWeather(city: String?, lat: Double?, lng: Double?) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getWeatherUseCase.call(WeatherRequest(city, lat, lng))))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getAllCities() = getCitiesUseCase.call()

    fun getCityByName(name: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getCityByNameUseCase.call(name)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun getLocation() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = getLocationUseCase.call(null)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun cancel() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = cancelLocationRequestUseCase.call(null)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun onLocationPermissionGranted() {
        _permissionGranted.postValue(true)
    }

    fun onLocationPermissionDenied() {
        _permissionGranted.postValue(false)
    }
}