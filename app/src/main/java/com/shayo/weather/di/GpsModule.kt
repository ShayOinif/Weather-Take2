package com.shayo.weather.di

import com.shayo.weather.broadcast.GpsReceiver
import com.shayo.weather.broadcast.GpsReceiverImpl
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
    @Binds
    abstract fun bindGpsReceiver(impl: GpsReceiverImpl): GpsReceiver

    @Singleton
    @Binds
    abstract fun bindGpsChecker(impl: GpsCheckerImpl): GpsChecker
}