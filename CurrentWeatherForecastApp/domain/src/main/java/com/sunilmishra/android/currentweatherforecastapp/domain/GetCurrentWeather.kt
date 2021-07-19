package com.sunilmishra.android.currentweatherforecastapp.domain

import com.sunilmishra.android.currentweatherforecastapp.domain.base.UseCase
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.Weather
import com.sunilmishra.android.currentweatherforecastapp.domain.exceptions.IErrorHandler
import com.sunilmishra.android.currentweatherforecastapp.domain.exceptions.InvalidCityId
import javax.inject.Inject

class GetCurrentWeather @Inject constructor(private val repository: Repository, errorHandler: IErrorHandler) :
    UseCase<Weather, Int>(errorHandler) {

    override suspend fun run(params: Int?): Weather {
        if (params == null)
            throw InvalidCityId()
        else
            return repository.getCurrentWeather(params)
    }


}