package com.shayo.weather.domain

import com.shayo.weather.data.location.repository.LocationRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCurrentLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
){
    operator fun invoke() =
        locationRepository.getCurrentLocation().flowOn(Dispatchers.IO)
}