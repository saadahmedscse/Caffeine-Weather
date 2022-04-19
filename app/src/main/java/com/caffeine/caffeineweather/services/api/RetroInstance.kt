package com.caffeine.caffeineweather.services.api

import com.caffeine.caffeineweather.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val getInstance : APIInterface by lazy {
        retrofit.create(APIInterface::class.java)
    }
}