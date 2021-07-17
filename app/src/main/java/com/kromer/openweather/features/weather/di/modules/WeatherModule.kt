package com.kromer.openweather.features.weather.di.modules

import com.kromer.openweather.core.local.MyDatabase
import com.kromer.openweather.features.weather.data.datasources.local.WeatherDao
import com.kromer.openweather.features.weather.data.datasources.local.WeatherLocalDataSource
import com.kromer.openweather.features.weather.data.datasources.local.WeatherLocalDataSourceImpl
import com.kromer.openweather.features.weather.data.datasources.remote.WeatherApiInterface
import com.kromer.openweather.features.weather.data.datasources.remote.WeatherRemoteDataSource
import com.kromer.openweather.features.weather.data.datasources.remote.WeatherRemoteDataSourceImpl
import com.kromer.openweather.features.weather.data.repositories.WeatherRepositoryImpl
import com.kromer.openweather.features.weather.domain.repositories.WeatherRepository
import com.kromer.openweather.features.weather.domain.usecases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherModule {

    @Provides
    @Singleton
    fun provideWeatherApiInterface(retrofit: Retrofit): WeatherApiInterface =
        retrofit.create(WeatherApiInterface::class.java)

    @Provides
    @Singleton
    fun provideWeatherDao(myDatabase: MyDatabase) = myDatabase.getWeatherDao()

    @Provides
    @Singleton
    fun provideWeatherRemoteDataSource(
        apiInterface: WeatherApiInterface
    ): WeatherRemoteDataSource {
        return WeatherRemoteDataSourceImpl(
            apiInterface
        )
    }

    @Provides
    @Singleton
    fun provideWeatherLocalDataSource(
        weatherDao: WeatherDao
    ): WeatherLocalDataSource {
        return WeatherLocalDataSourceImpl(
            weatherDao
        )
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        weatherRemoteDataSource: WeatherRemoteDataSource,
        weatherLocalDataSource: WeatherLocalDataSource
    ): WeatherRepository {
        return WeatherRepositoryImpl(
            weatherRemoteDataSource,
            weatherLocalDataSource
        )
    }

    @Provides
    @Singleton
    fun provideWeatherUseCase(
        repository: WeatherRepository
    ): GetWeatherUseCase {
        return GetWeatherUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideCitiesUseCase(
        repository: WeatherRepository
    ): GetCitiesUseCase {
        return GetCitiesUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideCityByNameUseCase(
        repository: WeatherRepository
    ): GetCityByNameUseCase {
        return GetCityByNameUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideCityByIdUseCase(
        repository: WeatherRepository
    ): GetCityByIdUseCase {
        return GetCityByIdUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideAddCityUseCase(
        repository: WeatherRepository
    ): AddCityUseCase {
        return AddCityUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideDeleteCityUseCase(
        repository: WeatherRepository
    ): DeleteCityUseCase {
        return DeleteCityUseCase(
            repository
        )
    }
}
