package com.apwdevs.footballMatch.activityComponents.onSplashScreen.apiRequest

import android.net.Uri
import com.apwdevs.footballMatch.BuildConfig

object GetLeagueSoccer {
    fun getAllLeague(): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("all_leagues.php")
            .build()
            .toString()

    }
}