package com.caffeine.caffeineweather.services.api

import com.caffeine.caffeineweather.services.model.APIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface APIInterface {
    @GET("data/weather/{location}")
    suspend fun getWeatherInfo(@Path("location") location : String) : Response<APIResponse>
}