package com.sedaaggez.weatherforecast.view

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sedaaggez.weatherforecast.R
import com.sedaaggez.weatherforecast.adapter.CityAdapter
import com.sedaaggez.weatherforecast.viewmodel.CitiesViewModel
import kotlinx.android.synthetic.main.fragment_cities.*


class CitiesFragment : Fragment() {

    private lateinit var viewModel: CitiesViewModel
    private val cityAdapter = CityAdapter(arrayListOf())
    private val requestPermissionCode = 1
    private var lattlong = ""
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var connectivityManager: ConnectivityManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CitiesViewModel::class.java)
        locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivityManager.activeNetwork == null || !locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )
        ) {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Internet or Location off")
            builder.setMessage("Please turn on internet and location to continue")
            builder.setPositiveButton("Okay") { dialog, which ->
                startActivity(Intent(Settings.ACTION_SETTINGS))
                requireActivity().finish()

            }
            builder.show()
        } else {
            locationListener = object : LocationListener {
                override fun onLocationChanged(location: Location?) {
                    if (location != null) {
                        lattlong =
                            location.latitude.toString() + "," + location.longitude.toString()
                        viewModel.getData(lattlong)
                    }
                }

                override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {
                }

                override fun onProviderEnabled(p0: String?) {
                }

                override fun onProviderDisabled(p0: String?) {
                }

            }

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermission()
            } else {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    30,
                    30f,
                    locationListener
                )
                val lastLocation =
                    locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (lastLocation != null) {
                    lattlong =
                        lastLocation.latitude.toString() + "," + lastLocation.longitude.toString()
                    viewModel.getData(lattlong)
                }
            }
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = cityAdapter

            observeLiveData()

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == requestPermissionCode) {
            if (grantResults.size > 0) {
                if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        30,
                        30f,
                        locationListener
                    )
                }
            }
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestPermissionCode
        )
    }

    private fun observeLiveData() {
        progressBar.visibility = View.VISIBLE
        viewModel.cities.observe(viewLifecycleOwner, Observer { cities ->
            cities?.let {
                recyclerView.visibility = View.VISIBLE
                cityAdapter.updateCityList(cities)
            }
            progressBar.visibility = View.GONE
        })

        viewModel.cityError.observe(viewLifecycleOwner, Observer { error ->
            error?.let {
                if (it) {
                    textViewError.visibility = View.VISIBLE
                } else {
                    textViewError.visibility = View.GONE
                }
            }
        })
    }

}