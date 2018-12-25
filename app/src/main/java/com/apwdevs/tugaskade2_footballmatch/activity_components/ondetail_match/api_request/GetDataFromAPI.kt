package com.apwdevs.tugaskade2_footballmatch.activity_components.ondetail_match.api_request

import android.net.Uri
import com.apwdevs.tugaskade2_footballmatch.BuildConfig

object GetDataFromAPI {
    fun getDetailMatchURL(event_id: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupevent.php")
            .appendQueryParameter("id", event_id)
            .build()
            .toString()
    }

    fun getImageClubURL(club_id: String): String {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
            .appendPath("api")
            .appendPath("v1")
            .appendPath("json")
            .appendPath(BuildConfig.TSDB_API_KEY)
            .appendPath("lookupteam.php")
            .appendQueryParameter("id", club_id)
            .build()
            .toString()
    }
}