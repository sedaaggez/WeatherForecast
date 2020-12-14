package com.sedaaggez.weatherforecast.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedaaggez.weatherforecast.R
import com.sedaaggez.weatherforecast.adapter.DailyWeatherAdapter
import com.sedaaggez.weatherforecast.databinding.FragmentDailyWeatherBinding
import com.sedaaggez.weatherforecast.model.ConsolidatedWeather
import com.sedaaggez.weatherforecast.viewmodel.DailyWeatherViewModel
import kotlinx.android.synthetic.main.fragment_daily_weather.*

class DailyWeatherFragment : Fragment() {

    private lateinit var viewModel: DailyWeatherViewModel
    private var cityWoeId = 0
    private val dailyWeatherAdapter = DailyWeatherAdapter(arrayListOf())
    private lateinit var dataBinding: FragmentDailyWeatherBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_daily_weather,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            cityWoeId = DailyWeatherFragmentArgs.fromBundle(it).cityWoeId
        }

        viewModel = ViewModelProviders.of(this).get(DailyWeatherViewModel::class.java)
        viewModel.getDataDailyWeatherList(cityWoeId)

        recyclerViewNextDays.layoutManager = LinearLayoutManager(context)
        recyclerViewNextDays.adapter = dailyWeatherAdapter

        observeLiveData()
    }

    private fun observeLiveData() {
        progressBarDailyWeather.visibility = View.VISIBLE
        viewModel.dayLiveData.observe(viewLifecycleOwner, Observer { dailyWeather ->
            dailyWeather?.let {
                dataBinding.dailyWeather = dailyWeather
                dataBinding.currentDay = dailyWeather.consolidatedWeatherList[0]
                val otherDays =
                    dailyWeather.consolidatedWeatherList as ArrayList<ConsolidatedWeather>
                otherDays.removeAt(0)
                dailyWeatherAdapter.updateConsolidatedWeatherList(otherDays)
            }
            progressBarDailyWeather.visibility = View.GONE
        })

    }


}