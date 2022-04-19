package com.caffeine.caffeineweather.services.model

data class NextDay(
    val day : String? = null,
    val comment : String? = null,
    val max_temp : MaxTemp? = null,
    val min_temp : MinTemp? = null,
    val iconURL : String? = null
)
