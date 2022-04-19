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
import com.caffeine.caffeineweather.R
import com.caffeine.caffeineweather.databinding.FragmentHomeBinding
import com.caffeine.caffeineweather.services.api.RetroInstance
import com.caffeine.caffeineweather.utils.Constants
import com.caffeine.caffeineweather.utils.DataState
import com.caffeine.caffeineweather.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import java.io.IOException
import java.text.SimpleDateFormat


class HomeFragment : Fragment() {

    private lateinit var binding : FragmentHomeBinding
    private val viewModel : WeatherViewModel by viewModels()

    private lateinit var location: Location
    private lateinit var addresses: List<Address>
    private lateinit var geocoder: Geocoder

    private var city = ""
    private var country = ""

    private lateinit var sdf : SimpleDateFormat
    private lateinit var ndf : SimpleDateFormat

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeBinding.inflate(layoutInflater)

        sdf = SimpleDateFormat("EEEE hh:mm a")
        ndf = SimpleDateFormat("hh:mm a")
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

        return binding.root
    }

    private fun weatherObserver(){
        viewModel.weatherLiveData.observe(this) {
            when (it) {
                is DataState.Loading -> {}

                is DataState.Success -> {
                    val response = it.data
                    binding.date.text = response?.currentConditions?.dayhour
                    binding.temp.text = response?.currentConditions?.temp?.c.toString()
                    binding.location.text = response?.region
                    binding.comment.text = response?.currentConditions?.comment

                    calculateTime(response?.currentConditions?.comment, response?.currentConditions?.dayhour)
                }

                is DataState.Failed -> {
                    Log.d("debug_api_data", it.message!!)
                }
            }
        }
    }

    private fun calculateTime(comment : String?, time : String?){
        val defaultDate = sdf.parse(time!!)
        val mainDate = ndf.format(defaultDate!!)
        val mainTime = ndf.parse(mainDate)!!.time

        var isDay = false

        if (mainTime in 1..43199999) isDay = true

        setImage(comment!!, isDay)
    }

    private fun setImage(comment : String, isDay : Boolean){
        if (isDay){
            setImageForDay(comment)
        }
        else {
            setImageForNight(comment)
        }
    }

    private fun setImageForDay(comment : String){
        when (comment) {
            Constants.PARTLY_CLOUDY -> {
                setImageUsingPicasso(R.drawable.partly_cloudy)
            }
            Constants.HAZE -> {
                setImageUsingPicasso(R.drawable.haze)
            }
            Constants.CLEAR -> {
                setImageUsingPicasso(R.drawable.clear)
            }
            Constants.CLEAR_WITH_PERIODIC_CLOUDS -> {
                setImageUsingPicasso(R.drawable.partly_cloudy)
            }
            Constants.SHOWERS -> {
                setImageUsingPicasso(R.drawable.showers)
            }
            Constants.RAIN -> {
                setImageUsingPicasso(R.drawable.rain)
            }
            Constants.THUNDERSTROM -> {
                setImageUsingPicasso(R.drawable.thunderstrom)
            }
            Constants.SCATTERED_THUNDERSTROM -> {
                setImageUsingPicasso(R.drawable.scattered_thunderstrom)
            }
            Constants.SUNNY -> {
                setImageUsingPicasso(R.drawable.clear)
            }
            Constants.ISOLATED_THUNDERSTROM -> {
                setImageUsingPicasso(R.drawable.isolated_thunderstrom)
            }
            Constants.MOSTLY_SUNNY -> {
                setImageUsingPicasso(R.drawable.clear)
            }
            Constants.CLOUDY -> {
                setImageUsingPicasso(R.drawable.cloudy)
            }
            Constants.MOSTLY_CLOUDY -> {
                setImageUsingPicasso(R.drawable.cloudy)
            }
            Constants.SCATTERED_SHOWERS -> {
                setImageUsingPicasso(R.drawable.showers)
            }
            Constants.RAIN_SNOW -> {
                setImageUsingPicasso(R.drawable.rain_and_snow)
            }
            Constants.SCATTERED_SNOW_SHOWERS -> {
                setImageUsingPicasso(R.drawable.scattered_snow_showers)
            }
            Constants.SNOW -> {
                setImageUsingPicasso(R.drawable.snow)
            }
        }
    }

    private fun setImageForNight(comment : String){
        when (comment) {
            Constants.PARTLY_CLOUDY -> {
                setImageUsingPicasso(R.drawable.partly_cloudy_night)
            }
            Constants.HAZE -> {
                setImageUsingPicasso(R.drawable.haze)
            }
            Constants.CLEAR -> {
                setImageUsingPicasso(R.drawable.clear_night)
            }
            Constants.CLEAR_WITH_PERIODIC_CLOUDS -> {
                setImageUsingPicasso(R.drawable.partly_cloudy_night)
            }
            Constants.SHOWERS -> {
                setImageUsingPicasso(R.drawable.showers_night)
            }
            Constants.RAIN -> {
                setImageUsingPicasso(R.drawable.rain)
            }
            Constants.THUNDERSTROM -> {
                setImageUsingPicasso(R.drawable.thunderstrom)
            }
            Constants.SCATTERED_THUNDERSTROM -> {
                setImageUsingPicasso(R.drawable.scattered_thunderstrom)
            }
            Constants.SUNNY -> {
                setImageUsingPicasso(R.drawable.clear_night)
            }
            Constants.ISOLATED_THUNDERSTROM -> {
                setImageUsingPicasso(R.drawable.isolated_thunderstrom)
            }
            Constants.MOSTLY_SUNNY -> {
                setImageUsingPicasso(R.drawable.clear_night)
            }
            Constants.CLOUDY -> {
                setImageUsingPicasso(R.drawable.cloudy_night)
            }
            Constants.MOSTLY_CLOUDY -> {
                setImageUsingPicasso(R.drawable.cloudy_night)
            }
            Constants.SCATTERED_SHOWERS -> {
                setImageUsingPicasso(R.drawable.showers_night)
            }
            Constants.RAIN_SNOW -> {
                setImageUsingPicasso(R.drawable.rain_and_snow)
            }
            Constants.SCATTERED_SNOW_SHOWERS -> {
                setImageUsingPicasso(R.drawable.scattered_snow_showers)
            }
            Constants.SNOW -> {
                setImageUsingPicasso(R.drawable.snow)
            }
        }
    }

    private fun setImageUsingPicasso(image : Int){
        Picasso.get().load(image).into(binding.weatherIco)
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