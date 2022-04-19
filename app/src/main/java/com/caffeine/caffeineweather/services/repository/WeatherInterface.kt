package com.caffeine.caffeineweather.services.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.caffeine.caffeineweather.services.api.RetroInstance
import com.caffeine.caffeineweather.services.model.APIResponse
import com.caffeine.caffeineweather.utils.DataState

interface WeatherInterface {

    suspend fun getWeatherInfo(location : String,
                               context : Context,
                               instance : RetroInstance,
                               mutableLiveData : MutableLiveData<DataState<APIResponse>>)
}