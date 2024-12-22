package com.burakcanduzcan.eterationnativedevelopmentstudycase.util

import android.content.Context
import android.net.ConnectivityManager

object Helper {
    fun hasNetwork(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}