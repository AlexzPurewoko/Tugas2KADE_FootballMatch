package com.apwdevs.tugaskade2_footballmatch.activity_components.onsplash_screen.api_request

import android.net.Uri
import com.apwdevs.tugaskade2_footballmatch.BuildConfig

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