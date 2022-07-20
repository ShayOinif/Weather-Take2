package com.shayo.weather.domain

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshCurrentWeatherUseCase @Inject constructor(
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
    private val saveWeatherUseCase: SaveWeatherUseCase
) {
    @OptIn(FlowPreview::class)
    suspend operator fun invoke() =
        withContext(Dispatchers.IO) {
            getCurrentLocationUseCase.invoke()
                .flatMapConcat { locationResult ->
                    if (locationResult.isSuccess) {
                        getWeatherByLocationUseCase.invoke(locationResult.getOrNull()!!)
                    } else {
                        flow {
                            emit(
                                Result.failure(locationResult.exceptionOrNull()!!)
                            )
                        }
                    }
                }.map {
                    it.fold(
                        { weather ->
                            saveWeatherUseCase.invoke(weather)
                        },
                        { t ->
                            Result.failure(t)
                        }
                    )
                }
        }
}