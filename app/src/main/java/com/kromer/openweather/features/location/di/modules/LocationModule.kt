package com.kromer.openweather.features.location.di.modules

import android.content.Context
import com.kromer.openweather.features.location.data.datasources.LocationDataSource
import com.kromer.openweather.features.location.data.datasources.LocationDataSourceImpl
import com.kromer.openweather.features.location.data.repositories.LocationRepositoryImpl
import com.kromer.openweather.features.location.domain.repositories.LocationRepository
import com.kromer.openweather.features.location.domain.usecases.CancelLocationRequestUseCase
import com.kromer.openweather.features.location.domain.usecases.GetLocationUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {

    @Provides
    @Singleton
    fun provideLocationDataSource(
        @ApplicationContext context: Context
    ): LocationDataSource {
        return LocationDataSourceImpl(
            context
        )
    }

    @Provides
    @Singleton
    fun provideLocationRepository(
        locationDataSource: LocationDataSource
    ): LocationRepository {
        return LocationRepositoryImpl(
            locationDataSource
        )
    }

    @Provides
    @Singleton
    fun provideGetLocationUseCase(
        repository: LocationRepository
    ): GetLocationUseCase {
        return GetLocationUseCase(
            repository
        )
    }

    @Provides
    @Singleton
    fun provideCancelLocationRequestUseCase(
        repository: LocationRepository
    ): CancelLocationRequestUseCase {
        return CancelLocationRequestUseCase(
            repository
        )
    }
}
