package com.shayo.weather.di

import com.shayo.weather.data.weather.local.LocalWeatherDatasource
import com.shayo.weather.data.weather.local.LocalWeatherDatasourceImpl
import com.shayo.weather.data.weather.network.RemoteWeatherDatasource
import com.shayo.weather.data.weather.network.RemoteWeatherDatasourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class WeatherDatasourceModule {
    @Binds
    abstract fun bindRemoteWeatherDatasource(impl: RemoteWeatherDatasourceImpl): RemoteWeatherDatasource

    @Binds
    abstract fun bindLocalWeatherDatasource(impl: LocalWeatherDatasourceImpl): LocalWeatherDatasource
}