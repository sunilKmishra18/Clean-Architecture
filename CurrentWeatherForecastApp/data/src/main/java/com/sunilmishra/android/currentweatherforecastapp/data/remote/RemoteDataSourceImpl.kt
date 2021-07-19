package com.sunilmishra.android.currentweatherforecastapp.data.remote

import android.content.Context
import com.sunilmishra.android.currentweatherforecastapp.data.R
import com.sunilmishra.android.currentweatherforecastapp.data.mapper.mapToForecast
import com.sunilmishra.android.currentweatherforecastapp.data.mapper.mapToWeather
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.Weather
import com.sunilmishra.android.currentweatherforecastapp.data.remote.OpenWeatherApiService
import com.sunilmishra.android.currentweatherforecastapp.data.remote.RemoteDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

public class RemoteDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val service: OpenWeatherApiService
) : RemoteDataSource {

    companion object {
    }

    override suspend fun getCurrentWeather(cityId: Int): Weather {
        val currentWeather = service.getCurrentWeather(
            cityId = cityId,
            units = "metric",
            appId = context.getString(R.string.app_id)
        )

        return mapToWeather(currentWeather)
    }

    override suspend fun getMultipleTimesWeather(cityId: Int, cnt: Int): List<Weather> {
        val multipleTimesWeather = service.getMultipleTimesWeather(
            cityId,
            "metric",
            "en",
            cnt,
            context.getString(R.string.app_id)
        )

        return mapToForecast(multipleTimesWeather)
    }

}