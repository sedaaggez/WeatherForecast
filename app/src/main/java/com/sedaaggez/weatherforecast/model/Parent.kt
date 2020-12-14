package com.sedaaggez.weatherforecast.model

import com.google.gson.annotations.SerializedName

data class Parent(
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("woeid")
    val woeId: Long,
    @SerializedName("latt_long")
    val lattLong: String
)