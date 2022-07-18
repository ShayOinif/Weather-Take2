package com.shayo.weather.di

import android.content.Context
import android.location.LocationManager
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object LocationManagerModule {
    @Provides
    fun provideLocationManager(@ApplicationContext appContext: Context): LocationManager =
        ContextCompat.getSystemService(
            appContext,
            LocationManager::class.java
        ) as LocationManager
}