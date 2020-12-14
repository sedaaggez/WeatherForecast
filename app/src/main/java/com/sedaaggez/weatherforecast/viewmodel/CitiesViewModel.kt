package com.sedaaggez.weatherforecast.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sedaaggez.weatherforecast.model.City
import com.sedaaggez.weatherforecast.service.CityAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class CitiesViewModel : ViewModel() {

    private val cityAPIService = CityAPIService()
    private val disposable = CompositeDisposable()

    val cities = MutableLiveData<List<City>>()
    val cityError = MutableLiveData<Boolean>()

    fun getData(lattlong: String) {
        disposable.add(
            cityAPIService.getCities(lattlong)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<City>>() {
                    override fun onSuccess(t: List<City>) {
                        cities.value = t
                        cityError.value = false
                    }

                    override fun onError(e: Throwable) {
                        cityError.value = true
                        e.printStackTrace()
                    }

                })
        )
    }

}