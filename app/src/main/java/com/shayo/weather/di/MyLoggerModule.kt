package com.shayo.weather.di

import com.shayo.weather.utils.logger.MyLogger
import com.shayo.weather.utils.logger.MyLoggerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class MyLoggerModule {
    @Binds
    abstract fun bindMyLogger(impl: MyLoggerImpl): MyLogger
}