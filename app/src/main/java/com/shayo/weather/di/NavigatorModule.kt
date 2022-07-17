package com.shayo.weather.di

import com.shayo.weather.ui.utils.navigator.HomeFragmentNavigatorImpl
import com.shayo.weather.ui.utils.navigator.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.shayo.whatsapp.ui.navigator.NavigatorImpl
import javax.inject.Qualifier

@Qualifier
annotation class MainNavigator

@Qualifier
annotation class HomeFragmentNavigator

@InstallIn(SingletonComponent::class)
@Module
abstract class NavigatorModule {

    @MainNavigator
    @Binds
    abstract fun provideMainNavigator(impl: NavigatorImpl): Navigator

    @HomeFragmentNavigator
    @Binds
    abstract fun provideHomeFragmentNavigator(impl: HomeFragmentNavigatorImpl): Navigator
}