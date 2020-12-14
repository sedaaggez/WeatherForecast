package com.sedaaggez.weatherforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sedaaggez.weatherforecast.model.DailyWeather
import com.sedaaggez.weatherforecast.service.CityAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class DailyWeatherViewModel : ViewModel() {

    private val cityAPIService = CityAPIService()
    private val disposable = CompositeDisposable()

    val dayLiveData = MutableLiveData<DailyWeather>()

    fun getDataDailyWeatherList(woeId: Int) {
        disposable.add(
            cityAPIService.getDailyWeatherList(woeId)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<DailyWeather>() {
                    override fun onSuccess(t: DailyWeather) {
                        dayLiveData.value = t
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
        )
    }
}