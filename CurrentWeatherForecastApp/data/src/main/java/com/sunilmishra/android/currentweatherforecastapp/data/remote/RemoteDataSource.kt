package com.sunilmishra.android.currentweatherforecastapp.data.remote

import com.sunilmishra.android.currentweatherforecastapp.domain.entity.Weather

interface RemoteDataSource {
    suspend fun getCurrentWeather(cityId: Int): Weather

    suspend fun getMultipleTimesWeather(cityId: Int, cnt: Int): List<Weather>
}