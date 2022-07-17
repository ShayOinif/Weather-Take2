package com.shayo.weather.di

import com.shayo.weather.ui.utils.permissionmanager.PermissionManager
import com.shayo.weather.ui.utils.permissionmanager.PermissionManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class PermissionManagerModule {

    @Binds
    abstract fun bindPermissionManager(impl: PermissionManagerImpl): PermissionManager
}