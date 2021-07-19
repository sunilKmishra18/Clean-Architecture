package com.sunilmishra.android.currentweatherforecastapp.domain

import com.sunilmishra.android.currentweatherforecastapp.domain.base.UseCase
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.RequestCurrentWeather
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.Weather
import com.sunilmishra.android.currentweatherforecastapp.domain.exceptions.IErrorHandler
import com.sunilmishra.android.currentweatherforecastapp.domain.exceptions.InvalidCityId
import com.sunilmishra.android.currentweatherforecastapp.domain.exceptions.InvalidRequestCurrentWeather
import javax.inject.Inject

class GetMultipleTimesWeather @Inject constructor(
    private val repository: Repository,
    errorHandler: IErrorHandler
) : UseCase<List<Weather>, RequestCurrentWeather>(errorHandler) {

    override suspend fun run(params: RequestCurrentWeather?): List<Weather> {
        when {
            params == null -> throw InvalidRequestCurrentWeather()
            params.cityId == null -> throw InvalidCityId()
            else -> return repository.getMultipleTimesWeather(params.cityId, params.cnt)
        }
    }

}