package com.caffeine.caffeineweather.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.caffeine.caffeineweather.services.api.RetroInstance
import com.caffeine.caffeineweather.services.model.APIResponse
import com.caffeine.caffeineweather.services.repository.WeatherRepository
import com.caffeine.caffeineweather.utils.Constants
import com.caffeine.caffeineweather.utils.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val repository = WeatherRepository()

    private val weatherMutableLiveData = Constants.getStateOfWeather()
    val weatherLiveData : LiveData<DataState<APIResponse>>
        get() = weatherMutableLiveData

    fun getWeatherInfo(location : String, context: Context, instance: RetroInstance){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getWeatherInfo(location, context, instance, weatherMutableLiveData)
        }
    }
}