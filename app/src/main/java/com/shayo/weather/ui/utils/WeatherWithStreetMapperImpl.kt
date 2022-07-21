package com.shayo.weather.ui.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.shayo.weather.model.Weather
import com.shayo.weather.ui.model.WeatherWithAddress
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WeatherWithStreetMapperImpl @Inject constructor(
    @ApplicationContext applicationContext: Context
) : WeatherWithStreetMapper {

    private val gCoder = Geocoder(applicationContext)

    override fun mapToWeatherWithStreet(weather: Weather): WeatherWithAddress {
        val addresses: List<Address> = gCoder.getFromLocation(weather.lat, weather.lng, 1)

        return WeatherWithAddress(
            weather,
            if (addresses.isNotEmpty()) {
                addresses[0].getAddressLine(0) ?: addresses[0].countryName
            } else {
                null
            }
        )
    }
}