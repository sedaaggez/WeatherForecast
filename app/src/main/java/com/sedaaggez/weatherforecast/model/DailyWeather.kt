package com.sedaaggez.weatherforecast.model

import com.google.gson.annotations.SerializedName

data class DailyWeather(
    @SerializedName("consolidated_weather")
    val consolidatedWeatherList: List<ConsolidatedWeather>,
    val time: String,
    @SerializedName("sun_rise")
    val sunRise: String,
    @SerializedName("sun_set")
    val sunSet: String,
    @SerializedName("timezone_name")
    val timezoneName: String,
    val parent: Parent,
    val sources: List<Source>,
    val title: String,
    @SerializedName("location_type")
    val locationType: String,
    @SerializedName("woeid")
    val woeId: Long,
    @SerializedName("latt_long")
    val lattLong: String,
)