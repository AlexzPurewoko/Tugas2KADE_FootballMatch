package com.apwdevs.tugaskade2_footballmatch.fragment_components.api_request

import android.net.Uri
import com.apwdevs.tugaskade2_footballmatch.BuildConfig
import java.io.Serializable

object GetMatchInLeague {
    fun getMatch(idLeague: String, match_type: MATCH_TYPE): String {

        return when (match_type) {
            MATCH_TYPE.LAST_MATCH -> Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventspastleague.php")
                .appendQueryParameter("id", idLeague)
                .build()
                .toString()
            MATCH_TYPE.NEXT_MATCH -> Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("api")
                .appendPath("v1")
                .appendPath("json")
                .appendPath(BuildConfig.TSDB_API_KEY)
                .appendPath("eventsnextleague.php")
                .appendQueryParameter("id", idLeague)
                .build()
                .toString()
        }
    }
}

enum class MATCH_TYPE : Serializable {
    LAST_MATCH,
    NEXT_MATCH
}