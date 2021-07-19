package com.sunilmishra.android.currentweatherforecastapp.data.local

import com.sunilmishra.android.currentweatherforecastapp.data.local.PreferencesHelper
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val preferences: PreferencesHelper
) : LocalDataSource {

}