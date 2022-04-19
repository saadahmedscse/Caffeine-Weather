package com.caffeine.caffeineweather.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.caffeine.caffeineweather.R
import com.google.android.material.snackbar.Snackbar

object Constants {
    const val app_name = "Caffeine Weather";

    const val SNACK_LONG = Snackbar.LENGTH_LONG
    const val SNACK_SHORT = Snackbar.LENGTH_SHORT
    const val TOAST_LONG = Toast.LENGTH_LONG
    const val TOAST_SHORT = Toast.LENGTH_SHORT

    const val SPACE = " "
    const val BLANK = ""
    const val NEW_LINE = "\n"
    const val ALLOW_PERMISSION = "You must need to allow permission"
    const val FRONT_PAGE_STATE = "state"

    const val NO_INTERNET = "No Internet Connection"
    const val API_LIMIT_REACHED = "API rate limit exceeded"
    const val TOO_MANY_REQUEST = "Too many request from your device. Please come back later"
    const val ERROR = "Unexpected error occurred"

    const val CLOSE = "Close"

    //API
    const val BASE_URL = "https://free-news.p.rapidapi.com/v1/"
    const val X_RAPID_API_HOST = "free-news.p.rapidapi.com"
    const val X_RAPID_API_KEY = "bd40a269b6msh78f3d4e11748ecap168345jsnb848afa0759b"

    fun getHorizontalLayoutManager(context : Context) : LinearLayoutManager{
        return LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    fun getVerticalLayoutManager(context : Context) : LinearLayoutManager{
        return LinearLayoutManager(context)
    }

    /*
    fun getStateOfOfNews() : MutableLiveData<DataState<APIResponse>>{
        return MutableLiveData<DataState<APIResponse>>()
    }

     */

    fun intentToActivity(activity : Activity, c : Class<*>){
        activity.startActivity(Intent(activity, c))
        activity.finish()
    }

    fun showToast(context : Context, message : String, duration : Int){
        Toast.makeText(context, message, duration).show()
    }

    fun showSnackBar(context : Context, view : View, message: String, duration : Int){

        val snackBar = Snackbar.make(view, message, duration)
        snackBar.setAction("Close") {
            snackBar.dismiss()
        }

        val snackbarView = snackBar.view
        val font = ResourcesCompat.getFont(context, R.font.regular)

        snackbarView.setBackgroundResource(R.drawable.snack_bg)
        val snackText = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        val snackActionText = snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        snackText.setTextColor(Color.LTGRAY)
        snackText.typeface = font
        snackActionText.typeface = font
        snackActionText.isAllCaps = false
        snackActionText.setTextColor(context.resources.getColor(R.color.colorBlack))
        snackText.setTextColor(context.resources.getColor(R.color.colorDarkGrey))
        snackBar.show()
    }
}