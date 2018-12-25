package com.apwdevs.tugaskade2_footballmatch.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.net.InetAddress
import java.net.UnknownHostException

class CekKoneksi {
    @Throws(InterruptedException::class)
    fun isConnected(activity: Context, expired: Long): Boolean {
        val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(
                ConnectivityManager.TYPE_WIFI
            ).state == NetworkInfo.State.CONNECTED
        ) {
            //we are connected to a network
            /*IsNetworkReachable isReachableNet = new IsNetworkReachable();
            isReachableNet.run();
            int splice = Math.round(expired / 100);
            for(int x = 0; x < splice; x++) {
                Thread.sleep(100);
                if(threadState)
                    break;
            }
            if(isReachableNet.isAlive()) {
                isReachableNet.interrupt();
                return false;
            }
            else
                return isReachable;*/
            isReachable = isReachableNetwoorks()
            return isReachableNetwoorks()
        } else
            return false
    }

    private fun isReachableNetwoorks(): Boolean {
        try {
            val inetAddress = InetAddress.getByName("google.com")
            return if (inetAddress.toString().length > 1)
                true
            else
                false
        } catch (e: UnknownHostException) {
            return false
        }

    }
}