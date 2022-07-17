package com.shayo.weather.di

import com.shayo.news.utils.imageloader.ImageLoader
import com.shayo.weather.ui.utils.imageloader.ImageLoaderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ImageLoaderModule {
    @Binds
    @Singleton
    abstract fun bindImageLoader(impl: ImageLoaderImpl): ImageLoader
}