package com.shayo.weather.di

import com.shayo.weather.data.weather.repository.WeatherRepository
import com.shayo.weather.data.weather.repository.WeatherRepositoryImpl
import com.shayo.weather.data.weather.repository.mediator.WeatherMediator
import com.shayo.weather.data.weather.repository.mediator.WeatherMediatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class WeatherRepositoryModule {
    @Singleton
    @Binds
    abstract fun bindWeatherRepository(impl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    abstract fun bindWeatherMediator(impl: WeatherMediatorImpl): WeatherMediator
}