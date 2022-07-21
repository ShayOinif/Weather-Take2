package com.shayo.weather.di

import com.shayo.weather.domain.utils.WeatherMapper
import com.shayo.weather.domain.utils.WeatherMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class WeatherMapperModule {
    @Binds
    abstract fun bindWeatherMapper(impl: WeatherMapperImpl): WeatherMapper
}