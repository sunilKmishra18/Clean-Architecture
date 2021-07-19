package com.sunilmishra.android.currentweatherforecastapp.data.mapper

import com.sunilmishra.android.currentweatherforecastapp.data.remote.model.CurrentWeatherResponse
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.Weather
import kotlin.math.roundToInt

fun mapToWeather(currentWeather: CurrentWeatherResponse): Weather {
    val icon = currentWeather.weather.first().icon

    return Weather(
        currentWeather.main.temp_min.roundToInt(),
        currentWeather.main.temp_max.roundToInt(),
        currentWeather.main.temp.roundToInt(),
        mapWeatherIcon(icon)
    )
}
