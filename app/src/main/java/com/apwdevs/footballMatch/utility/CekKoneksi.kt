package com.apwdevs.footballMatch.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import java.io.IOException
import java.net.InetAddress

object CekKoneksi {
    @Throws(InterruptedException::class)
    fun isConnected(
        activity: Context,
        coroutineContextProvider: CoroutineContextProvider = CoroutineContextProvider()
    ): Deferred<Boolean> {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        //val networkInfo = connectivityManager.activeNetworkInfo
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
            ConnectivityManager.TYPE_WIFI
            ).state == NetworkInfo.State.CONNECTED
        ) {
            return isReachableNetwoorks("8.8.8.8", coroutineContextProvider)
        }
        return GlobalScope.async(coroutineContextProvider.main) {
            false
        }
    }

    fun isReachableNetwoorks(
        host: String = "google.com",
        coroutineContextProvider: CoroutineContextProvider
    ): Deferred<Boolean> = GlobalScope.async(coroutineContextProvider.main) {
        try {
            val inetAddress = InetAddress.getByName(host)
            inetAddress.hostAddress != null && inetAddress.hostAddress.length > 1
        } catch (e: IOException) {
            false
        }


    }
}