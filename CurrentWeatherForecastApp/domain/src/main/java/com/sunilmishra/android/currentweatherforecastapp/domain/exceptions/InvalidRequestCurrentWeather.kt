package com.sunilmishra.android.currentweatherforecastapp.domain.exceptions

import java.lang.IllegalArgumentException

/**
 * Invalid place id
 */
class InvalidRequestCurrentWeather : IllegalArgumentException()
{
    override val message: String?
        get() = "Please check your RequestCurrentDays"
}
