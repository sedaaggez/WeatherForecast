package com.sedaaggez.weatherforecast.model

import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class ConsolidatedWeather(
    val id: Long,
    @SerializedName("weather_state_name")
    val weatherStateName: String,
    @SerializedName("weather_state_abbr")
    val weatherStateAbbr: String,
    @SerializedName("wind_direction_compass")
    val windDirectionCompass: String,
    val created: String,
    @SerializedName("applicable_date")
    val applicableDate: String,
    @SerializedName("min_temp")
    val minTemp: Double,
    @SerializedName("max_temp")
    val maxTemp: Double,
    @SerializedName("the_temp")
    val temp: Double,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("wind_direction")
    val windDirection: Double,
    @SerializedName("air_pressure")
    val airPressure: Double,
    val humidity: Long,
    val visibility: Double,
    val predictability: Long
) {
    val iconUrl: String
        get() = String.format(
            "https://www.metaweather.com/static/img/weather/png/$weatherStateAbbr.png",
            weatherStateAbbr
        )

    val formattedMinTemp: String
        get() = String.format("%d°", minTemp.roundToInt())

    val formattedMaxTemp: String
        get() = String.format("%d°", maxTemp.roundToInt())

    val formattedTemp: String
        get() = String.format("%d°", temp.roundToInt())

    val formattedHumidity: String
        get() = String.format(humidity.toInt().toString() + "%%")

    val formattedWindSpeed: String
        get() = String.format(windSpeed.toInt().toString() + " km/h")

    val formattedWeatherStateInfo: String
        get() = String.format("Today: Current Weather $weatherStateName.")

    val formattedTempInfo: String
        get() = String.format("Temperature " + temp.toInt() + "°" + ", today's highest forecast " + maxTemp.toInt() + "°" + ".")


}