package com.example.architecturewithcoroutine

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {

    /*checking internet connectivity*/
    fun isConnected(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun hideSoftKeyboard(context: Context, view: View?) {
        val inputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    class NetManager  constructor(private val applicationContext: Context) {

        val isConnectedToInternet: Boolean
            @SuppressLint("MissingPermission")
            get() {
                val conManager =
                    applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val network = conManager.activeNetworkInfo
                return network != null && network.isConnected
            }
    }
}