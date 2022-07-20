package com.shayo.weather.data.location.repository


import com.shayo.weather.data.location.local.LocalLocationDatasource
import com.shayo.weather.data.location.service.ServiceLocationDatasource
import kotlinx.coroutines.flow.combine
import javax.inject.Inject



class LocationRepositoryImpl @Inject constructor(
    private val serviceLocationDatasource: ServiceLocationDatasource,
    private val localLocationDatasource: LocalLocationDatasource,
) : LocationRepository {
    override fun getCurrentLocation() =
    combine(
        localLocationDatasource.getLocation(),
        serviceLocationDatasource.getCurrentLocation()
    ) { localResult, serviceResult ->
        if (serviceResult.isSuccess) {
            localLocationDatasource.deleteAll()
            localLocationDatasource.insertLocation(serviceResult.getOrNull()!!)

            serviceResult
        }
        else
            localResult
    }
}