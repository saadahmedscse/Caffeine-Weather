package com.caffeine.caffeineweather.view.activity

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.caffeine.caffeineweather.R
import com.caffeine.caffeineweather.databinding.ActivityFrontBinding
import com.caffeine.caffeineweather.utils.Constants

class FrontActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFrontBinding
    private lateinit var editor : SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFrontBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preference = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        editor = preference.edit()

        if (preference.getBoolean(Constants.FRONT_PAGE_STATE, false)){
            intentToHome()
        }

        binding.btnGetStarted.setOnClickListener{
            if (ContextCompat.checkSelfPermission( this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION), 1)
                }
            }
        }
    }

    private fun intentToHome(){
        Constants.intentToActivity(this, HomeActivity::class.java)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                editor.putBoolean(Constants.FRONT_PAGE_STATE, true)
                editor.apply()
                intentToHome()
            }
            else {
                Constants.showSnackBar(this, binding.root, Constants.ALLOW_PERMISSION, Constants.SNACK_LONG)
            }
        }
    }
}