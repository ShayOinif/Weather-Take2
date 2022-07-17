package com.shayo.weather.di

import com.shayo.weather.utils.networkstatuschecker.NetworkStatusChecker
import com.shayo.weather.utils.networkstatuschecker.NetworkStatusCheckerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkStatusCheckerModule {
    @Singleton
    @Binds
    abstract fun bindNetStatusChecker(impl: NetworkStatusCheckerImpl): NetworkStatusChecker
}