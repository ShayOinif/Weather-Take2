package com.shayo.weather.domain

import javax.inject.Inject

class RefreshCurrentWeatherUseCase @Inject constructor(
  private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
  private val getWeatherByLocationUseCase: GetWeatherByLocationUseCase,
  private val saveWeatherUseCase: SaveWeatherUseCase
) {

}