package com.shayo.weather.ui.home

import androidx.lifecycle.ViewModel
import com.shayo.weather.domain.GetCurrentWeatherUseCase
import com.shayo.weather.ui.model.WeatherWithAddress
import com.shayo.weather.ui.utils.WeatherWithStreetMapper
import com.shayo.weather.utils.WeatherAppThrowable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    weatherWithStreetMapper: WeatherWithStreetMapper
) : ViewModel() {

    val currentWeatherFlow = getCurrentWeatherUseCase.invoke()
        .filter {
            it.isSuccess || (it.isFailure && it.exceptionOrNull() == WeatherAppThrowable.NoCurrentWeather)
        }
        .map { weatherResult ->
            weatherResult
                .fold(
                    { weather ->
                        CurrentWeather.NotEmpty(
                            weatherWithStreetMapper.mapToWeatherWithStreet(weather)
                        )
                    },
                    {
                        CurrentWeather.Empty
                    }
                )
        }
}

sealed class CurrentWeather {
    class NotEmpty(val weatherWithAddress: WeatherWithAddress) : CurrentWeather()
    object Empty : CurrentWeather()
}