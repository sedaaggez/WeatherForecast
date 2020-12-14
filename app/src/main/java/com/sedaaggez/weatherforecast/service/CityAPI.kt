package com.sedaaggez.weatherforecast.service

import com.sedaaggez.weatherforecast.model.City
import com.sedaaggez.weatherforecast.model.DailyWeather
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CityAPI {

    // https://www.metaweather.com/api/location/search/?lattlong=36.96,-122.02
    @GET("api/location/search")
    fun getCities(@Query("lattlong") lattlong: String): Single<List<City>>

    // https://www.metaweather.com/api/location/2488853
    @GET("api/location/{woeId}")
    fun getDailyWeatherList(@Path("woeId") woeId: Int): Single<DailyWeather>

}