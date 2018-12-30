package com.apwdevs.footballMatch.fragmentComponents.apiRequest

import com.apwdevs.footballMatch.BuildConfig
import java.io.Serializable

object GetMatchInLeague {
    fun getMatch(idLeague: String, matchType: MATCH_TYPE): String {

        return when (matchType) {
            MATCH_TYPE.LAST_MATCH -> "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php?id=$idLeague"
            MATCH_TYPE.NEXT_MATCH -> "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnextleague.php?id=$idLeague"
        }
    }
}

enum class MATCH_TYPE : Serializable {
    LAST_MATCH,
    NEXT_MATCH
}