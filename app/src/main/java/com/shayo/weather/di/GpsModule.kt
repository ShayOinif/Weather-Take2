package com.shayo.weather.di

import com.shayo.weather.utils.gps.GpsChecker
import com.shayo.weather.utils.gps.GpsCheckerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class GpsModule {
    @Singleton
    @Binds
    abstract fun bindGpsChecker(impl: GpsCheckerImpl): GpsChecker
}