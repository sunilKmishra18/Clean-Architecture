package com.sunilmishra.android.currentweatherforecastapp.presentation.mapper

import com.sunilmishra.android.currentweatherforecastapp.domain.entity.TimeState
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.WeatherState
import com.sunilmishra.android.currentweatherforecastapp.presentation.mapper.WeatherStateMap

fun getWeatherIcon(state: WeatherState, currentTimeState: TimeState): Int {
    return WeatherStateMap.icon[Pair(
        state,
        currentTimeState
    )] ?: WeatherStateMap.icon[Pair(
        state,
        TimeState.Undefined
    )]!!
}