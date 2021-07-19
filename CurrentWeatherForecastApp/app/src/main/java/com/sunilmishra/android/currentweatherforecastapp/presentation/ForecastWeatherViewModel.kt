package com.sunilmishra.android.currentweatherforecastapp.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sunilmishra.android.currentweatherforecastapp.domain.GetCurrentTimeState
import com.sunilmishra.android.currentweatherforecastapp.domain.GetCurrentWeather
import com.sunilmishra.android.currentweatherforecastapp.domain.GetMultipleTimesWeather
import com.sunilmishra.android.currentweatherforecastapp.domain.base.UseCaseCallback
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.RequestCurrentWeather
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.TimeState
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.Weather
import com.sunilmishra.android.currentweatherforecastapp.domain.exceptions.ErrorModel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

class ForecastWeatherViewModel @ViewModelInject constructor(
    private val getCurrentWeather: GetCurrentWeather,
    private val getCurrentTimeState: GetCurrentTimeState,
    private val getMultipleTimesWeather: GetMultipleTimesWeather
) :
    ViewModel() {

    private val _currentWeather by lazy { MutableLiveData<Weather>() }
    val currentWeather: LiveData<Weather> get() = _currentWeather

    private val _error by lazy { MutableLiveData<ErrorModel>() }
    val error: LiveData<ErrorModel> get() = _error

    private val _currentTimeState by lazy { MutableLiveData<TimeState>() }
    val currentTimeState: LiveData<TimeState> get() = _currentTimeState

    private val _multipleTimeWeather by lazy { MutableLiveData<List<Weather>>() }
    val multipleTimeWeather: LiveData<List<Weather>> get() = _multipleTimeWeather

    private val _currentCityId by lazy { MutableLiveData<Int>() }
    val currentCityId: LiveData<Int> get() = _currentCityId

    fun getCurrentWeather() {
        viewModelScope.launch {

            tickFlow(60000L).collect{
                getCurrentWeather.call(currentCityId.value, object : UseCaseCallback<Weather> {
                    override fun onSuccess(result: Weather) {
                        _currentWeather.value = result
                    }

                    override fun onError(errorModel: ErrorModel?) {
                        _error.value = errorModel
                    }
                })
            }

            /*getCurrentWeather.call(currentCityId.value, object : UseCaseCallback<Weather> {
                override fun onSuccess(result: Weather) {
                    _currentWeather.value = result
                }

                override fun onError(errorModel: ErrorModel?) {
                    _error.value = errorModel
                }
            })*/
        }
    }

    fun tickFlow(millis: Long) = callbackFlow<Int> {
        val timer = Timer()
        var time = 0
        timer.scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    try { offer(time) } catch (e: Exception) {}
                    time += 1
                }
            },
            0,
            millis)
        awaitClose {
            timer.cancel()
        }
    }

    fun getCurrentTimeState() {
        viewModelScope.launch {
            getCurrentTimeState.call(onResult = object : UseCaseCallback<TimeState> {
                override fun onSuccess(result: TimeState) {
                    _currentTimeState.value = result
                }

                override fun onError(errorModel: ErrorModel?) {
                    _error.value = errorModel
                }
            })
        }
    }

    fun getMultipleTimesWeather() {
        viewModelScope.launch {
            getMultipleTimesWeather.call(
                RequestCurrentWeather(currentCityId.value, 7),
                object : UseCaseCallback<List<Weather>> {
                    override fun onSuccess(result: List<Weather>) {
                        _multipleTimeWeather.value = result
                    }

                    override fun onError(errorModel: ErrorModel?) {
                        _error.value = errorModel
                    }
                })

        }
    }

    fun setCurrentCityId(value: Int) {
        _currentCityId.value = value
    }

    fun setCurrentWeather(value: Weather) {
        _currentWeather.value = value
    }
}