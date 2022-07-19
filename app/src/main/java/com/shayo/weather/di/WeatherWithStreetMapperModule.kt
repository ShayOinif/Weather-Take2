package com.shayo.weather.di

import com.shayo.weather.ui.utils.WeatherWithStreetMapper
import com.shayo.weather.ui.utils.WeatherWithStreetMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class WeatherWithStreetMapperModule {
    @Binds
    abstract fun bindWeatherWithStreetMapper(impl: WeatherWithStreetMapperImpl): WeatherWithStreetMapper
}