package com.caffeine.caffeineweather.services.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.caffeine.caffeineweather.services.api.RetroInstance
import com.caffeine.caffeineweather.services.model.APIResponse
import com.caffeine.caffeineweather.utils.Constants
import com.caffeine.caffeineweather.utils.DataState
import com.caffeine.caffeineweather.utils.NetworkUtil

class WeatherRepository : WeatherInterface {
    override suspend fun getWeatherInfo(
        location: String,
        context: Context,
        instance: RetroInstance,
        mutableLiveData: MutableLiveData<DataState<APIResponse>>
    ) {
        if (NetworkUtil.internetAvailable(context)){
            mutableLiveData.postValue(DataState.Loading())

            try {
                val result = instance.getInstance.getWeatherInfo(location)

                when {
                    result.code() == 200 -> {
                        mutableLiveData.postValue(DataState.Success(result.body()))
                    }
                    result.code() == 403 -> {
                        mutableLiveData.postValue(DataState.Failed(Constants.API_LIMIT_REACHED))
                    }
                    result.code() == 429 -> {
                        mutableLiveData.postValue(DataState.Failed(Constants.TOO_MANY_REQUEST))
                    }
                    result.code() == 400 -> {
                        mutableLiveData.postValue(DataState.Failed(Constants.INVALID_INPUT))
                    }
                    else -> {
                        mutableLiveData.postValue(DataState.Failed(result.code().toString()))
                    }
                }
            }
            catch (e : Exception){
                mutableLiveData.postValue(DataState.Failed(e.message))
            }
        }
        else {
            mutableLiveData.postValue(DataState.Failed(Constants.NO_INTERNET))
        }
    }
}