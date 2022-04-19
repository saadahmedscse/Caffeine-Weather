package com.caffeine.caffeineweather.services.model

data class APIResponse(
    val region : String? = null,
    val currentConditions : CurrentConditions? = null,
    val next_days : List<NextDay>? = null,
    val contact_author : ContactAuthor? = null,
    val data_source : String? = null
)