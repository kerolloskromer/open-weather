package com.kromer.openweather.features.weather.domain.usecases

import androidx.lifecycle.MutableLiveData
import com.kromer.openweather.features.weather.domain.entities.City
import com.kromer.openweather.features.weather.domain.entities.Forecast
import com.kromer.openweather.features.weather.domain.repositories.WeatherRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*


class GetCitiesUseCaseTest {

    private lateinit var repository: WeatherRepository
    private lateinit var getCitiesUseCase: GetCitiesUseCase

    @Before
    fun setup() {
        repository = mock()
        getCitiesUseCase = GetCitiesUseCase(repository)
    }

    @Test
    fun `should get cities list from repository`() {
        val forecastList = listOf(
            Forecast(
                1626566400,
                "2021-07-18 00:00:00",
                20f,
                20f,
                20f,
                20f,
                20f,
                20f,
                20f,
                "clear"
            )
        )
        val citiesList = listOf(
            City(
                1,
                "Cairo",
                "EG",
                forecastList,
            )
        )

        val liveData = MutableLiveData<List<City>>().apply { postValue(citiesList) }

        runBlocking {
            // arrange
            whenever(repository.getAll()).thenReturn(liveData)
            // act
            val result = getCitiesUseCase.call()
            // assert
            assertEquals(liveData, result)
            verify(repository, times(1)).getAll()
            verifyNoMoreInteractions(repository)
        }
    }
}