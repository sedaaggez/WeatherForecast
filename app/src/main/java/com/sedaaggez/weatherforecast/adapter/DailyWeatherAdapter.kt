package com.sedaaggez.weatherforecast.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.sedaaggez.weatherforecast.R
import com.sedaaggez.weatherforecast.databinding.ItemDailyWeatherBinding
import com.sedaaggez.weatherforecast.model.ConsolidatedWeather

class DailyWeatherAdapter(val consolidatedWeatherList: ArrayList<ConsolidatedWeather>) :
    RecyclerView.Adapter<DailyWeatherAdapter.DailyWeatherViewHolder>() {

    class DailyWeatherViewHolder(var view: ItemDailyWeatherBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyWeatherViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ItemDailyWeatherBinding>(
            inflater,
            R.layout.item_daily_weather,
            parent,
            false
        )
        return DailyWeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyWeatherViewHolder, position: Int) {
        holder.view.consolidatedWeather = consolidatedWeatherList[position]

    }

    override fun getItemCount(): Int {
        return consolidatedWeatherList.size
    }

    fun updateConsolidatedWeatherList(newConsolidatedWeatherList: List<ConsolidatedWeather>) {
        consolidatedWeatherList.clear()
        consolidatedWeatherList.addAll(newConsolidatedWeatherList)
        notifyDataSetChanged()
    }
}