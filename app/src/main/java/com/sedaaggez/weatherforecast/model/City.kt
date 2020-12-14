package com.sedaaggez.weatherforecast.model

import com.google.gson.annotations.SerializedName

data class City(
    @SerializedName("woeid")
    val woeId: Int,
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("latt_long")
    val lattLong: String,
    val distance: Int
)
