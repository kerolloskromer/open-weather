package com.kromer.openweather.core.usecases

interface NoParamUseCase<T> {
    fun call(): T
}