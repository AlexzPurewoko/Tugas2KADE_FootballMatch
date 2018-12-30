package com.apwdevs.footballMatch.activityComponents.onDetailMatch.apiRequest

import com.apwdevs.footballMatch.BuildConfig

object GetDataFromAPI {
    fun getDetailMatchURL(event_id: String): String {
        return "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupevent.php?id=$event_id"
    }

    fun getImageClubURL(club_id: String): String {
        return "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/lookupteam.php?id=$club_id"
    }
}