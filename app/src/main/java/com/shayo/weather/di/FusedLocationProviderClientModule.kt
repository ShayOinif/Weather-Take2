package com.shayo.weather.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object FusedLocationProviderClientModule {
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext applicationContext: Context,
    ): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(applicationContext)
}