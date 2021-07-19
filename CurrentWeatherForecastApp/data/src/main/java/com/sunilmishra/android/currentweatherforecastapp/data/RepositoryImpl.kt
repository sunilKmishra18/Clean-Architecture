package com.sunilmishra.android.currentweatherforecastapp.data

import com.sunilmishra.android.currentweatherforecastapp.data.local.LocalDataSource
import com.sunilmishra.android.currentweatherforecastapp.data.remote.RemoteDataSource
import com.sunilmishra.android.currentweatherforecastapp.domain.Repository
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.Weather
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : Repository {

    override suspend fun getCurrentWeather(cityId: Int): Weather {
        return remoteDataSource.getCurrentWeather(cityId)
    }

    override suspend fun getMultipleTimesWeather(cityId: Int, cnt: Int): List<Weather> {
        return remoteDataSource.getMultipleTimesWeather(cityId, cnt)
    }

}