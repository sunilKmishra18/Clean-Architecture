package com.sunilmishra.android.currentweatherforecastapp.data.di

import android.content.Context
import com.sunilmishra.android.currentweatherforecastapp.data.ErrorHandler
import com.sunilmishra.android.currentweatherforecastapp.data.RepositoryImpl
import com.sunilmishra.android.currentweatherforecastapp.data.local.LocalDataSource
import com.sunilmishra.android.currentweatherforecastapp.data.local.LocalDataSourceImpl
import com.sunilmishra.android.currentweatherforecastapp.data.local.PreferencesHelper
import com.sunilmishra.android.currentweatherforecastapp.data.remote.OpenWeatherApiService
import com.sunilmishra.android.currentweatherforecastapp.data.remote.RemoteDataSource
import com.sunilmishra.android.currentweatherforecastapp.data.remote.RemoteDataSourceImpl
import com.sunilmishra.android.currentweatherforecastapp.domain.Repository
import com.sunilmishra.android.currentweatherforecastapp.domain.exceptions.IErrorHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideRepository(
        remoteDataSourceImpl: RemoteDataSourceImpl,
        localDataSourceImpl: LocalDataSourceImpl
    ): Repository {
        return RepositoryImpl(remoteDataSourceImpl, localDataSourceImpl)
    }

    @Singleton
    @Provides
    fun provideLocalDataSource(
        preferencesHelper: PreferencesHelper
    ): LocalDataSource {
        return LocalDataSourceImpl(preferencesHelper)
    }

    @Singleton
    @Provides
    fun providePreferencesHelper(@ApplicationContext context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(
        @ApplicationContext context: Context,
        weatherApiService: OpenWeatherApiService
    ): RemoteDataSource {
        return RemoteDataSourceImpl(context, weatherApiService)
    }

    @Singleton
    @Provides
    fun provideErrorHandler(): IErrorHandler {
        return ErrorHandler()
    }

}