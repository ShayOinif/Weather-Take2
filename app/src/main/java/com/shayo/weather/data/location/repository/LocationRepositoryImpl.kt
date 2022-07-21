package com.shayo.weather.data.location.repository

import android.location.Location
import com.shayo.weather.data.database.LocalLocation
import com.shayo.weather.data.location.local.LocalLocationDatasource
import com.shayo.weather.data.location.repository.mediator.LocationMediator
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val localLocationDatasource: LocalLocationDatasource,
    private val locationMediator: LocationMediator,
) : LocationRepository {
    override val currentLocation =
        localLocationDatasource.currentLocation

    override suspend fun refreshCurrentLocation(): Result<LocalLocation> {
        var result = Result.failure<LocalLocation>(Throwable())

        locationMediator.getNewLocation()
            .collect { localLocationResult ->
                result = localLocationResult.fold(
                    onSuccess = { newLocation ->
                        localLocationDatasource.clear()
                            .fold(
                                onSuccess = {
                                    newLocation.mapToLocal().let { locationToInsert ->
                                        localLocationDatasource.insertLocation(locationToInsert)
                                            .map {
                                                locationToInsert
                                            }
                                    }
                                },
                                onFailure = {
                                    Result.failure(it)
                                }
                            )
                    },
                    onFailure = {
                        Result.failure(it)
                    }
                )
            }

        return result
    }
}

private fun Location.mapToLocal() =
    LocalLocation(
        latitude,
        longitude,
        time
    )