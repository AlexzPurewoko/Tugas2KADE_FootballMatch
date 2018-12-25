package com.apwdevs.tugaskade2_footballmatch.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.net.InetAddress
import java.net.UnknownHostException

object CekKoneksi {
    @Throws(InterruptedException::class)
    fun isConnected(activity: Context): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //val networkInfo = connectivityManager.activeNetworkInfo
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
        ).state == NetworkInfo.State.CONNECTED
    }

    private fun isReachableNetwoorks(): Boolean {
        try {
            val inetAddress = InetAddress.getByName("google.com")
            return inetAddress.toString().length > 1
        } catch (e: UnknownHostException) {
            return false
        }

    }
}