package com.apwdevs.footballMatch.activityComponents.onSplashScreen.apiRequest

import com.apwdevs.footballMatch.BuildConfig

object GetLeagueSoccer {
    fun getAllLeague(): String {
        return "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/all_leagues.php"
    }
}