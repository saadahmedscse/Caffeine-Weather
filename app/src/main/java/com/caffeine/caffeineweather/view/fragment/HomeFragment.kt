package com.caffeine.caffeineweather.view.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.caffeine.caffeineweather.databinding.FragmentHomeBinding
import com.caffeine.caffeineweather.services.api.RetroInstance
import com.caffeine.caffeineweather.utils.Constants
import com.caffeine.caffeineweather.utils.DataState
import com.caffeine.caffeineweather.viewmodel.WeatherViewModel
import java.io.IOException


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel : WeatherViewModel by viewModels()

    private lateinit var location: Location
    private lateinit var addresses: List<Address>
    private lateinit var geocoder: Geocoder

    private var city = ""
    private var country = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getLocationInfo()

        val loc = (city + Constants.SPACE + country)
        viewModel.getWeatherInfo(loc, requireContext(), RetroInstance)
        weatherObserver()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        binding.tv.text = city + Constants.SPACE + country

        return binding.root
    }

    private fun weatherObserver(){
        viewModel.weatherLiveData.observe(this) {
            when (it) {
                is DataState.Loading -> {}

                is DataState.Success -> {
                    Log.d("debug_api_data", it.data?.next_days?.get(0)?.comment!!)
                }

                is DataState.Failed -> {
                    Log.d("debug_api_data", it.message!!)
                }
            }
        }
    }

    private fun getLocationInfo(){
        val locationManager = requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1)
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1)

                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        else{
            location = locationManager
                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)!!
            geocoder = Geocoder(requireContext())
            try {
                addresses = geocoder.getFromLocation(location.latitude, location.longitude, 10)
                val address = addresses[1]
                country = address.countryName
                city = address.subAdminArea
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //granted
            }
            else {
                //denied
            }
        }
    }
}