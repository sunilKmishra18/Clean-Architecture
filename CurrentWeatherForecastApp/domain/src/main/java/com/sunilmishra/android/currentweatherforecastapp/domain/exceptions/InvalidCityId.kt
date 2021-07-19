package com.sunilmishra.android.currentweatherforecastapp.domain.exceptions

import java.lang.IllegalArgumentException

/**
 * Invalid place id
 */
class InvalidCityId : IllegalArgumentException()
{
    override val message: String?
        get() = "Please check your city id"
}
