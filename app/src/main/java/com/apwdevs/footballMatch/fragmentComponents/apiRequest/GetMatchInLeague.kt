package com.apwdevs.footballMatch.fragmentComponents.apiRequest

import com.apwdevs.footballMatch.BuildConfig
import java.io.Serializable

object GetMatchInLeague {
    fun getMatch(idLeague: String, matchType: MatchType): String {

        return when (matchType) {
            MatchType.LAST_MATCH -> "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/eventspastleague.php?id=$idLeague"
            MatchType.NEXT_MATCH -> "${BuildConfig.BASE_URL}api/v1/json/${BuildConfig.TSDB_API_KEY}/eventsnextleague.php?id=$idLeague"
        }
    }
}

enum class MatchType : Serializable {
    LAST_MATCH,
    NEXT_MATCH
}