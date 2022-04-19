package com.caffeine.caffeineweather.services.model

data class CurrentConditions(
    val dayhour : String? = null,
    val temp : Temp? = null,
    val precip : String? = null,
    val humidity : String? = null,
    val wind : Wind? = null,
    val iconURL : String? = null,
    val comment : String? = null
)