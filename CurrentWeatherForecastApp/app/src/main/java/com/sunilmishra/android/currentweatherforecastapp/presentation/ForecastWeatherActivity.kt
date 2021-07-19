package com.sunilmishra.android.currentweatherforecastapp.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.sunilmishra.android.currentweatherforecastapp.presentation.adapter.ForecastWeatherTimesAdapter
import com.sunilmishra.android.currentweatherforecastapp.R
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.TimeState
import com.sunilmishra.android.currentweatherforecastapp.domain.entity.Weather
import com.sunilmishra.android.currentweatherforecastapp.presentation.components.SpanningLinearLayoutManager
import com.sunilmishra.android.currentweatherforecastapp.presentation.mapper.getWeatherIcon
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class ForecastWeatherActivity : AppCompatActivity() {
    private var toast: Toast? = null
    private lateinit var forecastWeatherTimesAdapter: ForecastWeatherTimesAdapter
    private lateinit var cityData: List<Pair<Int, String>>

    private val forecastWeatherViewModel: ForecastWeatherViewModel by viewModels()
    private var currentTimeState: TimeState? = null
    private var isNotNowWeather = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        registerObservers()
        forecastWeatherViewModel.getCurrentTimeState()

        val cityIds = resources.getIntArray(R.array.city_ids)
        val cityNames = resources.getStringArray(R.array.city_name)
        cityData = cityIds.zip(cityNames)
        forecastWeatherViewModel.setCurrentCityId(cityData.first().first)
    }

    @SuppressLint("SetTextI18n")
    private fun registerObservers() {
        forecastWeatherViewModel.currentCityId.observe(this, Observer {
            forecastWeatherViewModel.getCurrentWeather()
            forecastWeatherViewModel.getMultipleTimesWeather()
            isNotNowWeather = false
        })

        forecastWeatherViewModel.error.observe(this, Observer {
            toast?.cancel()
            toast = Toast.makeText(this, it.message, Toast.LENGTH_LONG)
            toast?.show()
        })

        forecastWeatherViewModel.currentTimeState.observe(this, Observer { timeState ->
            setBackgroundContainer(timeState)
            currentTimeState = timeState
        })

        forecastWeatherViewModel.currentWeather.observe(this, Observer {
            txtDegree.text = "${it.temp}°"
            if (it.min != it.max)
                txtDegreeRange.text = "${it.min}°/${it.max}°"
            else
                txtDegreeRange.text = ""
            setWeatherIcon(it)
        })

        forecastWeatherViewModel.multipleTimeWeather.observe(this, Observer {
            forecastWeatherTimesAdapter.setData(it, currentTimeState!!)
        })
    }

    private fun setBackgroundContainer(timeState: TimeState?) {
        container.setBackgroundResource(
            when (timeState) {
                TimeState.Dawn ->
                    R.drawable.background_dawn
                TimeState.Night ->
                    R.drawable.background_night
                TimeState.Morning ->
                    R.drawable.background_morning
                TimeState.Evening ->
                    R.drawable.background_evening
                TimeState.Noon ->
                    R.drawable.background_noon
                else ->
                    R.drawable.background_noon
            }
        )
    }

    private fun setWeatherIcon(weather: Weather) {
        imgIcon.setImageResource(getWeatherIcon(weather.state, currentTimeState!!))
    }


    private fun initViews() {
        rvTimes.layoutManager = SpanningLinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        forecastWeatherTimesAdapter = ForecastWeatherTimesAdapter(this)
        rvTimes.adapter = forecastWeatherTimesAdapter

        spCity.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                forecastWeatherViewModel.setCurrentCityId(cityData[position].first)
            }

        }
    }

    override fun onPause() {
        super.onPause()
        toast?.cancel()
    }

    override fun onBackPressed() {
        if (isNotNowWeather) {
            isNotNowWeather = false
            forecastWeatherViewModel.setCurrentCityId(cityData[spCity.selectedItemPosition].first)
        } else
            super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModelStore.clear()
    }
}