package com.sunilmishra.android.currentweatherforecastapp.domain

import com.sunilmishra.android.currentweatherforecastapp.domain.entity.Weather

interface Repository {

    suspend fun getCurrentWeather(cityId: Int): Weather
    suspend fun getMultipleTimesWeather(cityId: Int, cnt: Int): List<Weather>

}