package com.shayo.weather.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object ConnectivityManagerModule {
    @Provides
    fun provideConnectivityManager(@ApplicationContext appContext: Context): ConnectivityManager {
        return ContextCompat.getSystemService(
            appContext,
            ConnectivityManager::class.java
        ) as ConnectivityManager
    }
}