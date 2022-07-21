package com.shayo.weather.di

import com.shayo.weather.data.location.local.LocalLocationDatasource
import com.shayo.weather.data.location.local.LocalLocationDatasourceImpl
import com.shayo.weather.data.location.remote.RemoteLocationDatasource
import com.shayo.weather.data.location.remote.RemoteLocationDatasourceImpl
import com.shayo.weather.data.location.repository.LocationRepository
import com.shayo.weather.data.location.repository.LocationRepositoryImpl
import com.shayo.weather.data.location.repository.mediator.LocationMediator
import com.shayo.weather.data.location.repository.mediator.LocationMediatorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class LocationModule {
    @Binds
    abstract fun bindServiceLocationDatasource(impl: RemoteLocationDatasourceImpl): RemoteLocationDatasource

    @Binds
    abstract fun bindLocationRepository(impl: LocationRepositoryImpl): LocationRepository

    @Binds
    abstract fun bindLocalLocationDatasource(impl: LocalLocationDatasourceImpl): LocalLocationDatasource

    @Binds
    abstract fun bindLocationMediator(impl: LocationMediatorImpl): LocationMediator
}