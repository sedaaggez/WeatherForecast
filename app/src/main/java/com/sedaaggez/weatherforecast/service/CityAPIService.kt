package com.sedaaggez.weatherforecast.service

import com.sedaaggez.weatherforecast.model.City
import com.sedaaggez.weatherforecast.model.DailyWeather
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class CityAPIService {

    private val BASE_URL = "https://www.metaweather.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(CityAPI::class.java)

    fun getCities(lattlong: String): Single<List<City>> {
        return api.getCities(lattlong)
    }

    fun getDailyWeatherList(woeid: Int): Single<DailyWeather> {
        return api.getDailyWeatherList(woeid)
    }

}