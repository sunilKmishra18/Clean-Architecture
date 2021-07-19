package com.sunilmishra.android.currentweatherforecastapp.data.remote

import com.sunilmishra.android.currentweatherforecastapp.data.remote.model.CurrentWeatherResponse
import com.sunilmishra.android.currentweatherforecastapp.data.remote.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface OpenWeatherApiService {

    /**
     * Get current weather of city
     * @param cityId String city Id
     * @param appId String api key
     * @param units String for temp unit
     * @return instance of [CurrentWeatherResponse]
     */

    @GET("weather")
    suspend fun getCurrentWeather(
        @Query("id") cityId: Int,
        @Query("units") units: String,
        @Query("appid") appId: String
    ): CurrentWeatherResponse

    /**
     * Get multiple days weather
     *
     * @param cityId String city Id
     * @param units String units of response
     * @param lang  String language of response
     * @param appId String api key
     * @return instance of [ForecastResponse]
     */
    @GET("forecast")
    suspend fun getMultipleTimesWeather(
        @Query("id") cityId: Int,
        @Query("units") units: String?,
        @Query("lang") lang: String?,
        @Query("cnt") dayCount: Int,
        @Query("appid") appId: String?
    ): ForecastResponse
}