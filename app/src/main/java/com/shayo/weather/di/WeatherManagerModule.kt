package com.shayo.weather.di

import com.shayo.weather.data.WeatherManager
import com.shayo.weather.data.WeatherManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class WeatherManagerModule {
    @Binds
    abstract fun bindWeatherManager(impl: WeatherManagerImpl): WeatherManager
}