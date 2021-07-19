package com.sunilmishra.android.currentweatherforecastapp.domain.base

import com.sunilmishra.android.currentweatherforecastapp.domain.exceptions.ErrorModel

interface UseCaseCallback<Type> {

    fun onSuccess(result: Type)

    fun onError(errorModel: ErrorModel?)
}