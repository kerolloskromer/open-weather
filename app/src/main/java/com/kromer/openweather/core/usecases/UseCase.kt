package com.kromer.openweather.core.usecases

interface UseCase<T, Params> {
    suspend fun call(params: Params): T
}