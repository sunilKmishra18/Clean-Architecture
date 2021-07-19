package com.sunilmishra.android.currentweatherforecastapp.domain.exceptions

import com.sunilmishra.android.currentweatherforecastapp.domain.exceptions.ErrorModel

interface IErrorHandler {
    fun handleException(throwable: Throwable?): ErrorModel
}