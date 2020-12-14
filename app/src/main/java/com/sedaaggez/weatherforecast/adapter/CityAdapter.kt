package com.sedaaggez.weatherforecast.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.sedaaggez.weatherforecast.R
import com.sedaaggez.weatherforecast.databinding.ItemCityBinding
import com.sedaaggez.weatherforecast.model.City
import com.sedaaggez.weatherforecast.view.CitiesFragmentDirections
import kotlinx.android.synthetic.main.item_city.view.*

class CityAdapter(val cityList: ArrayList<City>) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>(), CityClickListener {

    class CityViewHolder(var view: ItemCityBinding) : RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view =
            DataBindingUtil.inflate<ItemCityBinding>(inflater, R.layout.item_city, parent, false)
        return CityViewHolder(view)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.view.city = cityList[position]
        holder.view.listener = this
    }

    override fun getItemCount(): Int {
        return cityList.size
    }

    override fun onCityClicked(v: View) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, v.textViewName.text.toString())
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle)

        val woIed = v.textViewWoeId.text.toString().toInt()
        val action =
            CitiesFragmentDirections.actionCitiesFragmentToDailyWeatherFragment(woIed)
        Navigation.findNavController(v).navigate(action)

    }

    fun updateCityList(newCityList: List<City>) {
        cityList.clear()
        cityList.addAll(newCityList)
        notifyDataSetChanged()
    }
}