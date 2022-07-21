package com.shayo.weather.data.location.repository.mediator

import com.shayo.weather.data.location.remote.RemoteLocationDatasource
import javax.inject.Inject

class LocationMediatorImpl @Inject constructor(
    private val remoteLocationDatasource: RemoteLocationDatasource,
) : LocationMediator {
    override fun getNewLocation() =
        remoteLocationDatasource.getCurrentLocation()
}