package com.shayo.weather.di

import com.shayo.weather.utils.externalcoroutine.ExternalCoroutine
import com.shayo.weather.utils.externalcoroutine.ExternalCoroutineImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class ExternalCoroutineModule {
    @Singleton
    @Binds
    abstract fun bindExternalCoroutine(impl: ExternalCoroutineImpl): ExternalCoroutine
}